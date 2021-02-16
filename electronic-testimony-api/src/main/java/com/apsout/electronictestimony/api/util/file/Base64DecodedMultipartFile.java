package com.apsout.electronictestimony.api.util.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class Base64DecodedMultipartFile implements MultipartFile {
    private final byte[] bytes;
    private final String name;
    private final String contentType;

    public Base64DecodedMultipartFile(String name, String contentType, byte[] bytes) {
        this.bytes = bytes;
        this.name = name;
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return name;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return bytes == null || bytes.length == 0;
    }

    @Override
    public long getSize() {
        return bytes.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return bytes;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public void transferTo(File file) throws IOException, IllegalStateException {
        new FileOutputStream(file).write(bytes);
    }
}
