package com.orhonit.modules.generator.app.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 学员卷面答案
 * @author YaoSC
 *
 */
@Data
public class AppStudentAnswer implements Serializable{
    private static final long serialVersionUID = 1L;
	
	private String id;
	private String select;
	private Integer no;

}
