package com.apsout.electronictestimony.api.entity.model.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class _ReviewInfo {
    private Integer documentId;
    private String hashIdentifier;
    private Byte observed;
    private Byte cancelled;
    private String comment;
}
