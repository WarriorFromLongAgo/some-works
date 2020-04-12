package com.orhon.smartcampus.modules.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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
@TableName("user_token")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Token extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;


    private Long userid;
    private String token;
    private Date expire_time;
    private Date update_time;
    private String username;

}
