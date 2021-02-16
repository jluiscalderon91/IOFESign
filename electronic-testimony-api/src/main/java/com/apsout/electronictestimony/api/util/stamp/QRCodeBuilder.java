package com.apsout.electronictestimony.api.util.stamp;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.qrcode.EncodeHintType;
import com.itextpdf.text.pdf.qrcode.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class QRCodeBuilder {
    private static final Logger logger = LoggerFactory.getLogger(QRCodeBuilder.class);

    private String content;
    private int widht;
    private int height;

    public QRCodeBuilder(String content, int width, int height) {
        this.content = content;
        this.widht = width;
        this.height = height;
    }

    public QRCodeBuilder(String content, int sideSize) {
        this(content, sideSize, sideSize);
    }

    public File getQRCode() {
        return buildImgFile(this.content, this.widht, this.height);
    }

    private File buildImgFile(String content, int width, int height) {
        Map<EncodeHintType, Object> params = new HashMap<EncodeHintType, Object>();
        params.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        params.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BarcodeQRCode qrCode = new BarcodeQRCode(content, width, height, params);
        try {
            File srcImg = extractQrfileOf(qrCode);
            File croppedImg = Files.createTempFile("img-dest-", ".png").toFile();
            cropQrImgFile(srcImg, croppedImg);
            return croppedImg;
        } catch (BadElementException | IOException e) {
            logger.error("Generating QR code.", e);
            return null;
        }
    }

    private File extractQrfileOf(BarcodeQRCode barcode) throws BadElementException, IOException {
        Image awtImage = barcode.createAwtImage(Color.BLACK, Color.WHITE);
        int width = (int) barcode.getImage().getWidth();
        int height = (int) barcode.getImage().getHeight();
        BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bImage.createGraphics();
        g.drawImage(awtImage, 0, 0, null);
        g.dispose();
        File srcImg = Files.createTempFile("img-src-", ".png").toFile();
        ImageIO.write(bImage, "png", srcImg);
        return srcImg;
    }

    private void cropQrImgFile(File src, File dest) throws IOException {
        // TODO improvement this method with percents
        BufferedImage originalImage = ImageIO.read(src);
        int originalHeight = originalImage.getHeight() <= 57 ? 57 : originalImage.getHeight();
        int originalWidth = originalImage.getWidth() <= 57 ? 57 : originalImage.getWidth();
        int xCoordUpperLeft;
        int yCoordUpperLeft;
        int targetWidth;
        int targetHeight;
        if (originalWidth <= 57) {
            ImageIO.write(originalImage, "png", dest);
            return;
        } else if (originalWidth <= 70) {
            xCoordUpperLeft = 6;
            yCoordUpperLeft = 6;
            targetWidth = originalWidth - 12;
            targetHeight = originalHeight - 12;
        } else if (originalWidth <= 85) {
            xCoordUpperLeft = 12;
            yCoordUpperLeft = 12;
            targetWidth = originalWidth - 24;
            targetHeight = originalHeight - 24;
        } else if (originalWidth <= 95) {
            xCoordUpperLeft = 16;
            yCoordUpperLeft = 16;
            targetWidth = originalWidth - 36;
            targetHeight = originalHeight - 36;
        } else if (originalWidth <= 410) {
            xCoordUpperLeft = 24;
            yCoordUpperLeft = 24;
            targetWidth = originalWidth - 49;
            targetHeight = originalHeight - 49;
        } else if (originalWidth <= 810) {
            xCoordUpperLeft = 48;
            yCoordUpperLeft = 48;
            targetWidth = originalWidth - 96;
            targetHeight = originalHeight - 96;
        } else {
            xCoordUpperLeft = 60;
            yCoordUpperLeft = 60;
            targetWidth = originalWidth - 120;
            targetHeight = originalHeight - 120;
        }
        BufferedImage croppedImage = originalImage.getSubimage(xCoordUpperLeft, yCoordUpperLeft, targetWidth, targetHeight);
        ImageIO.write(croppedImage, "png", dest);
    }
}
