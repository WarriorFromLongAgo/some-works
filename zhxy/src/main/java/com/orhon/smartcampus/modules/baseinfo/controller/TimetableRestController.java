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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.baseinfo.entity.Timetable;
import com.orhon.smartcampus.modules.baseinfo.service.ITimetableService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;

/**
 * <p>
 * 课节信息表 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/baseinfo/timetable", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TimetableRestController extends ApiController {

	@Autowired
	private ITimetableService service;
	
	/**
	 * 条件加分页查询集合
	 * @param Timetabl
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/getList")
	@ResponseBody
	public R getList(Timetable timetable,PageDto dto) {
		IPage<Timetable> page = new Page<>(dto.getPage(),dto.getLimit());
		QueryWrapper<Timetable> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(timetable);
	    IPage<Timetable> pagelist = service.page(page, queryWrapper);
	    return R.ok().put("data", pagelist);
	}
	
	/**
	 * id查询一条数据
	 * @param Timetable
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/getById/{id}")
	@ResponseBody
	public R getById(@PathVariable("id") Integer id) {
		Timetable byId = service.getById(id);
	    return R.ok().put("data", byId);
	}
	
	/**
	 * id删除数据
	 * @param Timetable
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
	 * @param semester
	 * @param dto
	 * @return
	 */
	@PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
		Timetable timetable = JSONObject.parseObject(JSONObject.toJSONString(maps), Timetable.class);
	    boolean save = service.save(timetable);
	    if (save) {
			return R.ok().put("data", timetable);
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
		Timetable timetable = JSONObject.parseObject(JSONObject.toJSONString(maps), Timetable.class);
		service.updateById(timetable);
		return R.ok().put("data", timetable);
	}
	
}
