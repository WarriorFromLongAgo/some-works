package com.orhon.smartcampus.modules.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;

import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 
 * </p>
 *
 * @author Orhon
 */
@TableName("base_dictionary")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Dictionary extends BaseModel {

private static final long serialVersionUID=1L;

    @TableId
    private Integer id;  //"数据字典id"
    private Integer parent_id;  //"上级数据字典id"
    private String alias;  //"别名"
    private String type;  //"字典类型(category 分类、option选项值)"
    private String dictionary_code;  //"数据字典编码"
    private String value_code; //"字典值"
    @JSONField(jsonDirect=true)
    private String dictionary_name;  //"数据字典名称"
    @JSONField(jsonDirect=true)
    private String dictionary_description;  //"数据字典描述"
    private Integer status;  //"可用状态"
    private String standard_type;  //"标准类型（GB国标、GB/T 国标推荐、other其他）"
    private String meta;  //"扩展字段"
    private Date created_at;  //"创建日期"
    private Date updated_at;  //"最后更新时间"
    private Date deleted_at;  //"删除标识（1=正常2=删除）"
}
