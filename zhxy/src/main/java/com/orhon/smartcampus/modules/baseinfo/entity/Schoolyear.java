package com.orhon.smartcampus.modules.baseinfo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;


/**
 * <p>
 * 学年表
 * </p>
 *
 * @author Orhon
 */
@TableName("baseinfo_schoolyear")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Schoolyear extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer schoolyear_id;  //"学年id"
    private Integer school_id;  //"学校id"
    private String schoolyear_code;  //"学年编号"
    private String start_at;  //"学年开始日期"
    private String end_at;  //"学年结束日期"
    private Integer status_flag;  //"学年状态"
    private DateTime created_at;  //"创建时间"
    private Integer created_by;  //"创建人"
    private DateTime updated_at;  //"修改时间"
    private Integer updated_by;  //"修改人"
    private DateTime deleted_at;  //"删除标识"
    private String mark;
    @JSONField(jsonDirect = true)
    private String schoolyear_name;
}
