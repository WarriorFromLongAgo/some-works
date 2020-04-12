package com.orhon.smartcampus.modules.core.graphql.gql.model;

import lombok.Data;

import java.util.Date;

@Data
public class GQLDuty {

        private Long id;
        private GQLLang duties_name;
        private Integer duties_order;
        private GQLDuty parent;
        private String level;
        private GQLDepartmentUsers users;


}
