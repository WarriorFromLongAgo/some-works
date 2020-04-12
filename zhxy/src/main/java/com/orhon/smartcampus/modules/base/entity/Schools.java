package com.orhon.smartcampus.modules.base.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 学校
 * </p>
 *
 * @author Orhon
 */
@TableName("base_schools")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Schools extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId 
    private Integer id;  //"学校 id"   
    private String school_slug;  //"学校别名"   
    private String school_number;  //"学校编号"   
    private Integer school_order;  //"顺序"   
    private Date created_at;
    private Date updated_at;
    private Integer parent_id;  //"父级学校"   
    private String school_type;  //"学校类型"
    private Integer region_id;  //"学校所属地区id"   
    private String school_client_key;  //"orhonedu对应学校客户key"   
    private String client_secret;  //"orhonedu对应学校客户秘钥	"   
    private String orhonedu_base;  //"orhonedu地址"   
    private Date deleted_at;
    @JSONField(jsonDirect=true)
    private String school_name;   
    @JSONField(jsonDirect=true)
    private String introduction;
    private String icon;
    private Integer city_id;
    private Integer district_id;
    private String header_img;
    private String main_color;
    private String mark;
    private Integer cloud_status;
    private String lng;
    private String lat;
    private String address;
    private Integer province_id;

}
