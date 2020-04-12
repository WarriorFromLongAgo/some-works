package com.orhonit.ole.enforce.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ole_message_push")
@AllArgsConstructor
@NoArgsConstructor
public class MessagePushEntity {
	
	@Id
	private String id;

	/**
	 * 推送内容
	 */
	private String push_content;

	/**
	 * 推送人(创建人)
	 */
	private String push_person;

	/**
	 * 被推送人
	 */
	private String cover_person;

	/**
	 * 界面类型
	 */
	private int interface_type;

	/**
	 * 模块类型
	 */
	private int modular_type;

	/**
	 * 模块类型ID
	 */
	private String modular_id;

	/**
	 * 推送是否成功
	 */
	private int push_success;
	
	/**
	 * pc端推送是否成功
	 */
	private int pc_push_success;

	/**
	 * 创建时间
	 */
	private Date create_time;

	/**
	 * 修改时间
	 */
	private Date update_time;

}
