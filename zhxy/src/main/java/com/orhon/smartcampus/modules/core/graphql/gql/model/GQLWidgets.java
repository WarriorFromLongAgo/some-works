package com.orhon.smartcampus.modules.core.graphql.gql.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GQLWidgets {

        private Long id;
        private GQLLang title;
        private GQLLang name;
        private List<String> apis;
        private GQLMenus menu;
        private String type;
        private GQLModule module;
        private List<String> clients;
        private String meta;
        private String status;
        private Date created_at;
        private Date updated_at;
        private Date deleted_at;
}
