package com.apsout.electronictestimony.api.util.file;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Compressor {
    private static final Logger logger = LoggerFactory.getLogger(Compressor.class);

    public static Path zip(List<File> srcFiles, Path targetZippedFile) {
        HashMap<String, Integer> map = new HashMap<>();
        try (FileOutputStream fos = new FileOutputStream(targetZippedFile.toFile());
             ZipOutputStream zipOut = new ZipOutputStream(fos);) {
            for (File file2Zip : srcFiles) {
                String nameX = buildEntryZipName(map, file2Zip);
                FileInputStream fis = new FileInputStream(file2Zip);
                ZipEntry zipEntry = new ZipEntry(nameX);
                zipOut.putNextEntry(zipEntry);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                fis.close();
            }
            return targetZippedFile;
        } catch (IOException e) {
            logger.error(String.format("Creating zip temp file with name: %s", targetZippedFile), e);
        } finally {
            map.clear();
        }
        return null;
    }

    public static Path zip(List<File> srcFiles, String zipName) {
        String filename = zipName + ".zip";
        Path path = FileUtil.createOnTemp(filename);
        return zip(srcFiles, path);
    }

    private static String buildEntryZipName(HashMap<String, Integer> map, File file) {
        String name = file.getName();
        String basename = FilenameUtils.getBaseName(name);
        String extension = FilenameUtils.getExtension(name);
        if (map.containsKey(name)) {
            int nextNumber = map.get(name);
            String newName = new StringBuilder(basename).append(" (").append(nextNumber).append(").").append(extension).toString();
            map.put(name, nextNumber + 1);
            return newName;
        }
        map.put(name, 1);
        return name;
    }

    public static Path zip(List<File> srcFiles) {
        return zip(srcFiles, "defaultname");
    }
}
