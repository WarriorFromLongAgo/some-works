package com.orhon.smartcampus.modules.core.graphql.gql.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GQLOperation {

        private Long id;
        private GQLLang title;
        private List<String> apis;
        private String menus;
        private List<String> widgets;
        private Integer module_id;
        private Date created_at;
        private Date updated_at;
        private Date deleted_at;
}
