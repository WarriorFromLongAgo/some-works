package com.orhonit.modules.religion.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.religion.entity.SysArea;


@Mapper
public interface SysAreaMapper extends BaseMapper<SysArea>{
	
	
	/**
	 * 通过地区名称模糊查询  只有嘎查
	 * @param parentId
	 * @return
	 */
	@Select({
		""
		+ " select id,name ,parent_id parentId,level from sys_area where level=6 "
		+ ""
	})
	List<SysArea> selectAreaGacha();
	
	/**
	 * 通过父级id查询地区信息
	 * @param parentId
	 * @return
	 */
	@Select({
		"select id,name ,parent_id parentId,level from sys_area where parent_id=#{parentId}"
	})
	List<SysArea> selectAreaByParentId(String parentId);
}
