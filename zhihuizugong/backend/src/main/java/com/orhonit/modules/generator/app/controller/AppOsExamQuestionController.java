package com.orhonit.modules.generator.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.app.entity.AppStudentExam;
import com.orhonit.modules.generator.app.entity.AppStudentExamScore;
import com.orhonit.modules.generator.app.msg.ObjectRestResponse;
import com.orhonit.modules.generator.app.service.AppOsExamQuestionService;
import com.orhonit.modules.generator.app.vo.AppOsExamUserExaminationVO;
import com.orhonit.modules.sys.controller.AbstractController;



/**
 * APP端考试题库试题表
 * @author YaoSC
 *
 */
@RestController
@RequestMapping("/app/examquestion")
public class AppOsExamQuestionController extends AbstractController{
	
	@Autowired
	AppOsExamQuestionService OsExamQuestionService;
	
	
	
	//根据试卷编号出题(套卷练习)
	@SuppressWarnings("unused")
	@GetMapping("/getCoilingExercise")
	public AppOsExamUserExaminationVO getCoiling(@RequestParam("examId")String examId) {
		String userId = String.valueOf(getUserId());
		if(examId != null || examId != "") {
			return OsExamQuestionService.CatenaQuestion(examId,userId);
		}
	return null;
	}
	
	/**
	 * 计算练习题卷面成绩
	 * 
	 * @param  appstudentExam
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	@PostMapping("/MarkExerciseExam")
	public ObjectRestResponse<AppStudentExamScore>MarkExerciseExam(
			@RequestBody AppStudentExam appstudentExam){
		appstudentExam.setUserId(String.valueOf(getUserId()));
		AppStudentExamScore MarkExerciseExam = OsExamQuestionService.MarkExerciseExam(appstudentExam);
		return new ObjectRestResponse<>().data(MarkExerciseExam);
		
	}
	
	
	/**
	  * 套卷练习和在线考试判分
	 * 
	 * @param studentExam
	 * @return
	 */
	@SuppressWarnings("unused")
	@GetMapping("/getOnlineQuestion")
	public AppOsExamUserExaminationVO getOnlineQuestion(@RequestParam("examId")String examId) {
		String userId = String.valueOf(getUserId());
		if (examId != null || examId != "") {
			return OsExamQuestionService.OnlineQuestion(examId,userId);
		}
		return null;
	}
	
	
	/**
	 * 套卷练习和在线考试判分
	 * 
	 * @param studentExam
	 * @return
	 */
	@PostMapping("/MarkOnlineExam")
	public R MarkOnlineExam(@RequestBody AppStudentExam studentExam) {
		AppStudentExamScore MarkExerciseExam = OsExamQuestionService.MarkOnlineExam(studentExam);
		return R.ok().put("MarkExerciseExam", MarkExerciseExam);
	}
		

}
