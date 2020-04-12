package com.orhon.smartcampus.modules.core.graphql.gql.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.orhon.smartcampus.modules.base.entity.Periods;
import lombok.Data;

import java.util.Date;

@Data
public class GQLGrades {
        private Long id;
        private String slug;
        private String number;
        private GQLPeriod period;
        private GQLLang name;
        private Integer order;
        private String mark;
        private Date created_at;
        private Date updated_at;
        private Date deleted_at;
}
