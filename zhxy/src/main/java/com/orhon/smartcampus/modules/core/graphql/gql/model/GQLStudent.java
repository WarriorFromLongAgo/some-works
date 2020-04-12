package com.orhon.smartcampus.modules.core.graphql.gql.model;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class GQLStudent {

        private Long id;
        private GQLUser user;
        private String number;
        private String learn_code;
        private GQLLang name;
        private String pinyin_name;
        private String country;
        private String idcard;
        private String birthday;
        private GQLDictionary gender;
        private GQLSchool school;
        private String picture;
        private GQLLang native_place;
        private GQLDictionary nation;
        private Integer hkmtb;
        private String political;
        private GQLLang speciality;
        private String identity_ivalidity;
        private String blood;
        private String tel_number;
        private String email;
        private String postal_code;
        private String home_page;
        private GQLDictionary household_register_type;
        private GQLLang birth_place;
        private String present_address;
        private String corresponding_address;
        private String family_address;
        private GQLRegion household_registed;
        private GQLRegion household_registed_city;
        private GQLRegion household_registed_county;
        private GQLDictionary card_type;
        private Integer is_onlychild;
        private Integer is_pre_school_education;
        private Integer is_left_behind_children;
        private Integer is_orphan;
        private Integer is_martyr;
        private Integer is_study_in_class;
        private Integer is_government_degree;
        private Integer is_funding;
        private Integer is_supplement;
        private Integer is_move_with;
        private String disabled_status;
        private String health;
        private Date created_at;
        private Date updated_at;
        private Date deleted_at;
}
