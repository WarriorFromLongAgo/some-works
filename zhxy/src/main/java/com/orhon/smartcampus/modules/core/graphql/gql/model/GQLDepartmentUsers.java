package com.orhon.smartcampus.modules.core.graphql.gql.model;

import lombok.Data;

import java.util.Date;

@Data
public class GQLDepartmentUsers {

        private Long id;
        private String email;
        private GQLDictionary gender;
        private String idcard;
        private String mobile;
        private String imgphotos;
        private String workdate;
        private Integer is_leaders;
        private Date leave_at;
        private GQLDepartment department;
        private GQLDuty duty;



}
