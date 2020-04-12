package com.orhonit.modules.generator.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class QuestionLibraryVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// 单选题数量
	private Integer single;
	
	// 多选题数量
	private Integer multi;
	
	// 选择题数量
	private Integer judge;
	
	// 总题数
	private Integer totle;

}
