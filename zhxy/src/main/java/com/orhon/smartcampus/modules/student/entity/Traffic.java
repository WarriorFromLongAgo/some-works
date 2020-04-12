package com.orhon.smartcampus.modules.student.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.orhon.smartcampus.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 学生交通信息表
 * </p>
 *
 * @author Orhon
 */
@TableName("student_traffic")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Traffic extends BaseModel {

	private static final long serialVersionUID=1L;

	@TableId
	private Integer id;  //"学生交通信息id"   
	private Double user_id;  //"用户id "   
	private Integer student_id;    
	private Integer distance;  //"上下学距离"   
	private Integer traffic_release_id;  //"上下学交通方式"   
	private Integer is_school_bus;  //"是否需要乘坐校车"   
	private LocalDateTime created_at;  //"创建时间"   
	private Integer created_by;  //"创建人"   
	private LocalDateTime updated_at;  //"修改时间"   
	private Integer updated_by;  //"修改人"   
	private LocalDateTime deleted_at;  //"删除标识"
}
