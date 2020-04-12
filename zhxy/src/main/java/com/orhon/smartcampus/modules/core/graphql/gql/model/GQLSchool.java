package com.orhon.smartcampus.modules.core.graphql.gql.model;


import lombok.Data;

import java.util.Date;

@Data
public class GQLSchool {

    private Long id;
    private GQLLang name;
    private GQLLang introduction;
    private String slug;
    private String number;
    private String icon;
    private Integer order;
    private GQLRegion province_id;
    private GQLRegion city_id;
    private GQLRegion district_id;
    private GQLRegion region;
    private String header_img;
    private String main_color;
    private String mark;
    private GQLDictionary school_type;
    private Integer cloud_status;
    private String client_key;
    private String client_secret;
    private String orhonedu_base;
    private String lng;
    private String lat;
    private String address;
    private GQLSchool parent;
    private GQLModule modules;
    private GQLUser users;
    private GQLStudent students;
    private GQLTeacher teachers;
    private GQLSchoolSettings settings;

    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
    private GQLSchoolType type;
}
