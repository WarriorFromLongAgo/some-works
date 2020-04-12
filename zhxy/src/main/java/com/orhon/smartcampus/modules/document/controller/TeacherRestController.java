package com.orhon.smartcampus.modules.document.controller;

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
import com.orhon.smartcampus.modules.document.entity.Teacher;
import com.orhon.smartcampus.modules.document.service.ITeacherService;
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
@RequestMapping(value = "/document/teacher", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TeacherRestController extends ApiController {

	@Autowired
	private ITeacherService service;
	
	/**
	 * 条件加分页查询集合
	 * @param Teacher
	 * @param bao
	 * @return
	 */
	@GetMapping(value="/getList")
	@ResponseBody
	public R getList(Teacher teacher,PageDto dto) {
		IPage<Teacher> page = new Page<>(dto.getPage(),dto.getLimit());
		QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(teacher);
	    IPage<Teacher> pagelist = service.page(page, queryWrapper);
	    return R.ok().put("data", pagelist);
	}
	
	/**
	 * id查询一条数据
	 * @param Teacher
	 * @param bao
	 * @return
	 */
	@GetMapping(value="/getById/{id}")
	@ResponseBody
	public R getById(@PathVariable("id") Integer id) {
		Teacher byId = service.getById(id);
	    return R.ok().put("data", byId);
	}
	
	/**
	 * id删除数据
	 * @param Teacher
	 * @param bao
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
	 * @param Teacher
	 * @param bao
	 * @return
	 */
	@PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
		Teacher teacher = JSONObject.parseObject(JSONObject.toJSONString(maps), Teacher.class);
	    boolean save = service.save(teacher);
	    if (save) {
			return R.ok().put("data", teacher);
		}
	    return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
	}
		
	/**
	 * id修改一条记录
	 * @param Teacher
	 * @param bao
	 * @return
	 */
	@PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
		Teacher teacher = JSONObject.parseObject(JSONObject.toJSONString(maps), Teacher.class);
		service.updateById(teacher);
		return R.ok().put("data", teacher);
	}
	//查看
	@PutMapping("/look")
	@ResponseBody
	public R look(@RequestBody HashMap<String, Object> maps) {
		Teacher teacher = JSONObject.parseObject(JSONObject.toJSONString(maps), Teacher.class);
		service.updateById(teacher);
		return R.ok().put("data", teacher);
	}
}
