package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.util.enums.Pages2Stamp;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.nio.file.Path;

public interface StamperService {

    Path stamp(Enterprise enterprise, Workflow workflow, Resource resource);

//    Path stamp(Enterprise enterprise, Workflow workflow, Resource resource, Path srcPathTemplate);

    Pages2Stamp translate2Pages2Stamp(Pagestamp pagestamp);

    Integer getNumberPages(Path filePath);

    Path stampRubric(Document  document, Participant participant, Path srcPath, boolean preview);

    Path stampBeforeRubric(Enterprise enterprise, Workflow workflow, Document document, Resource resource, Path srcPathPdfTemplate, boolean preview);

    Path stampAllElements(Enterprise enterprise, Workflow workflow, Resource resource, Path srcPathPdfTemplate, boolean preview);
}
