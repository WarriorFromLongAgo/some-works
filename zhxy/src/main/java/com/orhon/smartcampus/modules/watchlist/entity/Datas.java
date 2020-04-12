package com.orhon.smartcampus.modules.watchlist.entity;

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
 * 行政值班数据项
 * </p>
 *
 * @author Orhon
 */
@TableName("watchlist_datas")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Datas extends BaseModel {

private static final long serialVersionUID=1L;

   
    private Integer id;  //"行政值班数据项id"   
    private Integer administrativeduty_id;  //"行政值班项id"   
    private Integer admininspectterm_id;  //"行政值班检查项id"   
    private Integer leader;  //"值班领导"   
    private Integer cadre;  //"值班环节干部"   
    private Integer school_id;  //"学校id"   
    private Integer state;  //"是否正常1、正常2、有异常"   
    private LocalDateTime created_at;  //"创建时间"   
    private Integer created_by;  //"创建人"   
    private LocalDateTime updated_at;  //"修改时间"   
    private Integer updated_by;  //"修改人"   
    private LocalDateTime deleted_at;  //"删除标识"   
    private String time;  //"时间"
    private Integer data_log_id;  //"数据id"   
    private String img;  //"图片"   
    private Integer schoolyear_id;  //"学年"   
    private Integer semester_id;  //"学期"   
    private Integer feedback;  //"待反馈1，未反馈2，已反馈"   
    private Integer moral;  //"德育管理人员"   
    private String explains;  //"领导班子说明"   
    private String feedbacks;  //"环节干部反馈"
}
