package com.apsout.electronictestimony.api.entity.model.pojo;

import com.apsout.electronictestimony.api.entity.Participant;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class _Body {
    private Integer type;
    private String subject;
    private Integer workflowId;
    private List<Participant> participants;
    private List<_File> files;
}
