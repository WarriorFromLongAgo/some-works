package com.orhonit.modules.generator.app.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.modules.generator.app.entity.AppOsExamQuestionEntity;
import com.orhonit.modules.generator.app.entity.AppStudentExam;
import com.orhonit.modules.generator.app.entity.AppStudentExamScore;
import com.orhonit.modules.generator.app.vo.AppOsExamUserExaminationVO;


//@Slf4j
public interface AppOsExamQuestionService extends IService<AppOsExamQuestionEntity>{

    //套卷出题
	AppOsExamUserExaminationVO CatenaQuestion(String examId,String userId);
	
	AppStudentExamScore MarkExerciseExam(AppStudentExam studentExam);
	
	AppOsExamUserExaminationVO OnlineQuestion(String examId,String userId);
	/*
	 * 在线考试判分
	 */
	AppStudentExamScore MarkOnlineExam(AppStudentExam appstudentExam);
}
