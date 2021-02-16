package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.entity.common.MoreAboutWorkflow;
import com.apsout.electronictestimony.api.entity.model.pojo._WorkflowTemplateDesign;
import com.apsout.electronictestimony.api.exception.WorkflowNotFoundException;
import com.apsout.electronictestimony.api.repository.WorkflowRepository;
import com.apsout.electronictestimony.api.service.*;
import com.apsout.electronictestimony.api.util.StamplayoutfileAllocator;
import com.apsout.electronictestimony.api.util.allocator.*;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WorkflowServiceImpl implements WorkflowService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowServiceImpl.class);

    @Autowired
    private WorkflowRepository repository;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private StamplegendService stamplegendService;
    @Autowired
    private StampimageService stampimageService;
    @Autowired
    private StampqrcodeService stampqrcodeService;
    @Autowired
    private WorkflowstamplegendService workflowstamplegendService;
    @Autowired
    private WorkflowstampimageService workflowstampimageService;
    @Autowired
    private WorkflowstampqrcodeService workflowstampqrcodeService;
    @Autowired
    private WorkflowstampdatetimeService workflowstampdatetimeService;
    @Autowired
    private FonttypeService fonttypeService;
    @Autowired
    private FontsizeService fontsizeService;
    @Autowired
    private FontcolorService fontcolorService;
    @Autowired
    private PagestampService pagestampService;
    @Autowired
    private StamptestfileService stamptestfileService;
    @Autowired
    private ContentpositionService contentpositionService;
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private StampdatetimeService stampdatetimeService;
    @Autowired
    private StamplayoutfileService stamplayoutfileService;
    @Autowired
    private WorkflowtypeService workflowtypeService;
    @Autowired
    private StamprubricService stamprubricService;
    @Autowired
    private WorkflowstamprubricService workflowstamprubricService;

    @Override
    public List<Workflow> findAllBy(int enterpriseId) {
        return findAllBy(Arrays.asList(enterpriseId));
    }

    @Override
    public List<Workflow> findAll() {
        return repository.findAllByActiveAndDeleted(States.ACTIVE, States.EXISTENT);
    }

    @Override
    public Page<Workflow> findAllBy(int partnerId, int enterpriseId, String term2Search, Pageable pageable) {
        Enterprise enterprise2 = enterpriseService.getBy(enterpriseId);
        List<Integer> enterpriseIds = enterpriseService.getEnterpriseIdsBy(partnerId, enterprise2);
        Page<Workflow> workflowPage;
        if (term2Search.isEmpty()) {
            workflowPage = repository.findByEnterpriseIdInAndDeleted(enterpriseIds, States.EXISTENT, pageable);
        } else {
            workflowPage = repository.findByEnterpriseIdInAndDescriptionContainingAndDeleted(enterpriseIds, term2Search, States.EXISTENT, pageable);
        }
        workflowPage.forEach(workflow -> {
            Enterprise enterprise = enterpriseService.getBy(workflow.getEnterpriseId());
            MoreAboutWorkflow moreAboutWorkflow = new MoreAboutWorkflow();
            List<Participant> participants = participantService.getAllBy(workflow);
            final byte requiredSieConfig = isRequiredSieConfig(participants) ? States.REQUIRED_SIE_CONFIG : States.NOT_REQUIRED_SIE_CONFIG;
            final byte sieConfigured = isSieConfigured(participants) ? States.SIE_CONFIGURED : States.SIE_NOT_CONFIGURED;
            WorkflowAllocator.loadMoreInfo(moreAboutWorkflow, enterprise, sieConfigured, requiredSieConfig);
            workflow.setMoreAboutWorkflow(moreAboutWorkflow);
        });
        return workflowPage;
    }

    private boolean isRequiredSieConfig(List<Participant> participants) {
        if (participants.isEmpty()) {
            return false;
        }
        return participants.stream().anyMatch(participant -> States.SEND_SIE_NOTIFICATION == participant.getSendNotification());
    }

    private boolean isSieConfigured(List<Participant> participants) {
        if (participants.isEmpty()) {
            return false;
        }
        if (!isRequiredSieConfig(participants)) {
            return false;
        }
        return !participants.stream().anyMatch(participant -> {
            if (States.SEND_SIE_NOTIFICATION == participant.getSendNotification()) {
                return States.SIE_NOT_CONFIGURED == participant.getSieConfigured();
            } else {
                return false;
            }
        });
    }

    private boolean isReady2Use(Workflow workflow, List<Participant> participants) {
        final boolean requiredSieConfig = isRequiredSieConfig(participants);
        if (!requiredSieConfig) {
            return States.COMPLETED == workflow.getCompleted();
        } else {
            return States.COMPLETED == workflow.getCompleted() && isSieConfigured(participants);
        }
    }

    public boolean ready2Use(Workflow workflow) {
        List<Participant> participants = participantService.getAllBy(workflow);
        return isReady2Use(workflow, participants);
    }

    @Override
    @Transactional
    public Workflow save(Workflow workflow) {
        Enterprise enterprise = enterpriseService.getBy(workflow.getEnterpriseId());
        Workflowtype workflowtype = workflowtypeService.getBy(workflow.getType());
        Workflow newWorkflow = WorkflowAllocator.build(workflow, enterprise, workflowtype);
        repository.save(newWorkflow);
        logger.info(String.format("Workflow save for workfowId: %d", newWorkflow.getId()));
        return newWorkflow;
    }

    @Override
    @Transactional
    public Workflow onlySave(Workflow workflow) {
        return repository.save(workflow);
    }

    @Override
    @Transactional
    public Workflow update(Workflow workflow) {
        Workflow workflowDb = getBy(workflow.getId());
        Workflowtype workflowtype = workflowtypeService.getBy(workflow.getType());
        WorkflowAllocator.forUpdate(workflowDb, workflow, workflowtype);
        repository.save(workflowDb);
        logger.info(String.format("Workflow updated by workflowId: %d", workflowDb.getId()));
        return workflowDb;
    }

    @Override
    @Transactional
    public Workflow delete(Workflow workflow) {
        WorkflowAllocator.forDeleted(workflow);
        repository.save(workflow);
        logger.info(String.format("Workflow deleted by workflowId: %d", workflow.getId()));
        return workflow;
    }

    @Override
    public Workflow getBy(int workflowId) {
        Optional<Workflow> optional = findBy(workflowId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new WorkflowNotFoundException(String.format("Workflow not found with workflowId: %d", workflowId));
    }

    public Optional<Workflow> findBy(int workflowId) {
        return repository.findByIdAndActiveAndDeleted(workflowId, States.ACTIVE, States.EXISTENT);
    }

    @Override
    public List<Workflow> findAllWhereIsAssigned(int personId) {
        return repository.findAllWherePersonWasAssigned(personId);
    }

    @Override
    public List<Workflow> findAllWhereIsParticipant(int personId) {
        return repository.findAllWherePersonIsParticipant(personId);
    }

    @Override
    public List<Workflow> findAllWhereIsParticipantOrAssigned(Person person) {
        return this.findAllWhereIsParticipantOrAssigned(person.getId());
    }

    public List<Workflow> findAllWhereIsParticipantOrAssigned(int personId) {
        List<Workflow> workflows1 = this.findAllWhereIsParticipant(personId);
        List<Workflow> workflows2 = this.findAllWhereIsAssigned(personId);
        return Stream
                .concat(workflows1.stream(), workflows2.stream())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Workflow> findAllBy4Outside(int enterpriseId) {
        List<Workflow> workflows = findAllBy(enterpriseId);
        WorkflowAllocator.forReturn(workflows);
        return workflows;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Workflow saveTemplateDesign(_WorkflowTemplateDesign workflowTemplateDesign) {
        int workflowId = workflowTemplateDesign.getWorkflowId();
        Workflow workflow = this.getBy(workflowId);
        // For testfile
        Stamptestfile stamptestfile = workflowTemplateDesign.getStamptestfile();
        if (stamptestfile != null) {
            StamptestfileAllocator.build(workflow, stamptestfile);
            stamptestfileService.save(stamptestfile);
        }
        // For stamplegends
        List<Stamplegend> stamplegends = workflowTemplateDesign.getStamplegends();
        if (!stamplegends.isEmpty()) {
            validateFontAndPageInfoLegend(stamplegends);
            StamplegendAllocator.ofPostMethod(stamplegends);
            List<Stamplegend> stamplegendsDb = stamplegendService.save(stamplegends);
            List<Workflowstamplegend> workflowstamplegends = WorkflowstamplegendAllocator.build(workflow, stamplegendsDb);
            workflowstamplegendService.save(workflowstamplegends);
        }
        // For stampimages
        List<Stampimage> stampimages = workflowTemplateDesign.getStampimages();
        if (!stampimages.isEmpty()) {
            validatePageInfoImage(stampimages);
            StampimageAllocator.ofPostMethod(stampimages);
            List<Stampimage> stampimagesDb = stampimageService.save(stampimages);
            List<Workflowstampimage> workflowstampimages = WorkflowstampimageAllocator.build(workflow, stampimagesDb);
            workflowstampimageService.save(workflowstampimages);
        }
        // For stampqrcodes
        List<Stampqrcode> stampqrcodes = workflowTemplateDesign.getStampqrcodes();
        if (!stampqrcodes.isEmpty()) {
            validatePageInfoQrcode(stampqrcodes);
            StampqrcodeAllocator.ofPostMethod(stampqrcodes);
            List<Stampqrcode> stampqrcodesDb = stampqrcodeService.save(stampqrcodes);
            List<Workflowstampqrcode> workflowstampqrcodes = WorkflowstampqrcodeAllocator.build(workflow, stampqrcodesDb);
            workflowstampqrcodeService.save(workflowstampqrcodes);
        }
        // For stampdatetimes
        List<Stampdatetime> stampdatetimes = workflowTemplateDesign.getStampdatetimes();
        if (!stampdatetimes.isEmpty()) {
            validatePageInfoDatetime(stampdatetimes);
            StampdatetimeAllocator.ofPostMethod(stampdatetimes);
            List<Stampdatetime> stampdatetimesDb = stampdatetimeService.save(stampdatetimes);
            List<Workflowstampdatetime> workflowstampdatetimes = WorkflowstampdatetimeAllocator.build(workflow, stampdatetimesDb);
            workflowstampdatetimeService.save(workflowstampdatetimes);
        }
        // For layoutfile
        Stamplayoutfile stamplayoutfile = workflowTemplateDesign.getStamplayoutfile();
        if (stamplayoutfile != null) {
            StamplayoutfileAllocator.build(workflow, stamplayoutfile);
            stamplayoutfileService.save(stamplayoutfile);
        }
        // For stamprubrics
        List<Stamprubric> stamprubrics = workflowTemplateDesign.getStamprubrics();
        if (!stamprubrics.isEmpty()) {
            validatePageInfoRubric(stamprubrics);
            StamprubricAllocator.ofPostMethod(stamprubrics);
            List<Stamprubric> stampimagesDb = stamprubricService.save(stamprubrics);
            List<Workflowstamprubric> workflowstamprubrics = WorkflowstamprubricAllocator.build(workflow, stampimagesDb);
            workflowstamprubricService.save(workflowstamprubrics);
        }
        return workflow;
    }

    private void validateFontAndPageInfoLegend(List<Stamplegend> stamplegends) {
        stamplegends.stream().forEach(this::validateFontAndPageInfoLegend);
    }

    private void validateFontAndPageInfoLegend(Stamplegend stamplegend) {
        int fonttypeId = stamplegend.getFontType();
        int fontcolorId = stamplegend.getFontColor();
        int fontsizeId = stamplegend.getFontSize();
        int pagestampId = stamplegend.getPageStamp();
        //Only for verify
        fonttypeService.getBy(fonttypeId);
        fontcolorService.getBy(fontcolorId);
        fontsizeService.getBy(fontsizeId);
        pagestampService.getBy(pagestampId);
    }

    private void validatePageInfoImage(List<Stampimage> stampimages) {
        stampimages.stream().forEach(this::validatePageInfoImage);
    }

    private void validatePageInfoImage(Stampimage stampimage) {
        int pagestampId = stampimage.getPageStamp();
        int contentpositionId = stampimage.getContentPosition();
        //Only for verify
        pagestampService.getBy(pagestampId);
        contentpositionService.getBy(contentpositionId);
    }

    private void validatePageInfoQrcode(List<Stampqrcode> stampqrcodes) {
        stampqrcodes.stream().forEach(this::validatePageInfoQrcode);
    }

    private void validatePageInfoQrcode(Stampqrcode stampqrcode) {
        int pagestampId = stampqrcode.getPageStamp();
        //Only for verify
        pagestampService.getBy(pagestampId);
    }

    private void validatePageInfoDatetime(List<Stampdatetime> stampdatetimes) {
        stampdatetimes.stream().forEach(this::validatePageInfoDatetime);
    }

    private void validatePageInfoDatetime(Stampdatetime stampdatetime) {
        int pagestampId = stampdatetime.getPageStamp();
        //Only for verify
        pagestampService.getBy(pagestampId);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Workflow updateTemplateDesign(_WorkflowTemplateDesign workflowTemplateDesign) {
        int workflowId = workflowTemplateDesign.getWorkflowId();
        Workflow workflow = this.getBy(workflowId);
        //Update testfile
        Stamptestfile stamptestfile = workflowTemplateDesign.getStamptestfile();
        if (stamptestfile != null && !stamptestfile.getBase64Data().isEmpty()) {
            //Disable actual row
            Integer stamptestfileId = stamptestfile.getId();
            Stamptestfile stamptestfileActual = stamptestfileService.getBy(stamptestfileId);
            StamptestfileAllocator.forDelete(stamptestfileActual);
            stamptestfileService.save(stamptestfileActual);
            Stamptestfile newStamptestfile = StamptestfileAllocator.forceBuild(workflow, stamptestfile);
            stamptestfileService.save(newStamptestfile);
        }
        // Update legends
        List<Stamplegend> stamplegends = workflowTemplateDesign.getStamplegends();
        List<Stamplegend> stamplegendsDb = stamplegends.stream().map(stamplegend -> {
            final Integer stamplegendId = stamplegend.getId();
            if (stamplegendId != null) {
                Stamplegend stamplegendDb = stamplegendService.getBy(stamplegendId);
                if (States.ACTIVE == stamplegend.getActive()) {
                    validateFontAndPageInfoLegend(stamplegend);
                    StamplegendAllocator.forUpdate(stamplegend, stamplegendDb);
                } else {
                    StamplegendAllocator.forDelete(stamplegendDb);
                }
                return stamplegendService.save(stamplegendDb);
            } else {
                validateFontAndPageInfoLegend(stamplegend);
                Stamplegend stamplegend1 = StamplegendAllocator.build(stamplegend);
                return stamplegendService.save(stamplegend1);
            }
        }).collect(Collectors.toList());
        List<Workflowstamplegend> workflowstamplegends = WorkflowstamplegendAllocator.build(workflow, stamplegendsDb);
        workflowstamplegends.stream().forEach(workflowstamplegend -> {
            final Workflow workflowId1 = workflowstamplegend.getWorkflowByWorkflowId();
            final Stamplegend stamplegend = workflowstamplegend.getStamplegendByStamplegendId();
            Optional<Workflowstamplegend> optionalWSL = workflowstamplegendService.findBy(workflowId1, stamplegend);
            if (!optionalWSL.isPresent()) {
                workflowstamplegendService.save(workflowstamplegend);
            }
        });
        // Update images
        List<Stampimage> stampimages = workflowTemplateDesign.getStampimages();
        List<Stampimage> stampimagesDb = stampimages.stream().map(stampimage -> {
            final Integer stampimageId = stampimage.getId();
            if (stampimageId != null) {
                Stampimage stampimageDb = stampimageService.getBy(stampimageId);
                if (States.ACTIVE == stampimage.getActive()) {
                    validatePageInfoImage(stampimage);
                    StampimageAllocator.forUpdate(stampimage, stampimageDb);
                } else {
                    StampimageAllocator.forDelete(stampimageDb);
                }
                return stampimageService.save(stampimageDb);
            } else {
                validatePageInfoImage(stampimage);
                Stampimage stampimage1 = StampimageAllocator.build(stampimage);
                return stampimageService.save(stampimage1);
            }
        }).collect(Collectors.toList());
        List<Workflowstampimage> workflowstampimages = WorkflowstampimageAllocator.build(workflow, stampimagesDb);
        workflowstampimages.stream().forEach(workflowstampimage -> {
            final Workflow workflowId1 = workflowstampimage.getWorkflowByWorkflowId();
            final Stampimage stampimage = workflowstampimage.getStampimageByStampimageId();
            Optional<Workflowstampimage> optionalWSI = workflowstampimageService.findBy(workflowId1, stampimage);
            if (!optionalWSI.isPresent()) {
                workflowstampimageService.save(workflowstampimage);
            }
        });
        // Update qrcodes
        List<Stampqrcode> stampqrcodes = workflowTemplateDesign.getStampqrcodes();
        List<Stampqrcode> stampqrcodesDb = stampqrcodes.stream().map(stampqrcode -> {
            final Integer stampqrcodeId = stampqrcode.getId();
            if (stampqrcodeId != null) {
                Stampqrcode stampqrcodeDb = stampqrcodeService.getBy(stampqrcodeId);
                if (States.ACTIVE == stampqrcode.getActive()) {
                    validatePageInfoQrcode(stampqrcode);
                    StampqrcodeAllocator.forUpdate(stampqrcode, stampqrcodeDb);
                } else {
                    StampqrcodeAllocator.forDelete(stampqrcodeDb);
                }
                return stampqrcodeService.save(stampqrcodeDb);
            } else {
                validatePageInfoQrcode(stampqrcode);
                Stampqrcode stampqrcode1 = StampqrcodeAllocator.build(stampqrcode);
                return stampqrcodeService.save(stampqrcode1);
            }
        }).collect(Collectors.toList());
        List<Workflowstampqrcode> workflowstampqrcodes = WorkflowstampqrcodeAllocator.build(workflow, stampqrcodesDb);
        workflowstampqrcodes.stream().forEach(workflowstampqrcode -> {
            final Workflow workflowId1 = workflowstampqrcode.getWorkflowByWorkflowId();
            final Stampqrcode stampqrcode = workflowstampqrcode.getStampqrcodeByStampqrcodeId();
            Optional<Workflowstampqrcode> optionalWSQR = workflowstampqrcodeService.findBy(workflowId1, stampqrcode);
            if (!optionalWSQR.isPresent()) {
                workflowstampqrcodeService.save(workflowstampqrcode);
            }
        });
        // Update datetimes
        List<Stampdatetime> stampdatetimes = workflowTemplateDesign.getStampdatetimes();
        List<Stampdatetime> stampdatetimesDb = stampdatetimes.stream().map(stampdatetime -> {
            final Integer stampdatetimeId = stampdatetime.getId();
            if (stampdatetimeId != null) {
                Stampdatetime stampdatetimeDb = stampdatetimeService.getBy(stampdatetimeId);
                if (States.ACTIVE == stampdatetime.getActive()) {
                    validatePageInfoDatetime(stampdatetime);
                    StampdatetimeAllocator.forUpdate(stampdatetime, stampdatetimeDb);
                } else {
                    StampdatetimeAllocator.forDelete(stampdatetimeDb);
                }
                return stampdatetimeService.save(stampdatetimeDb);
            } else {
                validatePageInfoDatetime(stampdatetime);
                Stampdatetime stampdatetime1 = StampdatetimeAllocator.build(stampdatetime);
                return stampdatetimeService.save(stampdatetime1);
            }
        }).collect(Collectors.toList());
        List<Workflowstampdatetime> workflowstampdatetimes = WorkflowstampdatetimeAllocator.build(workflow, stampdatetimesDb);
        workflowstampdatetimes.stream().forEach(workflowstampdatetime -> {
            final Workflow workflowId1 = workflowstampdatetime.getWorkflowByWorkflowId();
            final Stampdatetime stampdatetime = workflowstampdatetime.getStampdatetimeByStampdatetimeId();
            Optional<Workflowstampdatetime> optionalWSDT = workflowstampdatetimeService.findBy(workflowId1, stampdatetime);
            if (!optionalWSDT.isPresent()) {
                workflowstampdatetimeService.save(workflowstampdatetime);
            }
        });
        //Update layoutfile
        Stamplayoutfile stamplayoutfile = workflowTemplateDesign.getStamplayoutfile();
        if (stamplayoutfile != null && (stamplayoutfile.getBase64Data().isEmpty() || !stamplayoutfile.getExcelBase64Data().isEmpty())) {
            //Disable actual row
            Integer stamplayoutfileId = stamplayoutfile.getId();
            Stamplayoutfile stamplayoutfileDb = null;
            if (stamplayoutfileId != null) {
                stamplayoutfileDb = stamplayoutfileService.getBy(stamplayoutfileId);
                StamplayoutfileAllocator.forDelete(stamplayoutfileDb);
                stamplayoutfileService.save(stamplayoutfileDb);
            }
            Stamplayoutfile newStamplayoutfile = StamplayoutfileAllocator.forUpdate(workflow, stamplayoutfile, stamplayoutfileDb);
            stamplayoutfileService.save(newStamplayoutfile);
        }
        // Update images
        List<Stamprubric> stamprubrics = workflowTemplateDesign.getStamprubrics();
        List<Stamprubric> stamprubricsDb = stamprubrics.stream().map(stamprubric -> {
            final Integer stamprubricId = stamprubric.getId();
            if (stamprubricId != null) {
                Stamprubric stamprubricDb = stamprubricService.getBy(stamprubricId);
                if (States.ACTIVE == stamprubric.getActive()) {
                    validatePageInfoRubric(stamprubric);
                    StamprubricAllocator.forUpdate(stamprubric, stamprubricDb);
                } else {
                    StamprubricAllocator.forDelete(stamprubricDb);
                }
                return stamprubricService.save(stamprubricDb);
            } else {
                validatePageInfoRubric(stamprubric);
                Stamprubric stamprubric1 = StamprubricAllocator.build(stamprubric);
                return stamprubricService.save(stamprubric1);
            }
        }).collect(Collectors.toList());
        List<Workflowstamprubric> workflowstamprubrics = WorkflowstamprubricAllocator.build(workflow, stamprubricsDb);
        workflowstamprubrics.stream().forEach(workflowstamprubric -> {
            final Workflow workflowId1 = workflowstamprubric.getWorkflowByWorkflowId();
            final Stamprubric stamprubric = workflowstamprubric.getStamprubricByStamprubricId();
            Optional<Workflowstamprubric> optionalWSR = workflowstamprubricService.findBy(workflowId1, stamprubric);
            if (!optionalWSR.isPresent()) {
                workflowstamprubricService.save(workflowstamprubric);
            }
        });
        return workflow;
    }

    public List<Workflow> findAllBy(List<Integer> enterpriseIds) {
        return repository.findAllByEnterpriseIdInAndActiveAndDeleted(enterpriseIds, States.ACTIVE, States.EXISTENT);
    }

    private void validatePageInfoRubric(List<Stamprubric> stamprubrics) {
        stamprubrics.stream().forEach(this::validatePageInfoRubric);
    }

    private void validatePageInfoRubric(Stamprubric stamprubric) {
        int pagestampId = stamprubric.getPageStamp();
        int contentpositionId = stamprubric.getContentPosition();
        //Only for verify
        pagestampService.getBy(pagestampId);
        contentpositionService.getBy(contentpositionId);
    }
}
