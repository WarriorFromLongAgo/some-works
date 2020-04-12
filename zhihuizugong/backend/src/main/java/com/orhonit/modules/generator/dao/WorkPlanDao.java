package com.orhonit.modules.generator.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.WorkPlanEntity;
import com.orhonit.modules.generator.entity.ZgPlanFileEntity;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.entity.TaDepartmentMemberEntity;

@Mapper
public interface WorkPlanDao extends BaseMapper<WorkPlanEntity>{
	/**
	 * 添加
	 * @param sysWorkPlanEntity
	 */
	void savePlan(WorkPlanEntity sysWorkPlanEntity);
	/**
	 * 条件查询
	 * @param params
	 * @return
	 */
	List<WorkPlanEntity> findAllPlan(@Param("params") Map<String, Object> params);
	List<WorkPlanEntity> findAllPlanCount(@Param("params") Map<String, Object> params);
	/**
	 * 修改
	 * @param sysWorkPlanEntity
	 */
	void changePlan(WorkPlanEntity sysWorkPlanEntity);
	/**
	 * 删除
	 * @param id
	 */
	void delPlan(String id);
	/**
	 * 修改工作状态
	 * @param params
	 */
	void changeExp(@Param("params") Map<String,Object> params);
	/**
	 * 查询附件链接
	 * @param params
	 * @return
	 */
	List<ZgPlanFileEntity> findFile(@Param("params") Map<String, Object> params);
	/**
	 * 附件信息入库
	 * @param zgPlanFileEntity
	 */
	void saveFile(ZgPlanFileEntity zgPlanFileEntity);
	/**
	 * 修改完成状态
	 * @param entity
	 */
	void changeFinishStatus(WorkPlanEntity entity);
	/**
	 * 返回科室列表
	 * @return
	 */
	List<TaDepartmentMemberEntity> findLowerList(Long userId);
}
