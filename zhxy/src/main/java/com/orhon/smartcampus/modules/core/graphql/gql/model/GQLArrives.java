package com.orhon.smartcampus.modules.core.graphql.gql.model;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class GQLArrives {

        private Long id;
        private String mark;
        private GQLLang name;
        private GQLGrades grade;
        private GQLPeriod period;
        private Integer order;
        private String begin_year;
        private String graduate_year;
        private GQLSchool school;
        private Date created_at;
        private Date updated_at;
        private Date deleted_at;
}
