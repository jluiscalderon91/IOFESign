package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.entity.Assigner;
import com.apsout.electronictestimony.api.entity.model.AssignerModel;

public class AssignerModelAssembler {

    public AssignerModel toModel(Assigner assigner) {
        AssignerModel  assignerModel = new AssignerModel();
        assignerModel.setId(assigner.getId());
        assignerModel.setDocumentId(assigner.getDocumentId());
        assignerModel.setOperatorId(assigner.getOperatorId());
        assignerModel.setCompleted(assigner.getCompleted());
        return assignerModel;
    }
}
