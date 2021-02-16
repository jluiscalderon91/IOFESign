package com.apsout.electronictestimony.api.entity.common;

import com.apsout.electronictestimony.api.entity.Stampdatetime;
import com.apsout.electronictestimony.api.entity.model.pojo._Comment;
import com.apsout.electronictestimony.api.entity.model.pojo._Operator;
import com.apsout.electronictestimony.api.entity.model.pojo._Resource;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoreAboutDocument {
    private Integer orderNextSigner;
    private String fullnameNextSigner;
    private Integer personId;
    private String assignmentProgress;
    private Integer operationId;
    private Byte activeNotification;
    private Byte enabledNotification;
    private List<_Comment> commentsInfo;
    private Byte hasComment;
    private Byte observed;
    private String workflowDescription;
    private Integer participantType;
    private Byte mandatoryOperator;
    private String urlSignOperator;
    private Integer currentSignerOrder;
    private Integer totalSigners;
    private String nextSignerFullname;
    private List<_Resource> resources;
    private Stampdatetime stampdatetime;
    private String uploaderName;
    private String enterpriseName;
    private Byte dynamicWorkflow;
    private String basenameFile;
    private String suffix;
    private String extension;
    private List<_Operator> operators;
    private boolean digitalSignature;
    private String uuid;
    private boolean willBeClosedStamping;
    private boolean hasRubricSettings;
}
