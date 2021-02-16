package com.apsout.electronictestimony.api.entity.model.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class _Resource {
    private String fileName;
    private Integer orderResource;
    private String url;
    private String hashResource;
    private float length;
    private String url2;
}
