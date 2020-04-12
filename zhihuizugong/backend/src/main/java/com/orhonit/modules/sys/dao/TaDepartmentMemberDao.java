package com.orhonit.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.sys.entity.TaDepartmentMemberEntity;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 科室表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-30 17:07:15
 */
@Mapper
public interface TaDepartmentMemberDao extends BaseMapper<TaDepartmentMemberEntity> {

	@Select("select lower_id lowerId,lower_name lowerName,the_sorting theSorting from ta_department_member where dept_id = #{userDept} order by the_sorting")
	List<TaDepartmentMemberEntity> selecLowerList(@Param("userDept")Integer userDept);
	
}
