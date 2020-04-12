package com.orhon.smartcampus.modules.core.graphql.gql.model;


import lombok.Data;

import java.util.Date;

@Data
public class GQLRegion {

    private Long id;
    private String slug;
    private GQLRegion parent;
    private Integer level;
    private Integer order;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
    private GQLLang name;
}
