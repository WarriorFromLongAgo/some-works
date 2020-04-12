package com.orhon.smartcampus.modules.core.graphql.gql.model;

import lombok.Data;

import java.util.Date;

@Data
public class GQLSubject {

        private Long id;
        private GQLLang subject_name;
        private String subject_slug;
        private String subject_number;
        private Integer subject_order;
        private String mark;
        private String type;
        private GQLPeriod period;
        private GQLGrades grade;
}
