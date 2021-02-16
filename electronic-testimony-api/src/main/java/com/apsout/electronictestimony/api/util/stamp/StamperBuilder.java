package com.apsout.electronictestimony.api.util.stamp;

import com.apsout.electronictestimony.api.util.enums.ContentPosition;
import com.apsout.electronictestimony.api.util.enums.Pages2Stamp;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StamperBuilder {
    private static final Logger logger = LoggerFactory.getLogger(StamperBuilder.class);

    private File inputFile;
    private File outputFile;
    private int numberOfPages;
    private PdfReader reader;
    private PdfStamper stamper;

    public StamperBuilder(File inputFile, File outputFile) throws IOException, DocumentException {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        reader = new PdfReader(this.inputFile.getAbsolutePath());
        numberOfPages = reader.getNumberOfPages();
        stamper = new PdfStamper(reader, new FileOutputStream(this.outputFile));
    }

    public void stampImage(File srcImg, float positionX, float positionY, Pages2Stamp pages2Stamp) {
        if (Pages2Stamp.OTHER == pages2Stamp) {
            pages2Stamp = Pages2Stamp.ALL;
        }
        stampImage(srcImg, positionX, positionY, 1.0f, 0f, ContentPosition.OVER, pages2Stamp, 0);
    }

    public void stampImage(File srcImg, float positionX, float positionY, Pages2Stamp pages2Stamp, int pageNumber2Stamp) {
        stampImage(srcImg, positionX, positionY, 1.0f, 0f, ContentPosition.OVER, pages2Stamp, pageNumber2Stamp);
    }

    public void stampImage(File srcImg, float positionX, float positionY, float percentSize, float rotation, ContentPosition contentPosition, Pages2Stamp pages2Stamp, int pageNumber2Stamp) {
        Image image = null;
        try {
            image = Image.getInstance(srcImg.getAbsolutePath());
        } catch (BadElementException | IOException e) {
            logger.error("Obtaining an instance of a saved image", e);
        }
        stampImage(image, positionX, positionY, percentSize, contentPosition, pages2Stamp, pageNumber2Stamp);
    }

    public void stampImage(Image image, float positionX, float positionY, float percentSize, ContentPosition contentPosition, Pages2Stamp pages2Stamp, int pageNumber2Stamp) {
        boolean isOverPosition = ContentPosition.OVER == contentPosition;
        PdfContentByte pdfContentByte = null;
        try {
            switch (pages2Stamp) {
                case FIRST:
                    pdfContentByte = isOverPosition ? stamper.getOverContent(1) : stamper.getUnderContent(1);
                    pdfContentByte.addImage(image, image.getWidth() * percentSize, 0, 0, image.getHeight() * percentSize, positionX, positionY);
                    break;
                case PRE_LAST:
                    for (int index = 1; index < numberOfPages; index++) {
                        pdfContentByte = isOverPosition ? stamper.getOverContent(index) : stamper.getUnderContent(index);
                        pdfContentByte.addImage(image, image.getWidth() * percentSize, 0, 0, image.getHeight() * percentSize, positionX, positionY);
                    }
                    break;
                case LAST:
                    pdfContentByte = isOverPosition ? stamper.getOverContent(numberOfPages) : stamper.getUnderContent(numberOfPages);
                    pdfContentByte.addImage(image, image.getWidth() * percentSize, 0, 0, image.getHeight() * percentSize, positionX, positionY);
                    break;
                case EVEN:
                    for (int index = 1; index < numberOfPages + 1; index++) {
                        if (index % 2 == 0) {
                            pdfContentByte = isOverPosition ? stamper.getOverContent(index) : stamper.getUnderContent(index);
                            pdfContentByte.addImage(image, image.getWidth() * percentSize, 0, 0, image.getHeight() * percentSize, positionX, positionY);
                        }
                    }
                    break;
                case ODD:
                    for (int index = 1; index < numberOfPages + 1; index++) {
                        if (index % 2 != 0) {
                            pdfContentByte = isOverPosition ? stamper.getOverContent(index) : stamper.getUnderContent(index);
                            pdfContentByte.addImage(image, image.getWidth() * percentSize, 0, 0, image.getHeight() * percentSize, positionX, positionY);
                        }
                    }
                    break;
                case ALL:
                    for (int index = 1; index < numberOfPages + 1; index++) {
                        pdfContentByte = isOverPosition ? stamper.getOverContent(index) : stamper.getUnderContent(index);
                        pdfContentByte.addImage(image, image.getWidth() * percentSize, 0, 0, image.getHeight() * percentSize, positionX, positionY);
                    }
                    break;
                case SINCE: {
                    if (numberOfPages < pageNumber2Stamp) {
                        pageNumber2Stamp = numberOfPages;
                    }
                    for (int index = pageNumber2Stamp; index < numberOfPages + 1; index++) {
                        pdfContentByte = isOverPosition ? stamper.getOverContent(index) : stamper.getUnderContent(index);
                        pdfContentByte.addImage(image, image.getWidth() * percentSize, 0, 0, image.getHeight() * percentSize, positionX, positionY);
                    }
                    break;
                }
                case UNTIL: {
                    if (numberOfPages < pageNumber2Stamp) {
                        pageNumber2Stamp = numberOfPages;
                    }
                    for (int index = 1; index < pageNumber2Stamp + 1; index++) {
                        pdfContentByte = isOverPosition ? stamper.getOverContent(index) : stamper.getUnderContent(index);
                        pdfContentByte.addImage(image, image.getWidth() * percentSize, 0, 0, image.getHeight() * percentSize, positionX, positionY);
                    }
                    break;
                }
                default:
                    if (numberOfPages < pageNumber2Stamp) {
                        pageNumber2Stamp = numberOfPages;
                    }
                    pdfContentByte = isOverPosition ? stamper.getOverContent(pageNumber2Stamp) : stamper.getUnderContent(pageNumber2Stamp);
                    pdfContentByte.addImage(image, image.getWidth() * percentSize, 0, 0, image.getHeight() * percentSize, positionX, positionY);
                    break;
            }
        } catch (DocumentException e) {
            logger.error("Stamping image", e);
        }
        logger.info("Image stamp -> Ok! " + inputFile.getName());
    }

    public void stampLegend(String legend, float positionX, float positionY, float size, Pages2Stamp pages2Stamp) {
        stampLegend(legend, positionX, positionY, 0, size, FontFactory.COURIER, pages2Stamp);
    }

    public void stampLegend(String legend, float positionX, float positionY, float rotation, float size, String typeFont, Pages2Stamp pages2Stamp) {
        Phrase phrase = new Phrase(0f, legend, FontFactory.getFont(typeFont, size));
        PdfContentByte contentByte;
        switch (pages2Stamp) {
            case FIRST:
                int pageNum = 1;
                contentByte = stamper.getOverContent(pageNum);
                ColumnText.showTextAligned(contentByte,
                        Element.ALIGN_LEFT,
                        phrase,
                        positionX,
                        positionY,
                        rotation);
                break;
            case LAST:
                contentByte = stamper.getOverContent(numberOfPages);
                ColumnText.showTextAligned(contentByte,
                        Element.ALIGN_LEFT,
                        phrase,
                        positionX,
                        positionY,
                        rotation);
                break;
            case PRE_LAST:
                for (int index = 1; index < numberOfPages; index++) {
                    contentByte = stamper.getOverContent(index);
                    ColumnText.showTextAligned(contentByte,
                            Element.ALIGN_LEFT,
                            phrase,
                            positionX,
                            positionY,
                            rotation);
                }
                break;
            default:
                for (int index = 1; index < numberOfPages + 1; index++) {
                    contentByte = stamper.getOverContent(index);
                    ColumnText.showTextAligned(contentByte,
                            Element.ALIGN_LEFT,
                            phrase,
                            positionX,
                            positionY,
                            rotation);
                }
                break;
        }
    }

    public void stampLegend(String legend, float positionX, float positionY, float rotation, float size, String typeFont, Pages2Stamp pages2Stamp, int pageNumber2Stamp) {
        if (Pages2Stamp.OTHER != pages2Stamp && Pages2Stamp.SINCE != pages2Stamp && Pages2Stamp.UNTIL != pages2Stamp) {
            stampLegend(legend, positionX, positionY, rotation, size, typeFont, pages2Stamp);
        }
        Phrase phrase = new Phrase(0f, legend, FontFactory.getFont(typeFont, size));
        PdfContentByte contentByte;
        if (numberOfPages < pageNumber2Stamp) {
            pageNumber2Stamp = numberOfPages;
        }
        switch (pages2Stamp) {
            case SINCE:
                for (int index = pageNumber2Stamp; index < numberOfPages + 1; index++) {
                    contentByte = stamper.getOverContent(index);
                    ColumnText.showTextAligned(contentByte,
                            Element.ALIGN_LEFT,
                            phrase,
                            positionX,
                            positionY,
                            rotation);
                }
                break;
            case UNTIL:
                for (int index = 1; index < pageNumber2Stamp + 1; index++) {
                    contentByte = stamper.getOverContent(index);
                    ColumnText.showTextAligned(contentByte,
                            Element.ALIGN_LEFT,
                            phrase,
                            positionX,
                            positionY,
                            rotation);
                }
                break;
            case OTHER:
                contentByte = stamper.getOverContent(pageNumber2Stamp);
                ColumnText.showTextAligned(contentByte,
                        Element.ALIGN_LEFT,
                        phrase,
                        positionX,
                        positionY,
                        rotation);
                break;
        }
    }

    public void closeStreams() throws IOException, DocumentException {
        stamper.close();
        reader.close();
        logger.info("Close stamp stream -> Ok!");
    }
}
