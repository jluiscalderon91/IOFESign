package com.apsout.electronictestimony.api.entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoreAboutEnterprise {
    private BigDecimal balance;
}
