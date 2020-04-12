package com.orhon.smartcampus.modules.core.graphql.gql.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;



@Data
public class GQLUser {

        private Long id;
        private String email;
        private String username;
        private String password;
        private Integer status;
        private GQLSchool school;
        private String idcard;
        private String last_login_ip;
        private Date last_login_time;

        private String remember_token;
        private String api_token;
        private Date created_at;
        private Date updated_at;
        private Date deleted_at;
        private GQLType.UserType user_type;
        private String system_lang;
        private String mobile;
        private String equipment;
        private String union_id;


        public GQLUser(){

        }


        /**
         * dbMap是从数据库过来的原始记录，用于映射本数据字段到gqlmap
         * @param dbMap
         */
        public GQLUser(Map dbMap){
                this.id = (Long) dbMap.get("id");

        }

}
