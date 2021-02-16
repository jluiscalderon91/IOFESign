package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.config.Global;
import com.apsout.electronictestimony.api.config.ResourceProperties;
import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.exception.FileStorageException;
import com.apsout.electronictestimony.api.exception.ResourceNotFoundException;
import com.apsout.electronictestimony.api.service.*;
import com.apsout.electronictestimony.api.util.date.DateUtil;
import com.apsout.electronictestimony.api.util.enums.ContentPosition;
import com.apsout.electronictestimony.api.util.enums.Pages2Stamp;
import com.apsout.electronictestimony.api.util.file.FileUtil;
import com.apsout.electronictestimony.api.util.image.ImageUtil;
import com.apsout.electronictestimony.api.util.others.FontUtil;
import com.apsout.electronictestimony.api.util.stamp.QRCodeBuilder;
import com.apsout.electronictestimony.api.util.stamp.StamperBuilder;
import com.apsout.electronictestimony.api.util.statics.States;
import com.apsout.electronictestimony.api.util.statics.Replaceable;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfReader;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class StamperServiceImpl implements StamperService {
    private static final Logger logger = LoggerFactory.getLogger(StamperServiceImpl.class);
    private final Path fileStoragePath;

    @Autowired
    private DocumentresourceService documentresourceService;
    @Autowired
    private StamplegendService stamplegendService;
    @Autowired
    private StampimageService stampimageService;
    @Autowired
    private StampqrcodeService stampqrcodeService;
    @Autowired
    private StampdatetimeService stampdatetimeService;
    @Autowired
    private FontsizeService fontsizeService;
    @Autowired
    private FonttypeService fonttypeService;
    @Autowired
    private FontcolorService fontcolorService;
    @Autowired
    private PagestampService pagestampService;
    @Autowired
    private ContentpositionService contentpositionService;
    @Autowired
    private StamprubricService stamprubricService;
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private PersonrubricService personrubricService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private ResourceService resourceService;

    @Autowired
    public StamperServiceImpl(ResourceProperties resourceProperties) {
        fileStoragePath = Paths.get(resourceProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileStoragePath);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    /**
     * @param resource
     * @return A path file with default QR code as graphic representation
     */
//    @Override
//    public Path stamp(Resource resource) {
//        Documentresource documentresource = documentresourceService.getBy(resource);
//        Path srcPath = this.fileStoragePath.resolve(resource.getPath()).normalize();
//        File destFile = null;
//        try {
//            destFile = Files.createTempFile("tmp-", ".pdf").toFile();
//            StamperBuilder stamperBuilder = new StamperBuilder(srcPath.toFile(), destFile);
//            String URLVerifier = new StringBuilder(Global.ROOT_FRONT)
//                    .append("/document/")
//                    .append(documentresource.getDocumentByDocumentId().getHashIdentifier())
//                    .append("/resources/")
//                    .append(resource.getHash())
//                    .append("/verifier").toString();
//            String legend = "Firmado digitalmente";
//            QRCodeBuilder qrCodeBuilder = new QRCodeBuilder(URLVerifier, 80);
//            File imgFile = qrCodeBuilder.getQRCode();
//            stamperBuilder.stampImage(imgFile, 22, 15, Pages2Stamp.ALL);
//            stamperBuilder.stampLegend(legend, 5, 10, 8.0f, Pages2Stamp.ALL);
//            stamperBuilder.closeStreams();
//        } catch (IOException e) {
//            logger.error("Reading for stamping  ", e);
//        } catch (DocumentException e) {
//            logger.error("Stamping document ", e);
//        }
//        return destFile.toPath();
//    }
    @Override
    public Path stamp(Enterprise enterprise, Workflow workflow, Resource resource) {
        Path srcPath = this.fileStoragePath.resolve(resource.getPath()).normalize();
//        Documentresource documentresource = documentresourceService.getBy(resource);
        // TODO por modificar
        return stampBeforeRubric(enterprise, workflow, null, resource, null, false);
    }

//    /**
//     * @param enterprise
//     * @param workflow
//     * @param resource,       Cuando resource sea nulo, se estampa la plantilla de demostración
//     * @param srcPathTemplate
//     * @return
//     */
//    @Override
//    public Path stamp(Enterprise enterprise, Workflow workflow, Resource resource, Path srcPathTemplate) {
//        return null;
        /*
        Path srcPath;
        List<Stamplegend> stamplegends = stamplegendService.findBy(workflow);
        List<Stampimage> stampimages = stampimageService.findBy(workflow);
        List<Stampqrcode> stampqrcodes = stampqrcodeService.findBy(workflow);
        List<Stampdatetime> stampdatetimes = stampdatetimeService.findBy(workflow);
        List<Stamprubric> stamprubrics = stamprubricService.findBy(workflow);
        Documentresource documentresource;
        boolean onlyPreview = resource == null;
        if (!onlyPreview) {
            srcPath = this.fileStoragePath.resolve(resource.getPath()).normalize();
            documentresource = documentresourceService.getBy(resource);
        } else {
            srcPath = srcPathTemplate;
            documentresource = null;
        }
        final boolean isFileWithoutModifications = stamplegends.isEmpty() &&
                stampimages.isEmpty() &&
                stampqrcodes.isEmpty() &&
                stampdatetimes.isEmpty() &&
                stamprubrics.isEmpty();
        if (isFileWithoutModifications) {
            return srcPath;
        }
        File destFile = null;
        try {
            destFile = Files.createTempFile("tmp-", ".pdf").toFile();
            StamperBuilder stamperBuilder = new StamperBuilder(srcPath.toFile(), destFile);
            stamplegends.stream().forEach(stamplegend -> {
                String legend = stamplegend.getDescription();
                float positionX = stamplegend.getPositionX().floatValue();
                float positionY = stamplegend.getPositionY().floatValue();
                float rotation = stamplegend.getRotation().floatValue();
                int fontSizeId = stamplegend.getFontSize();
                int fontTypeId = stamplegend.getFontType();
                int fontColorId = stamplegend.getFontColor();
                int pageStampId = stamplegend.getPageStamp();
                final Integer stampOnRef = stamplegend.getStampOn();
                Fontsize fontsize = fontsizeService.getBy(fontSizeId);
                Fonttype fonttype = fonttypeService.getBy(fontTypeId);
                Fontcolor fontcolor = fontcolorService.getBy(fontColorId);
                Pagestamp pagestamp = pagestampService.getBy(pageStampId);
                final float fontSizeValue = fontsize.getSize().floatValue();
                final String fontTypeValue = fonttype.getDescription();
                final Pages2Stamp pages2StampValue = translate2Pages2Stamp(pagestamp);
                int stampOnValue = 0;
                if (stampOnRef != null) {
                    stampOnValue = stampOnRef.intValue();
                }
                if (hasSomeRegex(legend)) {
                    if (resource != null) {
                        legend = setLegendParams(legend, resource);
                    } else {
                        legend = setLegendParams4Preview(legend);
                    }
                }
                stamperBuilder.stampLegend(legend, positionX, positionY, rotation, fontSizeValue, fontTypeValue, pages2StampValue, stampOnValue);
            });
            stampimages.stream().forEach(stampimage -> {
                byte[] data = stampimage.getData();
                float positionX = stampimage.getPositionX().floatValue();
                float positionY = stampimage.getPositionY().floatValue();
                float percentSize = stampimage.getPercentSize().floatValue();
                float rotation = stampimage.getRotation().floatValue();
                int pageStampId = stampimage.getPageStamp();
                int contentpositionId = stampimage.getContentPosition();
                final Integer stampOnRef = stampimage.getStampOn();
                Pagestamp pagestamp = pagestampService.getBy(pageStampId);
                Contentposition contentposition = contentpositionService.getBy(contentpositionId);
                final Pages2Stamp pages2StampValue = translate2Pages2Stamp(pagestamp);
                final ContentPosition contentPosition = translate2ContentPositionEnum(contentposition);
                int stampOnValue = 0;
                if (stampOnRef != null) {
                    stampOnValue = stampOnRef.intValue();
                }
                File imgFile = FileUtil.write2NewFile(data, "logo-", "." + stampimage.getExtension());
                stamperBuilder.stampImage(imgFile, positionX, positionY, percentSize, rotation, contentPosition, pages2StampValue, stampOnValue);
            });
            stampqrcodes.stream().forEach(stampqrcode -> {
                float positionX = stampqrcode.getPositionX().floatValue();
                float positionY = stampqrcode.getPositionY().floatValue();
                int sideSize = stampqrcode.getSideSize();
                int pageStampId = stampqrcode.getPageStamp();
                final Integer stampOnRef = stampqrcode.getStampOn();
                Pagestamp pagestamp = pagestampService.getBy(pageStampId);
                final Pages2Stamp pages2StampValue = translate2Pages2Stamp(pagestamp);
                int stampOnValue = 0;
                if (stampOnRef != null) {
                    stampOnValue = stampOnRef.intValue();
                }
                File imgFile = null;
                if (resource != null) {
                    imgFile = buildQRCodeImage(documentresource, resource, sideSize);
                } else {
                    imgFile = buildQRCodeImage4Preview(sideSize);
                }
                stamperBuilder.stampImage(imgFile, positionX, positionY, pages2StampValue, stampOnValue);
            });
            boolean previewOptionSelected = resource == null;
            if (previewOptionSelected) {
                stampdatetimes.stream().forEach(stampdatetime -> {
                    String stringDate = DateUtil.build(new Date(), "dd/MM/yyy hh:mm");
                    float positionX = stampdatetime.getPositionX().floatValue();
                    float positionY = stampdatetime.getPositionY().floatValue();
                    final int widthContainer = stampdatetime.getWidthContainer().intValue();
                    final int heightContainer = stampdatetime.getHeightContainer().intValue();
                    final String legend = stampdatetime.getDescription().replace("<DATE>", stringDate);
                    float fontSizeValue = FontUtil.pickOptimalSize(legend, widthContainer, heightContainer);
                    int pageStampId = stampdatetime.getPageStamp();
                    final Integer stampOnRef = stampdatetime.getStampOn();
                    Pagestamp pagestamp = pagestampService.getBy(pageStampId);
                    final Pages2Stamp pages2StampValue = translate2Pages2Stamp(pagestamp);
                    int stampOnValue = 0;
                    if (stampOnRef != null) {
                        stampOnValue = stampOnRef.intValue();
                    }
                    stamperBuilder.stampLegend(legend, positionX, positionY, 0f, fontSizeValue, FontFactory.HELVETICA, pages2StampValue, stampOnValue);
                });
            }
            //TODO cerramos temporalmente el stream
            stamperBuilder.closeStreams();
            srcPath = destFile.toPath();
            */

        /*for (Stamprubric stamprubric : stamprubrics) {
            Participant participant = participantService.getBy(stamprubric.getParticipantId());
            srcPath = stampRubric(documentresource, participant, srcPath, onlyPreview);
            destFile = srcPath.toFile();
        }*/

        /*
            stamprubrics.stream().forEach(stamprubric -> {
                Participant participant = participantService.getBy(stamprubric.getParticipantId());
                Person person = participant.getPersonByPersonId();
                Optional<Personrubric> optional;
                if (States.REPLACEABLE == person.getReplaceable()) {
                    if (!onlyPreview) {
                        Document document = documentresource.getDocumentByDocumentId();
                        Operator operator = operatorService.getBy(document, participant.getOrderParticipant());
                        Person person1 = operator.getPersonByPersonId();
                        optional = personrubricService.findBy(person1);
                    } else {
                        optional = Optional.empty();
                    }
                } else {
                    optional = personrubricService.findBy(person);
                }
                byte[] data;
                String extension;
                if (optional.isPresent()) {
                    final Personrubric personrubric = optional.get();
                    data = personrubric.getData();
                    extension = personrubric.getExtension();
                } else {
                    Path template = FileUtil.copyInputStreamOfResources("default-rubric.png");
                    data = FileUtil.readFileToByteArray(template);
                    extension = FilenameUtils.getExtension(template.getFileName().toString());
                }
                float positionX = stamprubric.getPositionX().floatValue();
                float positionY = stamprubric.getPositionY().floatValue();
                float rotation = stamprubric.getRotation().floatValue();
                int pageStampId = stamprubric.getPageStamp();
                int contentpositionId = stamprubric.getContentPosition();
                final Integer stampOnRef = stamprubric.getStampOn();
                Pagestamp pagestamp = pagestampService.getBy(pageStampId);
                Contentposition contentposition = contentpositionService.getBy(contentpositionId);
                final Pages2Stamp pages2StampValue = translate2Pages2Stamp(pagestamp);
                final ContentPosition contentPosition = translate2ContentPositionEnum(contentposition);
                float percentSize;
                int stampOnValue = 0;
                if (stampOnRef != null) {
                    stampOnValue = stampOnRef.intValue();
                }
                File imgFile = FileUtil.write2NewFile(data, "rubric-", "." + extension);
                if (States.REPLACEABLE == person.getReplaceable()) {
                    BufferedImage sourceBufferedImage = ImageUtil.read(imgFile);
                    float originWidth = sourceBufferedImage.getWidth();
                    float originHeight = sourceBufferedImage.getHeight();
                    float positionXf = stamprubric.getPositionXf().floatValue();
                    float positionYf = stamprubric.getPositionYf().floatValue();
                    float targetHeight = this.calcSideRubric(positionY, positionYf);
                    float targetWidth = this.calcSideRubric(positionX, positionXf);
                    File referenceFile = this.resizeImage(imgFile, targetWidth, targetHeight, onlyPreview);
                    BufferedImage referenceBufferedImage = ImageUtil.read(referenceFile);
                    float referenceHeight = referenceBufferedImage.getHeight();
                    float referenceWidth = referenceBufferedImage.getWidth();
                    positionX += (targetWidth - referenceWidth) / 2;
                    positionY += (targetHeight - referenceHeight) / 2;
                    percentSize = (referenceHeight / originHeight + referenceWidth / originWidth) / 2;
                    if (onlyPreview) {
                        stamperBuilder.stampImage(referenceFile, positionX, positionY, 1, rotation, contentPosition, pages2StampValue, stampOnValue);
                    } else {
                        stamperBuilder.stampImage(imgFile, positionX, positionY, percentSize, rotation, contentPosition, pages2StampValue, stampOnValue);
                    }
                } else {
                    percentSize = stamprubric.getPercentSize().floatValue();
                    stamperBuilder.stampImage(imgFile, positionX, positionY, percentSize, rotation, contentPosition, pages2StampValue, stampOnValue);
                }
            });
            stamperBuilder.closeStreams();
        */

   /* } catch(
    IOException e)

    {
        logger.error(String.format("Reading for stamping for workflowId: %d", workflow.getId()), e);
    } catch(
    DocumentException e)

    {
        logger.error(String.format("Stamping document for workflowId: %d", workflow.getId()), e);
    }
        return destFile.toPath();
    */
//    }

    private float calcSideRubric(float initPosition, float finalPosition) {
        if (finalPosition < initPosition) {
            return 1;
        }
        return finalPosition - initPosition;
    }

    public File resizeImage(File originalImagefile, float targetWidth, float targetHeight, boolean isOnlyPreview) {
        if (!isOnlyPreview) {
            BufferedImage originalImage = ImageUtil.read(originalImagefile);
            int originalX = originalImage.getWidth();
            int originalY = originalImage.getHeight();
            Scalr.Mode mode = originalX > originalY ? Scalr.Mode.FIT_TO_WIDTH : Scalr.Mode.FIT_TO_HEIGHT;
            return ImageUtil.resize(originalImagefile, Scalr.Method.AUTOMATIC, mode, targetWidth, targetHeight);
        } else {
            return ImageUtil.resize(originalImagefile, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT, targetWidth, targetHeight);
        }
    }

    private boolean hasSomeRegex(String legend) {
        return legend.contains(Replaceable.RegexResourceHash) ||
                legend.contains(Replaceable.RegexDate) ||
                legend.contains(Replaceable.RegexTime) ||
                legend.contains(Replaceable.RegexDatetime);
    }

    public Pages2Stamp translate2Pages2Stamp(Pagestamp pagestamp) {
        switch (pagestamp.getId()) {
            case 1:
                return Pages2Stamp.FIRST;
            case 2:
                return Pages2Stamp.PRE_LAST;
            case 3:
                return Pages2Stamp.LAST;
            case 6:
                return Pages2Stamp.EVEN;
            case 7:
                return Pages2Stamp.ODD;
            case 4:
                return Pages2Stamp.ALL;
            case 8:
                return Pages2Stamp.SINCE;
            case 9:
                return Pages2Stamp.UNTIL;
            default:
                return Pages2Stamp.OTHER;
        }
    }

    private ContentPosition translate2ContentPositionEnum(Contentposition contentposition) {
        switch (contentposition.getId()) {
            case 1:
                return ContentPosition.OVER;
            default:
                return ContentPosition.UNDER;
        }
    }

    private File buildQRCodeImage(Document document, Resource resource, int sideSize) {
        String URLVerifier = resourceService.buildURLVerifier(document, resource);
        QRCodeBuilder qrCodeBuilder = new QRCodeBuilder(URLVerifier, sideSize);
        return qrCodeBuilder.getQRCode();
    }

    private File buildQRCodeImage4Preview(int sideSize) {
        String URLVerifier = new StringBuilder(Global.ROOT_FRONT)
                .append("/document/")
                .append("01e2a067bd2acfc4795ce5674ac83609b4fe29efa9ba8cb3ba121c3e72ba4547")
                .append("/resources/")
                .append("3135e69a5becef5f")
                .append("/verifier").toString();
        QRCodeBuilder qrCodeBuilder = new QRCodeBuilder(URLVerifier, sideSize);
        return qrCodeBuilder.getQRCode();
    }

    private String setLegendParams(String legend, Resource resource) {
        String rep1 = setLegendParamsOfDate(legend);
        return rep1.replaceAll(Pattern.quote(Replaceable.RegexResourceHash), resource.getResumeHash());
    }

    private String setLegendParams4Preview(String legend) {
        String rep1 = setLegendParamsOfDate(legend);
        return rep1.replaceAll(Pattern.quote(Replaceable.RegexResourceHash), "b8857e6ac7");
    }

    private String setLegendParamsOfDate(String legend) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = LocalDate.now().format(formatter);
        formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = LocalTime.now().format(formatter);
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");
        String datetime = LocalDateTime.now().format(formatter);
        return legend.replaceAll(Pattern.quote(Replaceable.RegexDate), date)
                .replaceAll(Pattern.quote(Replaceable.RegexTime), time)
                .replaceAll(Pattern.quote(Replaceable.RegexDatetime), datetime);
    }

    public Integer getNumberPages(Path filePath) {
        String stringPathSource = "";
        try {
            stringPathSource = filePath.toString();
            PdfReader reader = new PdfReader(stringPathSource);
            return reader.getNumberOfPages();
        } catch (IOException e) {
            throw new RuntimeException(String.format("Loading file: %s", stringPathSource), e);
        }
    }

    public Path stampRubric(Document document, Participant participant, Path srcPath, boolean preview) {
        try {
            File sourceFile = this.fileStoragePath.resolve(srcPath).normalize().toFile();
            File destFile = FileUtil.createTempFile("tmp-", ".pdf").toFile();
            StamperBuilder stamperBuilder = new StamperBuilder(sourceFile, destFile);
            Person person = participant.getPersonByPersonId();
            Optional<Stamprubric> optionalStamprubric = stamprubricService.findBy(participant);
            if (optionalStamprubric.isPresent()) {
                Optional<Personrubric> optionalPersonrubric = personrubricService.findBy(person);
//                Documentresource documentresource = documentresourceService.getBy(srcPath);
                Stamprubric stamprubric = optionalStamprubric.get();
                final boolean isInvited = States.REPLACEABLE == person.getReplaceable();
                if (isInvited) {
                    if (preview) {
                        optionalPersonrubric = Optional.empty();
                    } else {
//                        Document document = documentresource.getDocumentByDocumentId();
                        Operator operator = operatorService.getBy(document, participant.getOrderParticipant());
                        Person person2Replace = operator.getPersonByPersonId();
                        optionalPersonrubric = personrubricService.findBy(person2Replace);
                    }
                } else {
                    optionalPersonrubric = personrubricService.findBy(person);
                }
                byte[] data;
                String extension;
                if (optionalPersonrubric.isPresent()) {
                    final Personrubric personrubric = optionalPersonrubric.get();
                    data = personrubric.getData();
                    extension = personrubric.getExtension();
                } else {
                    Path template = FileUtil.copyInputStreamOfResources("default-rubric.png");
                    data = FileUtil.readFileToByteArray(template);
                    extension = FilenameUtils.getExtension(template.getFileName().toString());
                }

                float positionX = stamprubric.getPositionX().floatValue();
                float positionY = stamprubric.getPositionY().floatValue();
                float rotation = stamprubric.getRotation().floatValue();
                int pageStampId = stamprubric.getPageStamp();
                int contentpositionId = stamprubric.getContentPosition();
                final Integer stampOnRef = stamprubric.getStampOn();
                final Pagestamp pagestamp = pagestampService.getBy(pageStampId);
                final Contentposition contentposition = contentpositionService.getBy(contentpositionId);
                final Pages2Stamp pages2StampValue = translate2Pages2Stamp(pagestamp);
                final ContentPosition contentPosition = translate2ContentPositionEnum(contentposition);
                float percentSize;
                int stampOnValue = 0;
                if (stampOnRef != null) {
                    stampOnValue = stampOnRef.intValue();
                }
                File imgFile = FileUtil.write2NewFile(data, "rubric-", "." + extension);
                if (isInvited) {
                    BufferedImage sourceBufferedImage = ImageUtil.read(imgFile);
                    float originWidth = sourceBufferedImage.getWidth();
                    float originHeight = sourceBufferedImage.getHeight();
                    float positionXf = stamprubric.getPositionXf().floatValue();
                    float positionYf = stamprubric.getPositionYf().floatValue();
                    float targetHeight = this.calcSideRubric(positionY, positionYf);
                    float targetWidth = this.calcSideRubric(positionX, positionXf);
                    File referenceFile;
                    if (preview) {
                        referenceFile = ImageUtil.resize(imgFile, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT, targetWidth, targetHeight);
                        BufferedImage bufResizedImage = ImageUtil.read(referenceFile);
                        float referenceHeight = bufResizedImage.getHeight();
                        float referenceWidth = bufResizedImage.getWidth();
                        positionX += (targetWidth - referenceWidth) / 2;
                        positionY += (targetHeight - referenceHeight) / 2;
                        stamperBuilder.stampImage(referenceFile, positionX, positionY, 1, rotation, contentPosition, pages2StampValue, stampOnValue);
                    } else {
                        BufferedImage bufOriginalImage = ImageUtil.read(imgFile);
                        int originalX = bufOriginalImage.getWidth();
                        int originalY = bufOriginalImage.getHeight();
                        Scalr.Mode mode = originalX > originalY ? Scalr.Mode.FIT_TO_WIDTH : Scalr.Mode.FIT_TO_HEIGHT;
                        referenceFile = ImageUtil.resize(imgFile, Scalr.Method.AUTOMATIC, mode, targetWidth, targetHeight);
                        BufferedImage bufResizedImage = ImageUtil.read(referenceFile);
                        float referenceHeight = bufResizedImage.getHeight();
                        float referenceWidth = bufResizedImage.getWidth();
                        positionX += (targetWidth - referenceWidth) / 2;
                        positionY += (targetHeight - referenceHeight) / 2;
                        percentSize = (referenceHeight / originHeight + referenceWidth / originWidth) / 2;
                        stamperBuilder.stampImage(imgFile, positionX, positionY, percentSize, rotation, contentPosition, pages2StampValue, stampOnValue);
                    }
//                File referenceFile = this.resizeImage(imgFile, targetWidth, targetHeight, preview);

//                BufferedImage bufResizedImage = ImageUtil.read(referenceFile);
//                float referenceHeight = bufResizedImage.getHeight();
//                float referenceWidth = bufResizedImage.getWidth();
//                positionX += (targetWidth - referenceWidth) / 2;
//                positionY += (targetHeight - referenceHeight) / 2;
//                percentSize = (referenceHeight / originHeight + referenceWidth / originWidth) / 2;

//
//                if (preview) {
//                    stamperBuilder.stampImage(referenceFile, positionX, positionY, 1, rotation, contentPosition, pages2StampValue, stampOnValue);
//                } else {
//                    stamperBuilder.stampImage(imgFile, positionX, positionY, percentSize, rotation, contentPosition, pages2StampValue, stampOnValue);
//                }
                } else {
                    percentSize = stamprubric.getPercentSize().floatValue();
                    stamperBuilder.stampImage(imgFile, positionX, positionY, percentSize, rotation, contentPosition, pages2StampValue, stampOnValue);
                }
                stamperBuilder.closeStreams();
            } else {
                destFile = srcPath.toFile();
            }
            return destFile.toPath();
        } catch (IOException | DocumentException e) {
            throw new RuntimeException("Stamping rubric", e);
        }
    }

    public Path stampBeforeRubric(Enterprise enterprise, Workflow workflow, Document document, Resource resource, Path srcPathPdfTemplate, boolean preview) {
        List<Stamplegend> stamplegends = stamplegendService.findBy(workflow);
        List<Stampimage> stampimages = stampimageService.findBy(workflow);
        List<Stampqrcode> stampqrcodes = stampqrcodeService.findBy(workflow);
        List<Stampdatetime> stampdatetimes = stampdatetimeService.findBy(workflow);
//        List<Stamprubric> stamprubrics = stamprubricService.findBy(workflow);
//        Documentresource documentresource;
//        boolean onlyPreview = resource == null;
        Path srcPath = preview ? srcPathPdfTemplate : this.fileStoragePath.resolve(resource.getPath()).normalize();
//        if (preview) {
//            srcPath = srcPathTemplate;
////            documentresource = null;
//        } else {
//            srcPath = this.fileStoragePath.resolve(resource.getPath()).normalize();
////            documentresource = documentresourceService.getBy(resource);
//        }
        final boolean isFileWithoutModifications = stamplegends.isEmpty() &&
                stampimages.isEmpty() &&
                stampqrcodes.isEmpty() &&
                stampdatetimes.isEmpty();
//                stamprubrics.isEmpty();
        if (isFileWithoutModifications) {
            return srcPath;
        }
        File destFile = null;
        try {
            destFile = Files.createTempFile("tmp-", ".pdf").toFile();
            StamperBuilder stamperBuilder = new StamperBuilder(srcPath.toFile(), destFile);
            stamplegends.stream().forEach(stamplegend -> {
                String legend = stamplegend.getDescription();
                float positionX = stamplegend.getPositionX().floatValue();
                float positionY = stamplegend.getPositionY().floatValue();
                float rotation = stamplegend.getRotation().floatValue();
                int fontSizeId = stamplegend.getFontSize();
                int fontTypeId = stamplegend.getFontType();
                int fontColorId = stamplegend.getFontColor();
                int pageStampId = stamplegend.getPageStamp();
                final Integer stampOnRef = stamplegend.getStampOn();
                Fontsize fontsize = fontsizeService.getBy(fontSizeId);
                Fonttype fonttype = fonttypeService.getBy(fontTypeId);
                Fontcolor fontcolor = fontcolorService.getBy(fontColorId);
                Pagestamp pagestamp = pagestampService.getBy(pageStampId);
                final float fontSizeValue = fontsize.getSize().floatValue();
                final String fontTypeValue = fonttype.getDescription();
                final Pages2Stamp pages2StampValue = translate2Pages2Stamp(pagestamp);
                int stampOnValue = 0;
                if (stampOnRef != null) {
                    stampOnValue = stampOnRef.intValue();
                }
                if (hasSomeRegex(legend)) {
                    legend = preview ? setLegendParams4Preview(legend) : setLegendParams(legend, resource);
//                    if (preview) {
//                        legend = setLegendParams4Preview(legend);
//                    } else {
//                        legend = setLegendParams(legend, resource);
//                    }
                }
                stamperBuilder.stampLegend(legend, positionX, positionY, rotation, fontSizeValue, fontTypeValue, pages2StampValue, stampOnValue);
            });
            stampimages.stream().forEach(stampimage -> {
                byte[] data = stampimage.getData();
                float positionX = stampimage.getPositionX().floatValue();
                float positionY = stampimage.getPositionY().floatValue();
                float percentSize = stampimage.getPercentSize().floatValue();
                float rotation = stampimage.getRotation().floatValue();
                int pageStampId = stampimage.getPageStamp();
                int contentpositionId = stampimage.getContentPosition();
                final Integer stampOnRef = stampimage.getStampOn();
                Pagestamp pagestamp = pagestampService.getBy(pageStampId);
                Contentposition contentposition = contentpositionService.getBy(contentpositionId);
                final Pages2Stamp pages2StampValue = translate2Pages2Stamp(pagestamp);
                final ContentPosition contentPosition = translate2ContentPositionEnum(contentposition);
                int stampOnValue = 0;
                if (stampOnRef != null) {
                    stampOnValue = stampOnRef.intValue();
                }
                File imgFile = FileUtil.write2NewFile(data, "logo-", "." + stampimage.getExtension());
                stamperBuilder.stampImage(imgFile, positionX, positionY, percentSize, rotation, contentPosition, pages2StampValue, stampOnValue);
            });
            stampqrcodes.stream().forEach(stampqrcode -> {
                float positionX = stampqrcode.getPositionX().floatValue();
                float positionY = stampqrcode.getPositionY().floatValue();
                int sideSize = stampqrcode.getSideSize();
                int pageStampId = stampqrcode.getPageStamp();
                final Integer stampOnRef = stampqrcode.getStampOn();
                Pagestamp pagestamp = pagestampService.getBy(pageStampId);
                final Pages2Stamp pages2StampValue = translate2Pages2Stamp(pagestamp);
                int stampOnValue = 0;
                if (stampOnRef != null) {
                    stampOnValue = stampOnRef.intValue();
                }
                File imgFile = preview ? buildQRCodeImage4Preview(sideSize) : buildQRCodeImage(document, resource, sideSize);
//                if (preview) {
//                    imgFile = buildQRCodeImage4Preview(sideSize);
//                } else {
//                    imgFile = buildQRCodeImage(document, resource, sideSize);
//                }
                stamperBuilder.stampImage(imgFile, positionX, positionY, pages2StampValue, stampOnValue);
            });
            boolean previewOptionSelected = resource == null;
            if (previewOptionSelected) {
                stampdatetimes.stream().forEach(stampdatetime -> {
                    String stringDate = DateUtil.build(new Date(), "dd/MM/yyy hh:mm");
                    float positionX = stampdatetime.getPositionX().floatValue();
                    float positionY = stampdatetime.getPositionY().floatValue();
                    final int widthContainer = stampdatetime.getWidthContainer().intValue();
                    final int heightContainer = stampdatetime.getHeightContainer().intValue();
                    final String legend = stampdatetime.getDescription().replace("<DATE>", stringDate);
                    float fontSizeValue = FontUtil.pickOptimalSize(legend, widthContainer, heightContainer);
                    int pageStampId = stampdatetime.getPageStamp();
                    final Integer stampOnRef = stampdatetime.getStampOn();
                    Pagestamp pagestamp = pagestampService.getBy(pageStampId);
                    final Pages2Stamp pages2StampValue = translate2Pages2Stamp(pagestamp);
                    int stampOnValue = 0;
                    if (stampOnRef != null) {
                        stampOnValue = stampOnRef.intValue();
                    }
                    stamperBuilder.stampLegend(legend, positionX, positionY, 0f, fontSizeValue, FontFactory.HELVETICA, pages2StampValue, stampOnValue);
                });
            }
            //TODO cerramos temporalmente el stream
            stamperBuilder.closeStreams();
        } catch (IOException e) {
            logger.error(String.format("Reading for stamping for workflowId: %d", workflow.getId()), e);
        } catch (DocumentException e) {
            logger.error(String.format("Stamping document for workflowId: %d", workflow.getId()), e);
        }
        return destFile.toPath();
    }

    public Path stampAllElements(Enterprise enterprise, Workflow workflow, Resource resource, Path srcPathPdfTemplate, boolean preview) {
        File destFile = FileUtil.createTempFile("tmp-", ".pdf").toFile();
        Document document = preview ? null : documentresourceService.getBy(resource).getDocumentByDocumentId();
        Path srcPath = this.stampBeforeRubric(enterprise, workflow, document, resource, srcPathPdfTemplate, preview);
        List<Stamprubric> stamprubrics = stamprubricService.findBy(workflow);
        if (stamprubrics.isEmpty()) {
//            return this.fileStoragePath.resolve(resource.getPath()).normalize();
            return srcPath;
        }
        for (Stamprubric stamprubric : stamprubrics) {
            Participant participant = participantService.getBy(stamprubric.getParticipantId());
            srcPath = stampRubric(document, participant, srcPath, preview);
            destFile = srcPath.toFile();
        }
        return destFile.toPath();
    }
}
