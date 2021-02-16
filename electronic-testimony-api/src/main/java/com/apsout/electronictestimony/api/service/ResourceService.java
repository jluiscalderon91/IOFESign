package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Resource;
import com.apsout.electronictestimony.api.entity.model.pojo._Revision;
import com.apsout.electronictestimony.api.util.enums.ScopeResource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface ResourceService {
    String storeFile(Document document, int orderResource, MultipartFile file);

    Resource save(Resource resource);

    Optional<Resource> finBy(int resourceId);

    Optional<Resource> getOriginalResourceBy(int documentId);

    Optional<Resource> getOriginalResourceBy(int documentId, int orderResource);

    @Deprecated
    org.springframework.core.io.Resource getStreamResourceBy(int documentId);

    org.springframework.core.io.Resource getStreamBy(int resourceId);

    org.springframework.core.io.Resource getStreamBy(int documentId, String hashIdentifier, ScopeResource scopeResource);

    org.springframework.core.io.Resource getStreamBy(int documentId, String hashIdentifier, ScopeResource scopeResource, HttpServletRequest request);

    org.springframework.core.io.Resource getStreamBy2(int personId, int documentId, String hashIdentifier, int orderResource, ScopeResource scopeResource);

    org.springframework.core.io.Resource getStreamByWithStamps(int personId, int documentId, String hashIdentifier, int orderResource, ScopeResource scopeResource, boolean addGraphicElements);

    org.springframework.core.io.Resource getStreamOutsideBy(int documentId, String hashIdentifier, ScopeResource scopeResource);

    Resource save(int personId, int subOperationType, int documentId, String hashIdentifier, int orderResource, InputStream stream, boolean willBeClosedStamping);

    org.springframework.core.io.Resource getCsvStreamBy(String uuid);

    org.springframework.core.io.Resource getStreamBy(String hashIdentifier, ScopeResource scopeResource);

    org.springframework.core.io.Resource getStreamBy2(int personId, String hashIdentifier, String hashResource, ScopeResource scopeResource);

    void review(int personId,
                int documentId,
                String hashIdentifier,
                byte observed,
                byte cancelled,
                String comment,
                boolean willBeClosedStamping,
                HttpServletRequest request);

    Resource save(int personId,
                  int operationType,
                  int subOperationType,
                  int documentId,
                  String hashIdentifier,
                  int orderResource,
                  InputStream stream,
                  byte observed,
                  String comment,
                  boolean willBeClosedStamping);

    List<Resource> findAllBy(Document document);

    List<Resource> findAllBy(int documentId);

    List<File> findAllFilesBy(Document document, ScopeResource scopeResource);

    Resource getMaximumOrderBy(Document document);

    Resource getMaximumOrderBy(Document document, String hashResource);

    Resource getBy(int resourceId);

    org.springframework.core.io.Resource getStreamBy(String docIdentifiers);

    org.springframework.core.io.Resource getStreamTemplateBy(int workflowId);

    Optional<Resource> findBy(String resumeHash);

    Resource getBy(String resumeHash);

    org.springframework.core.io.Resource getStreamByResumeHash(String resumeHashResource);

    void review(int personId, _Revision revision, boolean willBeClosedStamping, HttpServletRequest request);

//    void review(int personId, _Revision revision, HttpServletRequest request);

    Optional<Resource> findFirstBy(Document document);

    String buildURLVerifier(Document document, Resource resource);

    String buildURLVerifier4FirstResource(Document document);
}
