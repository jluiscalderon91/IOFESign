package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.config.Global;
import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.repository.DeliveryRepository;
import com.apsout.electronictestimony.api.service.*;
import com.apsout.electronictestimony.api.util.allocator.DeliveryAllocator;
import com.apsout.electronictestimony.api.util.allocator.DocumentAllocator;
import com.apsout.electronictestimony.api.util.dealer.RestUploader;
import com.apsout.electronictestimony.api.util.enums.Mode;
import com.apsout.electronictestimony.api.util.enums.ScopeResource;
import com.apsout.electronictestimony.api.util.file.FileUtil;
import com.apsout.electronictestimony.api.util.statics.Default;
import com.apsout.electronictestimony.api.util.statics.DocumentState;
import com.apsout.electronictestimony.api.util.statics.States;
import com.apsout.electronictestimony.api.util.statics.TraceabilityType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    private static Logger logger = LogManager.getLogger(DeliveryServiceImpl.class);

    @Autowired
    private DeliveryRepository repository;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private DeliverysettingService deliverysettingService;
    @Autowired
    private DocumenttraceabilityService traceability;

    @Override
    public Delivery save(Delivery delivery) {
        return repository.save(delivery);
    }

    @Override
    public Optional<Delivery> findBy(byte sent) {
        return repository.findFirstBySentAndActiveAndDeletedOrderByPriorityAsc(sent, States.ACTIVE, States.EXISTENT);
    }

    public Optional<Delivery> find4Send() {
        return this.findBy(States.NOT_SENT);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deliver() {
        Optional<Delivery> optionalDelivery = this.find4Send();
        if (!optionalDelivery.isPresent()) {
            return;
        }
        System.setProperty("jsse.enableSNIExtension", "false");
        Delivery delivery = optionalDelivery.get();
        Document document = delivery.getDocumentByDocumentId();
        if (States.NOT_DELIVER == document.getWorkflowByWorkflowId().getDeliver()) {
            return;
        }
        Enterprise enterprise = document.getPersonByPersonId().getEnterpriseByEnterpriseIdView();
        Optional<Deliverysetting> optional = deliverysettingService.findByEnterprise(enterprise);
        if (optional.isPresent()) {
            logger.info(String.format("File sending started to: enterpriseId: %d, deliveryId: %d", enterprise.getId(), delivery.getId()));
            Deliverysetting deliverysetting = optional.get();
            String requestURL = deliverysetting.getUrl();
            boolean isPaino = requestURL.contains("www.notariapaino.com.pe");//Identificamos a Paino, ya que fueron inicialmente quienes nos impusieron su lista de parámetros
            final String requestBody = buildJSONBody(document, isPaino);
            HttpResponse httpResponse = RestUploader.post(requestURL, requestBody);
            final int statusCode = httpResponse.getStatusLine().getStatusCode();
            String contentResponse;
            try {
                contentResponse = EntityUtils.toString(httpResponse.getEntity());
            } catch (IOException e) {
                throw new RuntimeException(String.format("Can't get response content for deliveryId: %d", delivery.getId()));
            }
            if (HttpStatus.SC_OK == statusCode) {
                if (isPaino) {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        String responseCode = mapper.readTree(contentResponse).get("CodRespuesta").asText();
                        String responseDescription = mapper.readTree(contentResponse).get("DesRespuesta").asText();
                        final String successProcess = "300";
                        if (successProcess.equals(responseCode)) {
                            DeliveryAllocator.forUpdate(delivery, contentResponse, responseCode, responseDescription);
                            DocumentAllocator.forUpdateWithState(document, DocumentState.DELIVERED);
                            documentService.save(document);
                            traceability.save(Default.SUPERADM_PERSON_ID, document, DocumentState.DELIVERED, TraceabilityType.MANDATORY);
                        } else {
                            DeliveryAllocator.forReducePriority(delivery, responseCode, responseDescription);
                        }
                        deliveryService.save(delivery);
                        logger.info(String.format("Delivered attachment for enterpriseId: %d, documentId: %d, subject: %s", enterprise.getId(), document.getId(), document.getSubject()));
                    } catch (JsonProcessingException e) {
                        logger.error(String.format("Reading responde body of nodes for documentId: %d", document.getId()), e);
                    }
                } else {
                    DeliveryAllocator.forUpdate(delivery, requestBody, contentResponse, "200", "Envío de archivo satisfactorio");
                    DocumentAllocator.forUpdateWithState(document, DocumentState.DELIVERED);
                    documentService.save(document);
                    traceability.save(Default.SUPERADM_PERSON_ID, document, DocumentState.DELIVERED, TraceabilityType.MANDATORY);
                    deliveryService.save(delivery);
                    logger.info(String.format("Delivered attachment for enterpriseId: %d, documentId: %d, subject: %s", enterprise.getId(), document.getId(), document.getSubject()));
                }
            } else {
                DeliveryAllocator.forReducePriority(delivery, String.valueOf(statusCode), "Se produjo un error al enviar el archivo");
                deliveryService.save(delivery);
                logger.error(String.format("Server status error code: %d for documentId: %d of requestURL: %s", statusCode, document.getId(), requestURL));
            }
            logger.info(String.format("File sending finished for enterpriseId: %d, deliveryId: %d", enterprise.getId(), delivery.getId()));
        } else {
            logger.info(String.format("Config delivery not found for enterprise: %s", enterprise.getName()));
        }
    }

    private String buildJSONBody(Document document, boolean isPaino) {
        List<File> fileList = resourceService.findAllFilesBy(document, ScopeResource.PRIVATE);
        Optional<Resource> resourceOpt = resourceService.findFirstBy(document);
        if (!fileList.isEmpty() && resourceOpt.isPresent()) {
            Resource resource = resourceOpt.get();
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode objectNode = mapper.createObjectNode();
            String subject = buildSubject(document, isPaino);
            String code = document.getWorkflowByWorkflowId().getCode();
            if (isPaino) {
                File file = fileList.get(0);
                String base64Encoded = FileUtil.encode2Base64(file);
                objectNode.put("File", base64Encoded);
                objectNode.put("NroKardex", subject);
                objectNode.put("Tipo", code);
            } else {
                String urlfile = documentService.getURLStreamBy(document);
                if (fileList.size() == 1) {
                    File file = fileList.get(0);
                    objectNode.put("subject", subject);
                    objectNode.put("verificationcode", resource.getResumeHash());
                    objectNode.put("urlfile", urlfile);
                    objectNode.put("filename", resource.getOriginalName());
                    objectNode.put("code", code);
                } else {
                    objectNode.put("subject", subject);
                    objectNode.put("verificationcode", "No verification code");
                    objectNode.put("urlfile", urlfile);
                    objectNode.put("filename", subject + "_compressed.zip");
                    objectNode.put("code", code);
                }
            }
            return objectNode.toString();
        }
        return "undefined";
    }

    private String buildSubject(Document document, boolean isPaino) {
        if (isPaino) {
            final String subjectTest = "8060";
            return Mode.PRODUCTION == Global.MODE ? document.getSubject().trim() : subjectTest;
        } else {
            return document.getSubject().trim();
        }
    }
}
