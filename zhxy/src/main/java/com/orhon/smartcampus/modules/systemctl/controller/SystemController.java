package com.orhon.smartcampus.modules.systemctl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.modules.core.annotation.JsonForamtCmd;
import com.orhon.smartcampus.modules.core.annotation.JsonFormat;
import com.orhon.smartcampus.modules.systemctl.service.*;
import com.orhon.smartcampus.modules.user.entity.Users;
import com.orhon.smartcampus.utils.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.systemctl.entity.OrgDepartments;
import com.orhon.smartcampus.modules.systemctl.entity.OrgDuties;
import com.orhon.smartcampus.utils.R;

/**
 * 权限系统
 * @author bao
 *
 */
@RestController
@RequestMapping(value = "/systemctl/system", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SystemController {


	@Autowired
	private ISystemService service;

	@Autowired
	private InfoService infoService;

	@Autowired
	private IOrgDutiesService dutiesService;

	@Autowired
	private IOrgDepartmentsService departmentsService;


	@Autowired
	private IMenusService menusService;

	@Autowired
	private IModulesService modulesService;

	@Autowired
	private IWidgetsService widgetsService;

	@Autowired
	private IOperationsService operationsService;

	/**
	 * 维护中间表 systemctl_org_department_user_relation
	 * org_duties 职务表 duty_id
	 * org_departments 部门表  department_id
	 * user_users 用户表 user_id 数组
	 *  保存
	 * @return
	 */
	@PostMapping(value="/department/users/add")
	@ResponseBody
	public R departmentUsersAdd(@RequestBody HashMap<String, Object> maps) {
		return service.inserts(maps);
	}


	/**
	 * 维护中间表 systemctl_org_department_user_relation
	 * org_duties 职务表 duty_id
	 * org_departments 部门表  department_id
	 * user_users 用户表 user_id 数组
	 * 删除
	 * @return
	 */
	@PostMapping(value="/department/users/del")
	@ResponseBody
	public R departmentUserDel(@RequestBody HashMap<String, Object> maps) {
		return service.Delete(maps);
	}


	/**
	 * 是否是 领导  0不是  1是
	 * @param maps
	 * @return
	 */
	@PostMapping(value="/department/users/isLeaders")
	@ResponseBody
	public R departmentUsersIsLeaders(@RequestBody HashMap<String, Object> maps) {
		return service.isLeaders(maps);
	}


	/**
	 * 获取当前用户 获取职务信息
	 * @return
	 */
	@GetMapping(value="/get/users/departments")
	public R getUsersDepartments() {
		return service.getUserDepartments();
	}

	/**
	 * 获取当前用户 获取部门信息
	 * @return
	 */
	@GetMapping(value="/get/users/duties")
	public R getDuties() {
		 return service.getUserDuties();
	}

	/**
	 * 获取当前用户 全部操作权限
	 * @return
	 */
	@GetMapping(value="/get/users/operations")
	public R getUserOperations() {
		return service.getUserOperations();
	}

    /**
     * 查询 某学校 全部 部门和职位
     * OrgDepartments 部门表
     * OrgDuties  职位表
     * @param
     * @return
     */
	@GetMapping("/getListall")
	@ResponseBody
	public R getList() {
		//全部部门
		Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
		QueryWrapper<OrgDepartments> departmentWrapper = new QueryWrapper<>();
		departmentWrapper.eq("school_id", school_id);
		List<OrgDepartments> DepartmentList = departmentsService.list(departmentWrapper);
		//全部职务
		QueryWrapper<OrgDuties> dutiesWrapper = new QueryWrapper<>();
		dutiesWrapper.eq("school_id", school_id);
		List<OrgDuties> DutiesList = dutiesService.list(dutiesWrapper);
		HashMap<String, Object>  map = new HashMap<String, Object>();
		map.put("DepartmentList", DepartmentList);
		map.put("DutiesList", DutiesList);
		return R.ok().put("data", map);
	}

	/**
	 * 保存 部门和职位的关系 中间表
	 * systemctl_org_department_duty_relation
	 * 部门 职位 中间表
	 * @param maps
	 * @return
	 */
	@PostMapping(value="/department/duty/add")
    @ResponseBody
    public R departmentDutyAdd(@RequestBody HashMap<String, Object> maps) {
		return service.insertsSoddr1(maps);
    }

	/**
	 * 删除 部门和职位的关系 中间表
	 * systemctl_org_department_duty_relation
	 * 部门 职位 中间表
	 * @param maps
	 * @return
	 */
	@PostMapping(value="/department/duty/del")
    @ResponseBody
    public R departmentDutyDel(@RequestBody HashMap<String, Object> maps) {
		return service.delSoddr(maps);
    }


	/**
	 * 保存 用户和操作权限的关系 中间表
	 * systemctl_operation_user_relation
	 * 用户 操作权限 中间表
	 * user_id
	 * operation_id
	 * @param maps
	 * @return
	 */
	@PostMapping(value="/user/operation/add")
    @ResponseBody
    public R userOperationAdd(@RequestBody HashMap<String, Object> maps) {
		return service.insertsSour(maps);
    }

	/**
	 * 删除 部门和职位的关系 中间表
	 * systemctl_operation_user_relation
	 * 用户 操作权限 中间表
	 * @param maps
	 * @return
	 */
	@PostMapping(value="/user/operation/del")
    @ResponseBody
    public R userOperationDel(@RequestBody HashMap<String, Object> maps) {
		return service.delSour(maps);
    }


	/**
	 * 获取本用户所在学校 的 全部系统模块
	 *
	 * systemctl_modules
	 */
	@GetMapping("/get/user/modules")
	@ResponseBody
	@FastJsonView
	@JsonFormat({
			@JsonForamtCmd(cmd = "raw" , okey = "clients"),
			@JsonForamtCmd(cmd = "raw" , okey = "module_name")
	})
	public R getUserModules() {
		return service.getUserModules();
	}

    @GetMapping("/get/user/menus")
    @ResponseBody
    @FastJsonView
    @JsonFormat({
            @JsonForamtCmd(cmd = "raw" , okey = "clients"),
            @JsonForamtCmd(cmd = "raw" , okey = "title")
    })
    public R getUserMenus() {
    	return service.getUserMenus();
    }

    @GetMapping("/get/user/department/oprations")
    @ResponseBody
    @FastJsonView
    @JsonFormat({
            @JsonForamtCmd(cmd = "raw" , okey = "clients"),
            @JsonForamtCmd(cmd = "raw" , okey = "title")
    })
    public R getUsersDepartmentOprations() {
        return service.getUsersDepartmentOprations();
    }

	/**
	 * 获取某个系统 模块下的 全部页面组件
	 *
	 * systemctl_modules
	 */
	@GetMapping("/get/modeule/widgets")
	@ResponseBody
	public R getModeuleWidgets(@RequestBody HashMap<String, Object> maps) {
		return service.getModeuleWidgets(maps);
	}


	/**
	 * 获取某个系统 模块下的 全部菜单
	 *
	 * systemctl_widgets
	 */
	@GetMapping("/get/modeule/menus")
	public R getModeuleMenus(@RequestParam HashMap<String, Object> maps) {
		return service.getModeuleMenus(maps);
	}

	/**
	 * 获取操作权限表的 所属菜单列表和页面组件表数据
	 *
	 * systemctl_operations的id
	 */
	@PostMapping("/get/menus/and/widgets")
	@ResponseBody
	public R getMenusAndWidgets(@RequestBody(required=false) HashMap<String, Object> maps) {
		return service.getMenusAndWidgets(maps);
	}


	@GetMapping("/get/module/menus_rendered")
	public R getRenderedMenu(@RequestParam(name = "module_id") Long module_id){
		return service.getRenderMenu(module_id);
	}


	//get/userinfo
	@GetMapping(value="/get/userinfo")
	@ResponseBody
	public R getUserinfo() {
		HashMap userInfo = infoService.getCurrentUser();

		//if (userInfo.get("user_type") =="teacher"){
		//
		//}
		return R.ok().put("data", userInfo);
	}

	@GetMapping(value="/get/statistics")
	@ResponseBody
	public R getStatistics() {
		JSONObject statisticsData = new JSONObject();
		statisticsData.put("menus",menusService.count());
		statisticsData.put("modules",modulesService.count());
		statisticsData.put("widgets",widgetsService.count());
		statisticsData.put("operations",operationsService.count());

		return R.ok().put("data", statisticsData);
	}

}
