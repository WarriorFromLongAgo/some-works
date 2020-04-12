package com.orhon.smartcampus.modules.core.graphql.gql.model;

import lombok.Data;

import java.util.Date;

@Data
public class GQLDepartment {

        private Long id;
        private GQLLang department_name;
        private GQLDepartment parent;
        private Integer department_order;
        private GQLSchool school;
        private String type;
        private String group_type;
        private Integer status;
        private GQLGrades grade;
        private GQLSubject subject;
        private GQLDuty duties;
        private GQLDepartmentUsers users;
}
