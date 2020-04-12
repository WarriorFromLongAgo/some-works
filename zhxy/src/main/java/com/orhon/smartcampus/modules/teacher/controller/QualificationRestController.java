package com.orhon.smartcampus.modules.teacher.controller;

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
import com.orhon.smartcampus.modules.teacher.entity.Qualification;
import com.orhon.smartcampus.modules.teacher.service.IQualificationService;
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
@RequestMapping(value = "/teacher/qualification", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class QualificationRestController extends ApiController {

	@Autowired
	private IQualificationService service;
	
	/**
	 * 条件加分页查询集合
	 * @param Qualification
	 * @param bao
	 * @return
	 */
	@GetMapping(value="/getList")
	@ResponseBody
	public R getList(Qualification qualification,PageDto dto) {
		IPage<Qualification> page = new Page<>(dto.getPage(),dto.getLimit());
		QueryWrapper<Qualification> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(qualification);
	    IPage<Qualification> pagelist = service.page(page, queryWrapper);
	    return R.ok().put("data", pagelist);
	}
	
	/**
	 * id查询一条数据
	 * @param Qualification
	 * @param bao
	 * @return
	 */
	@GetMapping(value="/getById/{id}")
	@ResponseBody
	public R getById(@PathVariable("id") Integer id) {
		Qualification byId = service.getById(id);
	    return R.ok().put("data", byId);
	}
	
	/**
	 * id删除数据
	 * @param Qualification
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
	 * @param Qualification
	 * @param bao
	 * @return
	 */
	@PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
		Qualification qualification = JSONObject.parseObject(JSONObject.toJSONString(maps), Qualification.class);
	    boolean save = service.save(qualification);
	    if (save) {
			return R.ok().put("data", qualification);
		}
	    return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
	}
		
	/**
	 * id修改一条记录
	 * @param Qualification
	 * @param bao
	 * @return
	 */
	@PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
		Qualification qualification = JSONObject.parseObject(JSONObject.toJSONString(maps), Qualification.class);
		service.updateById(qualification);
		return R.ok().put("data", qualification);
	}
}
