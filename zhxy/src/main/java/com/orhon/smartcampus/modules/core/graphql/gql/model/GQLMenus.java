package com.orhon.smartcampus.modules.core.graphql.gql.model;

import lombok.Data;

import java.util.Date;

@Data
public class GQLMenus {

        private Long id;
        private GQLLang title;
        private GQLLang name;
        private String path;
        private String component;
        private GQLModule module;
        private GQLDictionary category;
        private GQLDictionary type;
        private GQLMenus parent;
        private Integer level;
        private String meta;
        private String clients;
        private String icon;
        private Integer ordered;
        private Integer status;
        private Date created_at;
        private Date updated_at;
        private Date deleted_at;
}
