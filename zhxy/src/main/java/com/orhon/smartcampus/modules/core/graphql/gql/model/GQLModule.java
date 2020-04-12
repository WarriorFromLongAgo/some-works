package com.orhon.smartcampus.modules.core.graphql.gql.model;

import lombok.Data;

import java.util.Date;

@Data
public class GQLModule {

        private Long id;
        private GQLLang module_name;
        private String category;
        private Integer module_order;
        private GQLMenus Menus;
        private String mark;
        private String icon;
        private String clients;
        private String meta;
        private String status;
        private GQLMenus menus;
        private GQLWidgets widgets;
        private GQLSchool schools;
        private GQLOperation oprations;
        private String created_at;
        private String updated_at;
        private String deleted_at;
}
