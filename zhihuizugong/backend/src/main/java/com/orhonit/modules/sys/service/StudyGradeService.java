package com.orhonit.modules.sys.service;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.R;
import com.orhonit.modules.sys.entity.StudyGradeEntity;
import com.orhonit.modules.sys.entity.StudyUserEntity;


public interface StudyGradeService extends IService<StudyGradeEntity>  {

	HashMap<String,Object> getSumGrade(StudyGradeEntity gradeEntity);

	HashMap<String,Object> getCountGrade(StudyGradeEntity gradeEntity);

	List<StudyUserEntity>  topten();

	List<StudyGradeEntity> getGradeList(StudyGradeEntity gradeEntity);

	R complete(StudyGradeEntity gradeEntity);

	R updateUser(StudyGradeEntity gradeEntity);

	R liveness(StudyUserEntity userEntity);

	R myuserCount(StudyUserEntity userEntity);

	R Completion(StudyUserEntity userEntity);

	R getUserList(StudyUserEntity userEntity);

}
