package com.orhon.smartcampus.modules.core.graphql.gql.model;

import lombok.Data;

import java.util.Date;

@Data
public class GQLSchoolSettings {

        private Long school_id;
        private GQLLang platform_name;
        private GQLGrades grades;
        private GQLPeriod periods;
        private GQLSubject subjects;
        private GQLDuty duties;
        private String dutilogoes;
        private String favicon;
        private String theme_settings;
        private String meta;
}
