package com.orhon.smartcampus.modules.systemctl.controller;

import com.orhon.smartcampus.modules.core.service.InfoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.systemctl.entity.Menus;
import com.orhon.smartcampus.modules.systemctl.service.IMenusService;
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
@RequestMapping(value = "/systemctl/menus", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MenusRestController extends ApiController {


	@Autowired
	IMenusService iMenusService;

	@Autowired
	private InfoService info;


	@RequestMapping("/test")
	public R test(){
		return R.ok().put("userInfo" , info.getCurruentUsresSemster());
	}

	@GetMapping(value="/get/Ids")
	@ResponseBody
	public R getIds(Menus menus,@RequestParam("ids") String ids) {
		QueryWrapper<Menus> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(menus);
		
		List<Integer> id = new ArrayList<Integer>();
		String[] split = ids.split(",");
		for (String string : split) {
			id.add(Integer.parseInt(string));
		}
		queryWrapper.in("id",id);
		List<Menus> list = iMenusService.list(queryWrapper);
		return R.ok().put("data", list);
	}

	@GetMapping(value="/get/list")
	@ResponseBody
	public R getList(Menus menus,PageDto dto) {
		// TODO: 11/7/2019 添加筛选条件 
		IPage<Menus> page = new Page<>(dto.getPage(),dto.getLimit());
		QueryWrapper<Menus> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(menus);
		IPage<Menus> pagelist = iMenusService.page(page, queryWrapper);
		return R.ok().put("data", pagelist);
	}


	@GetMapping(value="/get/{id}")
	@ResponseBody
	public R getById(@PathVariable("id") Integer id) {
		Menus byId = iMenusService.getById(id);
		return R.ok().put("data", byId);
	}


	@DeleteMapping(value="/del/{id}")
	@ResponseBody
	public R delById(@PathVariable("id") Integer id) {
		iMenusService.removeById(id);
		return R.ok().put("msg", "删除成功");
	}


	@PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
		Menus apcation = JSONObject.parseObject(JSONObject.toJSONString(maps), Menus.class);
		boolean save = iMenusService.save(apcation);
		if (save) {
			return R.ok().put("data", apcation);
		}
		return R.error().put("msg" , iMenusService.getValidationData().iterator().next().getMessage());
	}


	@PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
		Menus apcation = JSONObject.parseObject(JSONObject.toJSONString(maps), Menus.class);
		iMenusService.updateById(apcation);
		return R.ok().put("data", apcation);
	}

	// TODO: 11/7/2019  无分页列表
	@GetMapping("/get/nopaginglist")
	@ResponseBody
	public R getNopaginglist(Menus menus) {

		return R.ok().put("data", "");
	}

	// TODO: 11/7/2019 获取当前学校的模块列表、无分页
	@GetMapping("/get/ourschool")
	@ResponseBody
	public R getOurschool(Menus menus, PageDto dto) {

		return R.ok().put("data", "");
	}

	// TODO: 11/7/2019 获取当前用户的模块列表、无分页
	@GetMapping("/get/my")
	@ResponseBody
	public R getMy(Menus menus, PageDto dto) {

		return R.ok().put("data", "");
	}

	// TODO: 11/7/2019 获取指定用户的权限
	@GetMapping(value="/get/foruser/{id}")
	@ResponseBody
	public R getForuser(Menus menus,PageDto dto,@PathVariable("id") Integer id) {
		// 根据条件 当前用户所在学校的、分给当前用户和分给当前用户所在部门的所有权限。
		return R.ok().put("data", "");
	}

	// TODO: 11/7/2019 获取指定部门、职务的权限
	@GetMapping(value="/get/fordepartment/{departmentId}/{dutyId}")
	@ResponseBody
	public R getFordepartment(Menus menus,PageDto dto,@PathVariable("departmentId") Integer departmentId,@PathVariable("dutyId") Integer dutyId) {
		// 根据条件 当前用户所在学校的、分给当前用户和分给当前用户所在部门的所有权限。
		return R.ok().put("data", "");
	}
}
