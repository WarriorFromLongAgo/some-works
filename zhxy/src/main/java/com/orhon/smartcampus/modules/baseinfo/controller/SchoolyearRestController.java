package com.orhon.smartcampus.modules.baseinfo.controller;

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
import com.orhon.smartcampus.modules.baseinfo.entity.Schoolyear;
import com.orhon.smartcampus.modules.baseinfo.service.ISchoolyearService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;

/**
 * <p>
 * 学年表 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/baseinfo/schoolyear", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SchoolyearRestController extends ApiController {

	@Autowired
	private ISchoolyearService service;
	
	/**
	 * 条件加分页查询集合
	 * @param Schoolyear
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/getList")
	@ResponseBody
	public R getList(Schoolyear schoolyear,PageDto dto) {
		IPage<Schoolyear> page = new Page<>(dto.getPage(),dto.getLimit());
		QueryWrapper<Schoolyear> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(schoolyear);
	    IPage<Schoolyear> pagelist = service.page(page, queryWrapper);
	    return R.ok().put("data", pagelist);
	}
	@GetMapping(value="/getNoPageList")
	@ResponseBody
	public R getNoPageList(Schoolyear schoolyear,PageDto dto) {
		QueryWrapper<Schoolyear> queryWrapper = new QueryWrapper<>();
	    List<Schoolyear> list = service.list(queryWrapper);
	    return R.ok().put("data", list);
	}
	
	/**
	 * id查询一条数据
	 * @param Schoolyear
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/getById/{id}")
	@ResponseBody
	public R getById(@PathVariable("id") Integer id) {
		Schoolyear byId = service.getById(id);
	    return R.ok().put("data", byId);
	}
	
	/**
	 * id删除数据
	 * @param Schoolyear
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
	 * @param Schoolyear
	 * @param dto
	 * @return
	 */
	@PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
		Schoolyear schoolyear = JSONObject.parseObject(JSONObject.toJSONString(maps), Schoolyear.class);
	    boolean save = service.save(schoolyear);
	    if (save) {
			return R.ok().put("data", schoolyear);
		}
	    return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
	    
	}
	
	/**
	 * id修改一条记录
	 * @param Schoolyear
	 * @param dto
	 * @return
	 */
	@PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
		Schoolyear schoolyear = JSONObject.parseObject(JSONObject.toJSONString(maps), Schoolyear.class);
		service.updateById(schoolyear);
		return R.ok().put("data", schoolyear);
	}
}
