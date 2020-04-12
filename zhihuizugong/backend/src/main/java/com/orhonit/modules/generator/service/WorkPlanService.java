package com.orhonit.modules.generator.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.WorkPlanEntity;
import com.orhonit.modules.generator.entity.ZgPlanFileEntity;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.entity.TaDepartmentMemberEntity;


public interface WorkPlanService extends IService<WorkPlanEntity>{
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
	PageUtils findAllPlan(Map<String, Object> params);
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
	 * 通报曝光
	 * @param params
	 * @return
	 */
	PageUtils findByExp(Map<String, Object> params);
	/**
	 * 查询整改or完成列表
	 * @param exposure
	 * @return
	 */
	PageUtils findByChange(Map<String, Object> params);
	/**
	 * 查询附件链接
	 * @param params
	 * @return
	 */
	List<ZgPlanFileEntity> findFile(Map<String, Object> params);
	/**
	 * 修改完成状态
	 * @param id
	 */
	void changeFinishStatus(String id);
	/**
	 * 返回科室列表
	 * @return
	 */
	Map<String, List<TaDepartmentMemberEntity>> findLowerList(Long userId);

	/**
	 * 整改/曝光按钮
	 * @param params
	 */
	void changeExp(Map<String, Object> params);
}
