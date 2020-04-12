package com.orhon.smartcampus.modules.core.graphql.gql.model;

import java.util.Date;

import lombok.Data;

@Data
public class GQLDictionary {

	 private Long id;
     private int parent_id;
     private String alias;
     private String type;
     private GQLLang name;
     private GQLLang description;
     private String status;
     private GQLLang meta;
     private String value;
}
