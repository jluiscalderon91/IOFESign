package com.apsout.electronictestimony.api.util.file;

import com.apsout.electronictestimony.api.entity.model.pojo._File;
import com.apsout.electronictestimony.api.util.date.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;

public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static MultipartFile[] buildMultipartFiles(List<_File> files) {
        return files.stream().map(file -> {
            String base64Data = file.getBase64();
            String name = buildFilename(file);
            byte[] bytes = convertPdfBase64toBytes(base64Data);
            return new Base64DecodedMultipartFile(name, "application/pdf", bytes);
        }).toArray(size -> new MultipartFile[size]);
    }

    private static String buildFilename(_File file) {
        final String sendedName = file.getName();
        boolean hasExtension = !FilenameUtils.getExtension(sendedName).isEmpty();
        return hasExtension ? sendedName : sendedName + ".pdf";
    }

    public static Path createTempFile(String prefix, String suffix) {
        try {
            return Files.createTempFile(prefix, suffix);
        } catch (IOException e) {
            logger.error("Creating temp file", e);
            throw new RuntimeException("Can't create a temp file", e);
        }
    }

    public static Path createOnTemp(String filename) {
        Path path = createPathOnTemp(filename);
        File file = path.toFile();
        try {
            file.createNewFile();
            return path;
        } catch (IOException e) {
            logger.error(String.format("Creating personalized temp file with filename: %s", filename), e);
        }
        return null;
    }

    public static Path createPathOnTemp(String filename) {
        String tmpDir = System.getProperty("java.io.tmpdir");
        long now = DateUtil.now();
        String pathFolder = new StringBuilder(tmpDir).append(File.separator).append(now).append(File.separator).toString();
        File folder = new File(pathFolder);
        folder.mkdirs();
        File file = new File(folder.getPath() + File.separator + filename);
        return file.toPath();
    }

    public static byte[] convertPdfBase64toBytes(String base64Data) {
        return Base64.getDecoder().decode(base64Data.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] convertImageBase64toBytes(String base64Data) {
        int commaIndex = base64Data.indexOf(",") + 1;
        String onlyImportantContent = base64Data.substring(commaIndex);
        return Base64.getDecoder().decode(onlyImportantContent.getBytes(StandardCharsets.UTF_8));
    }

    public static File write2NewFile(byte[] data, String preffix, String suffix) {
        Path pathTempFile = FileUtil.createTempFile(preffix, suffix);
        final File tmpFile = pathTempFile.toFile();
        try {
            FileUtils.writeByteArrayToFile(tmpFile, data);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Building file file with name: %s", tmpFile.getName()), e);
        }
        return tmpFile;
    }

    public static Path copyInputStreamOfResources(String pathResource) {
        ClassPathResource classPathResource = new ClassPathResource(pathResource);
        String extension = "." + FilenameUtils.getExtension(pathResource);
        InputStream inputStream = null;
        try {
            inputStream = classPathResource.getInputStream();
        } catch (IOException e) {
            logger.error(String.format("Reading InpustStream of path: %s", pathResource), e);
        }
        Path path = FileUtil.createTempFile("template-", extension);
        try {
            FileUtils.copyInputStreamToFile(inputStream, path.toFile());
        } catch (IOException e) {
            logger.error(String.format("Copying fileInpustStream of path: %s", pathResource), e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.error(String.format("Closing InpustStream of path: %s", pathResource), e);
            }
        }
        return path;
    }

    public static String encode2Base64(File file) {
        Path pathFileBase64 = FileUtil.createTempFile("base64-", ".txt");
        try (OutputStream os = java.util.Base64.getEncoder().wrap(new FileOutputStream(pathFileBase64.toFile())); FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = new byte[1024];
            int read;
            while ((read = fis.read(bytes)) > -1) {
                os.write(bytes, 0, read);
            }
        } catch (IOException e) {
            logger.error(String.format("Building base64 file representation of: %s", file.getAbsoluteFile()), e);
        }
        try {
            return FileUtils.readFileToString(pathFileBase64.toFile(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Reading encoded file: %s", file.getAbsoluteFile()));
        }
    }

    public static String encodeBytes2Base64(byte[] data) {
        File file = write2NewFile(data, "pref", "jota.pdf");
        return encode2Base64(file);
    }

    public static byte[] readFileToByteArray(Path path) {
        try {
            return FileUtils.readFileToByteArray(path.toFile());
        } catch (IOException e) {
            throw new RuntimeException(String.format("Reading file of path: %s", path), e);
        }
    }

    public static String encode2Base64(Path path) {
        return encode2Base64(path.toFile());
    }
}
