package com.orhon.smartcampus.modules.core.graphql.gql.model;

import lombok.Data;

import java.util.Date;

@Data
public class GQLPeriod {
        private Long id;
        private String slug;
        private String number;
        private GQLLang name;
        private Integer order;
        private String mark;
        private Date created_at;
        private Date updated_at;
        private Date deleted_at;
}
