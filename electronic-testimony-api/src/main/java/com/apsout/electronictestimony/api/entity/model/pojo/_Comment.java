package com.apsout.electronictestimony.api.entity.model.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Timestamp;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class _Comment {

    private byte observed;
    private String author;
    private String description;
    private Timestamp createAt;

    public _Comment(String author, String description, byte observed, Timestamp createAt) {
        this.author = author;
        this.description = description;
        this.observed = observed;
        this.createAt = createAt;
    }

}
