package com.apsout.electronictestimony.api.entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Timestamp;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoreAboutSiecredential {
    private String enterpriseName;
    private String certificateName;
    private Timestamp certificateNotAfter;
    private Timestamp certificateNotBefore;
}
