package com.orhonit.modules.sys.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.sys.entity.StudyGradeEntity;
import com.orhonit.modules.sys.entity.StudyUserEntity;



@Mapper
public interface StudyGradeDao extends BaseMapper<StudyGradeEntity> {

	HashMap<String,Object> getSumGrade(StudyGradeEntity gradeEntity);

	HashMap<String,Object> getCountGrade(StudyGradeEntity gradeEntity);

	List<StudyUserEntity> topten();

	List<StudyGradeEntity> getGradeList(StudyGradeEntity gradeEntity);

	
}
