package com.orhon.smartcampus.modules.core.graphql.gql.model;

import java.util.Date;

import lombok.Data;

@Data
public class GQLSchoolType {
    private Long id;
    private GQLLang name;
    private String slug;
    private Integer order;
    private String mark;
    private GQLLang period;
    private Date created_at;
    private Date updated_at;
}
