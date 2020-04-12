package com.orhon.smartcampus.modules.student.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.student.entity.Eclass;
import com.orhon.smartcampus.modules.student.service.IEclassService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;

/**
 * <p>
 * 班级表 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/student/eclass", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EclassRestController extends ApiController {

	@Autowired
	private IEclassService service;
	
	@Autowired
	private InfoService infoService; 
	
	/**
	 * 查询某学校全部班级
	 * @param Learninfo
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/getSchoolByEclass")
	@ResponseBody
	public R getSchoolByEclass(@RequestParam HashMap<String, Object> maps) {
		Integer school_id = (Integer)infoService.getCurrentUser().get("school_id");
		maps.put("school_id", school_id);
		List<Eclass> list = service.getSchoolByEclass(maps);
	    return R.ok().put("data", list);
	}
	
	/**
	 * 条件加分页查询集合
	 * @param Eclass
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/getList")
	@ResponseBody
	public R getList(Eclass eclass,PageDto dto) {
		IPage<Eclass> page = new Page<>(dto.getPage(),dto.getLimit());
		QueryWrapper<Eclass> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(eclass);
	    IPage<Eclass> pagelist = service.page(page, queryWrapper);
	    return R.ok().put("data", pagelist);
	}
	
	/**
	 * id查询一条数据
	 * @param Eclass
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/getById/{id}")
	@ResponseBody
	public R getById(@PathVariable("id") Integer id) {
		Eclass byId = service.getById(id);
	    return R.ok().put("data", byId);
	}
	
	/**
	 * id删除数据
	 * @param Eclass
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
	 * @param Eclass
	 * @param dto
	 * @return
	 */
	@PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
		Eclass eclass = JSONObject.parseObject(JSONObject.toJSONString(maps), Eclass.class);
	    boolean save = service.save(eclass);
	    if (save) {
			return R.ok().put("data",eclass);
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
		Eclass eclass = JSONObject.parseObject(JSONObject.toJSONString(maps), Eclass.class);
		service.updateById(eclass);
		return R.ok().put("data",eclass);
	}

}
