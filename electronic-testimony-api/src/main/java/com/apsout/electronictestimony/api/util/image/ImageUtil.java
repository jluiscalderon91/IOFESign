package com.apsout.electronictestimony.api.util.image;

import com.apsout.electronictestimony.api.util.file.FileUtil;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {

    private ImageUtil() {
    }

    public static File resize(File originalImagefile, Scalr.Method method, Scalr.Mode mode, float targetWidth, float targetHeight) {
        String extension = FilenameUtils.getExtension(originalImagefile.getName());
            BufferedImage originalImage = ImageUtil.read(originalImagefile);
        try {
            BufferedImage bufferedImage = Scalr.resize(originalImage, method, mode, (int) targetWidth, (int) targetHeight);
            File outputfile = FileUtil.createTempFile("temp-img-", "." + extension).toFile();
            ImageIO.write(bufferedImage, extension, outputfile);
            return outputfile;
        } catch (IOException e) {
            throw new RuntimeException("Reading file to resize or writing resized file", e);
        }
    }

    public static BufferedImage read(File image) {
        try {
            return ImageIO.read(image);
        } catch (IOException e) {
            throw new RuntimeException("Reading image file", e);
        }
    }
}
