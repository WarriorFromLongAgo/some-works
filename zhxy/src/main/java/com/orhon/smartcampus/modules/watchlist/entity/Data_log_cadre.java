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
 * 
 * </p>
 *
 * @author Orhon
 */
@TableName("watchlist_data_log_cadre")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Data_log_cadre extends BaseModel {

private static final long serialVersionUID=1L;

   
    private Integer id;    
    private Integer data_log_id;    
    private Integer cadre;    
    private LocalDateTime created_at;    
    private LocalDateTime updated_at;    
    private LocalDateTime deleted_at;    
    private String cadre_remarks; 
}
