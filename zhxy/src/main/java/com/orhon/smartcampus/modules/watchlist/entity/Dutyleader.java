package com.orhon.smartcampus.modules.watchlist.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 值班领导、环节干部
 * </p>
 *
 * @author Orhon
 */
@TableName("watchlist_dutyleader")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Dutyleader extends BaseModel {

private static final long serialVersionUID=1L;


    private Integer id;  //"id"
    private Integer user_id;  //"用户id"
    private Integer department_id;  //"部门id"
    private Integer type;  //"类型1、值班领导2、环节干部3、德育管理人员"
    private Integer created_by;  //"创建人"
    private LocalDateTime created_at;  //"创建日期"
    private Integer updated_by;  //"最后修改人"
    private LocalDateTime updated_at;  //"最后修改时间"
    private LocalDateTime deleted_at;  //"删除标示"
    private Integer school_id;  //"学校id"
    @JSONField(jsonDirect=true)
    private String remarks;  //"人员备注"
}
