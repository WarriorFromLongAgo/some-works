package com.orhonit.modules.generator.app.vo;

import java.io.Serializable;
import java.util.List;

import com.orhonit.modules.generator.app.entity.AppOsQuestionEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 试卷
 * 
 * @author YaoSC
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppOsExamUserExaminationVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String paperNo;
	
	private List<AppOsQuestionEntity> questions;
	
	
	
	
 
}
