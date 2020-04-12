package com.orhonit.modules.generator.app.entity;

import java.util.List;

import lombok.Data;


/**
 *  学生答卷
 * @author YaoSC
 *
 */
@Data
public class AppStudentExam {
	
    private String examNo;
	
	private String useTime;
	
	private String userId;
	
	private List<AppStudentAnswer> studentAnswers;

	@Override
	public String toString() {
		return "AppStudentExam [examNo=" + examNo + ", useTime=" + useTime + ", userId=" + userId + ", studentAnswers="
				+ studentAnswers + "]";
	}
	
	

}
