package com.orhon.smartcampus.modules.systemctl.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.systemctl.entity.Operations;
import com.orhon.smartcampus.modules.systemctl.service.IOperationsService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/systemctl/operations", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OperationsRestController extends ApiController {
	
	@Autowired
	IOperationsService iOperationsService;
	

	@GetMapping(value="/getList")
	@ResponseBody
	public R getList(Operations operations,PageDto dto) {
		IPage<Operations> page = new Page<>(dto.getPage(),dto.getLimit());
		QueryWrapper<Operations> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(operations);
		 IPage<Operations> pagelist = iOperationsService.page(page, queryWrapper);
		 return R.ok().put("data", pagelist);
	}
	

	@GetMapping(value="/getById/{id}")
	@ResponseBody
	public R getById(@PathVariable("id") Integer id) {
		Operations byId = iOperationsService.getById(id);
	    return R.ok().put("data", byId);
	}
	

	@DeleteMapping(value="/delById/{id}")
	@ResponseBody
	public R delById(@PathVariable("id") Integer id) {
		iOperationsService.removeById(id);
	    return R.ok().put("msg", "删除成功");
	}
	

	@PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
		Operations apcation = JSONObject.parseObject(JSONObject.toJSONString(maps), Operations.class);
	    boolean save = iOperationsService.save(apcation);
	    if (save) {
			return R.ok().put("data", apcation);
		}
	    return R.error().put("msg" , iOperationsService.getValidationData().iterator().next().getMessage());
	}
	

	@PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
		Operations apcation = JSONObject.parseObject(JSONObject.toJSONString(maps), Operations.class);
		iOperationsService.updateById(apcation);
		return R.ok().put("data", apcation);
	}

	// TODO: 11/7/2019 获取我的权限、无分页
	@GetMapping(value="/get/my")
	@ResponseBody
	public R getMy(Operations operations,PageDto dto) {
		// 根据条件 当前用户所在学校的、分给当前用户和分给当前用户所在部门的所有权限。
		return R.ok().put("data", "");
	}

	// TODO: 11/7/2019 获取我校权限列表无分页
	@GetMapping(value="/get/ourschool")
	@ResponseBody
	public R getOurschool(Operations operations,PageDto dto) {

		// 用学校标识筛选出这个学校的所有权限
		return R.ok().put("data", "");
	}

	// TODO: 11/7/2019 获取权限模板
	@GetMapping(value="/get/template")
	@ResponseBody
	public R getTemplate(Operations operations,PageDto dto) {

		return R.ok().put("data", "");
	}


	// TODO: 11/7/2019 获取指定用户的权限
	@GetMapping(value="/get/foruser/{id}")
	@ResponseBody
	public R getForuser(Operations operations,PageDto dto,@PathVariable("id") Integer id) {
		// 根据条件 当前用户所在学校的、分给当前用户和分给当前用户所在部门的所有权限。
		return R.ok().put("data", "");
	}

	// TODO: 11/7/2019 获取指定部门、职务的权限
	@GetMapping(value="/get/fordepartment/{departmentId}/{dutyId}")
	@ResponseBody
	public R getFordepartment(Operations operations,PageDto dto,@PathVariable("departmentId") Integer departmentId,@PathVariable("dutyId") Integer dutyId) {
		// 根据条件 当前用户所在学校的、分给当前用户和分给当前用户所在部门的所有权限。
		return R.ok().put("data", "");
	}




}
