package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.OsQuestionLibraryEntity;
import com.orhonit.modules.generator.entity.OsQuestionTypeVariableEntity;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 题库表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-23 10:58:55
 */
@Mapper
public interface OsQuestionLibraryDao extends BaseMapper<OsQuestionLibraryEntity> {
	
	

	/* * //查询所有题库标题
	 * 
	 * 
	 */
	@Select("select library_title,library_id,question_scope,library_type from os_question_library where is_del='0'" )
	List<OsQuestionLibraryEntity>allSelect();
	
	
	//查出所有题库类型
	@Select("select * from os_question_type_variable ")
	List<OsQuestionTypeVariableEntity>variableList();
	
	//编辑查看题库
	List<OsQuestionLibraryEntity>SelectTypeList(@Param("libraryId") String libraryId);
	
	//@Update("update os_question_library set library_title=#{libraryTitle},library_type=#{libraryType},question_scope=#{questionScope}  where library_id=#{libraryId}")
	void updateOsQuestionlibrary( OsQuestionLibraryEntity osQuestionLibraryEntity);
	
	//删除题库同时底下所有试题也删
	void deleteAllOsQuestion(@Param("libraryId")String libraryId);
	
	// 查询某个题库下的选择题数量
	Integer selectSingleCount(@Param("libraryId") String libraryId);

	// 查询某个题库下的多选题数量
	Integer selectMultiCount(@Param("libraryId") String libraryId);

	// 查询某个题库下的判断题数量
	Integer selectJudgeCount(@Param("libraryId") String libraryId);

	// 查询某个题库下的题总数量
	Integer selectQuestionTotle(@Param("libraryId") String libraryId);
	
	@Insert("INSERT INTO os_question_library (library_id,library_title,library_type,question_scope,library_create_time,library_update_time,is_del)VALUES(#{libraryId},#{libraryTitle},#{libraryType},#{questionScope},#{libraryCreateTime},#{libraryUpdateTime},#{isDel})")
	void inserOsQuestionLibrary(OsQuestionLibraryEntity osQuestionLibraryEntity);
	
	
}
