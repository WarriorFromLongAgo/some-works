package com.orhon.smartcampus.modules.student.controller;

import com.orhon.smartcampus.modules.core.service.InfoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.student.entity.Arrives;
import com.orhon.smartcampus.modules.student.service.IArrivesService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;

/**
 * <p>
 * 届 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/student/arrives", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ArrivesRestController extends ApiController {

	@Autowired
	private IArrivesService service;

	@Autowired
	private InfoService infoService;
	
	/**
	 * 条件加分页查询集合
	 * @param Arrives
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/getList")
	@ResponseBody
	public R getList(Arrives arrives,PageDto dto) {
		IPage<Arrives> page = new Page<>(dto.getPage(),dto.getLimit());
		QueryWrapper<Arrives> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(arrives);
	    IPage<Arrives> pagelist = service.page(page, queryWrapper);
	    return R.ok().put("data", pagelist);
	}
	
	/**
	 * 条件加分页查询集合
	 * @param Arrives
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/getAllList")
	@ResponseBody
	public R getAllList(Arrives arrives) {
		Integer schoolID = infoService.getCurrentUserSchoolID();
		QueryWrapper<Arrives> queryWrapper = new QueryWrapper<>();
		arrives.setSchool_id(schoolID);
		queryWrapper.setEntity(arrives);
	    List<Arrives> list = service.list(queryWrapper);
	    return R.ok().put("data",list);
	}
	
	/**
	 * id查询一条数据
	 * @param Arrives
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/getById/{id}")
	@ResponseBody
	public R getById(@PathVariable("id") Integer id) {
		Arrives byId = service.getById(id);
	    return R.ok().put("data", byId);
	}
	
	/**
	 * id删除数据
	 * @param Arrives
	 * @param dto
	 * @return
	 */
	@DeleteMapping(value="/delById/{id}")
	@ResponseBody
	public R delById(@PathVariable("id") Integer id) {
	    service.removeById(id);
	    return R.ok().put("msg", "删除成功");
	}
	
	/**
	 * 新增一条记录
	 * @param Arrives
	 * @param dto
	 * @return
	 */
	@PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
		Arrives arrives = JSONObject.parseObject(JSONObject.toJSONString(maps), Arrives.class);
	    boolean save = service.save(arrives);
	    if (save) {
			return R.ok().put("data", arrives);
		}
	    return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
	}
	
	
	
	/**
	 * id修改一条记录
	 * @param Timetabl
	 * @param dto
	 * @return
	 */
	@PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
		Arrives arrives = JSONObject.parseObject(JSONObject.toJSONString(maps), Arrives.class);
		service.updateById(arrives);
		return R.ok().put("data", arrives);
	}


	@GetMapping(value="/get/all")
	@ResponseBody
	public R getAll(Arrives arrives) {
		Integer  schoolID = infoService.getCurrentUserSchoolID();
		QueryWrapper<Arrives> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(arrives);
		queryWrapper.eq("school_id",schoolID);
		List<Arrives> pagelist = service.list(queryWrapper);
		return R.ok().put("data", pagelist);
	}

}
