package com.orhonit.modules.generator.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.ZgTaskFinishEntity;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 工作任务副表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 16:28:24
 */
@Mapper
public interface ZgTaskFinishDao extends BaseMapper<ZgTaskFinishEntity> {

	/**
	 * 查询工作的完成情况
	 * @param id
	 * @return
	 */
	List<ZgTaskFinishEntity> selectCompletion(@Param("id")Integer id);
	/**
	 * 查询工作的督办情况
	 * @param id
	 * @return
	 */
	List<ZgTaskFinishEntity> selectRigorous(@Param("id")Integer id);
	/**
	 * 根据工作任务ID删除相关督办情况和完成情况
	 * @param id
	 * @return
	 */
	@Delete("delete from zg_task_finish where task_id = #{id}")
	void deleteTaskFinish(@Param("id")Integer id);
	
}
