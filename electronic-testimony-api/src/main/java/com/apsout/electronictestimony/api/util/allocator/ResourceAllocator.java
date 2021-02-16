package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Resource;
import com.apsout.electronictestimony.api.util.crypto.Hash;
import com.apsout.electronictestimony.api.util.statics.States;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ResourceAllocator {
    public static Resource build(MultipartFile multipartFile, String path, int orderResource) {
        Resource resource = new Resource();
        resource.setType(multipartFile.getContentType());
        resource.setPath(path);
        resource.setOriginalName(multipartFile.getOriginalFilename());
        resource.setNewName("-");
        resource.setExtension(FilenameUtils.getExtension(path));
        resource.setLength(multipartFile.getSize());
        resource.setOrderResource(orderResource);
        String hash = new StringBuilder(Hash.sha256(LocalDateTime.now().toString() + resource.getId())).toString();
        resource.setHash(buildHash4(hash));
        resource.setResumeHash(buildResumeHash4(hash));
        ofPostMethod(resource);
        return resource;
    }

    public static Resource build(Resource originRsrce, String path, int orderResource) {
        Resource resource = new Resource();
        resource.setType(originRsrce.getType());
        resource.setPath(path);
        resource.setOriginalName(FilenameUtils.getName(path));
        resource.setNewName("-");
        resource.setExtension(FilenameUtils.getExtension(path));
        resource.setLength(new File(path).length());
        resource.setOrderResource(orderResource);
        resource.setHash(originRsrce.getHash());
        resource.setResumeHash(originRsrce.getResumeHash());
        ofPostMethod(resource);
        return resource;
    }

    public static void ofPostMethod(Resource resource) {
        resource.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        resource.setActive(States.ACTIVE);
        resource.setDeleted(States.EXISTENT);
    }

    private static String buildHash4(String hash) {
        return hash.substring(0, 16).toLowerCase();
    }

    private static String buildResumeHash4(String hash) {
        return hash.substring(16, 26).toLowerCase();
    }
}
