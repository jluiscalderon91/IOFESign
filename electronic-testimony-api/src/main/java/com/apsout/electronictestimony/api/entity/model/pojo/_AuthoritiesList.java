package com.apsout.electronictestimony.api.entity.model.pojo;

import com.apsout.electronictestimony.api.entity.security.Authority;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class _AuthoritiesList {
    private List<Authority> authorities;
}
