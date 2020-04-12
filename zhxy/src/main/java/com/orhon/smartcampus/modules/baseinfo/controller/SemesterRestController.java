package com.orhon.smartcampus.modules.baseinfo.controller;

import com.orhon.smartcampus.modules.core.service.InfoService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.baseinfo.entity.Semester;
import com.orhon.smartcampus.modules.baseinfo.service.ISemesterService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;


/**
 * <p>
 * 学期表 前端控制器
 * </p>
 *
 * @author bao
 */

@RestController 
@RequestMapping(value = "/baseinfo/semester", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SemesterRestController extends ApiController {
	
	@Autowired
	private ISemesterService service;
	@Autowired
	private InfoService infoService;
	
	@GetMapping(value="/getList")
	@ResponseBody
	public R getList(Semester semester,PageDto dto) {
		IPage<Semester> page = new Page<>(dto.getPage(),dto.getLimit());
		QueryWrapper<Semester> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(semester);
	    IPage<Semester> pagelist = service.page(page, queryWrapper);
	    return R.ok().put("data", pagelist);
	}
	@GetMapping(value="/getNoPageList")
	@ResponseBody
	public R getNoPageList(@RequestParam HashMap<String, Object> maps, PageDto dto) {
		String schoolyear_id = (String)maps.get("schoolyear_id");
		QueryWrapper<Semester> queryWrapper = new QueryWrapper<>();
		if(schoolyear_id !=null){
			queryWrapper.eq("schoolyear_id",schoolyear_id);
		}
	    List<Semester> list = service.list(queryWrapper);
	    return R.ok().put("data", list);
	}

	

	@GetMapping(value="/getById/{id}")
	@ResponseBody
	public R getById(@PathVariable("id") Integer id) {
	    Semester byId = service.getById(id);
	    return R.ok().put("data", byId);
	}


	@DeleteMapping(value="/delById/{id}")
	public R delById(@PathVariable("id") Integer id) {
	    service.removeById(id);
	    return R.ok().put("msg", "删除成功");
	}


	@PostMapping(value="/save")
	public R save(@RequestBody  HashMap<String, Object> maps) {
		Semester semester = JSONObject.parseObject(JSONObject.toJSONString(maps), Semester.class);
		Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
		semester.setSchool_id(school_id);
		Boolean ret = service.save(semester);
	    if (ret) {
			return R.ok().put("data", semester);
		}
	    return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
	}

	@PutMapping("/update")
	public R update(@RequestBody HashMap<String, Object> maps) {
		Semester semester = JSONObject.parseObject(JSONObject.toJSONString(maps), Semester.class);
		service.updateById(semester);
		return R.ok().put("data", semester);
	}


}
