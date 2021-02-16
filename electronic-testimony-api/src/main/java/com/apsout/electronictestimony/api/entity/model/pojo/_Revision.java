package com.apsout.electronictestimony.api.entity.model.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class _Revision {
    private List<_ReviewInfo> reviewInfos;
}
