package com.orhonit.modules.generator.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.WorkPlanEntity;
import com.orhonit.modules.generator.entity.ZgPlanFileEntity;
import com.orhonit.modules.generator.service.WorkPlanService;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.entity.TaDepartmentMemberEntity;

@RestController
@RequestMapping("sys")
public class WorkPlanController extends AbstractController {

	@Autowired
	private WorkPlanService workPlanService;
	/**
	 * 添加
	 * @param sysWorkPlanEntity
	 * @return
	 */
	@RequestMapping(value = "/plan/savePlan",method = RequestMethod.POST)
	public R savePlan(@RequestBody WorkPlanEntity sysWorkPlanEntity) {
		Long userId = getUserId();
		sysWorkPlanEntity.setUserId(userId);
		workPlanService.savePlan(sysWorkPlanEntity);
		return R.ok();
	}
	/**
	 * 条件查询
	 * @param params
	 * @return
	 */
	@RequestMapping("/plan/findAllPlan")
	public R findAllPlan(@RequestParam Map<String, Object> params) {
		PageUtils page = workPlanService.findAllPlan(params);
		return R.ok().put("page", page);
	}
	/**
	 * 修改
	 * @param sysWorkPlanEntity
	 * @return
	 */
	@RequestMapping(value = "/plan/changePlan",method = RequestMethod.PUT)
	public R changePlan(@RequestBody WorkPlanEntity sysWorkPlanEntity) {
		workPlanService.changePlan(sysWorkPlanEntity);
		return R.ok();
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/plan/delPlan")
	public R delPlan(String id) {
		workPlanService.delPlan(id);
		return R.ok();
	}
	/**
	 * 修改完成状态 (走此方法的时间就是完成时间)
	 * @return
	 */
	@RequestMapping("/plan/changeFinishStatus")
	public R changeFinishStatus(String id) {
		workPlanService.changeFinishStatus(id);
		return R.ok();
	}
	/**
	 * 通报曝光
	 * @param params
	 * @return
	 */
	@RequestMapping("plan/findByExp")
	public R findByExp(@RequestParam Map<String, Object> params) {
		PageUtils page = workPlanService.findByExp(params);
		return R.ok().put("page", page);
	}

	/**
	 * 查询整改or完成列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/plan/findByChange")
	public R findByChange(@RequestParam Map<String, Object> params) {
		PageUtils page = workPlanService.findByChange(params);
		return R.ok().put("page", page);
	}
	/**
	 * 查询附件链接
	 * @param params
	 * @return
	 */
	@RequestMapping("/plan/findFile")
	public R findFile(@RequestParam Map<String, Object> params) {
		List<ZgPlanFileEntity> zgPlanFileEntity = workPlanService.findFile(params);
		return R.ok().put("zgPlanFileEntity", zgPlanFileEntity);
	}
	/**
	 * 返回科室列表
	 * @return
	 */
	@RequestMapping("/plan/findLowerList")
	public Map<String, List<TaDepartmentMemberEntity>> findLowerList() {
		Long userId = getUserId();
		return workPlanService.findLowerList(userId);
		
	}

	/**
	 * 整改/曝光按钮
	 * @param params
	 * @return
	 */
	@RequestMapping("/plan/changeExp")
	public R changeExp(@RequestParam Map<String, Object> params){
		workPlanService.changeExp(params);
		return R.ok();
	}
	
	
	
	
}
