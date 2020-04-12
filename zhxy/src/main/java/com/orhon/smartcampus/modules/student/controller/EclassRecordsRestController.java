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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orhon.smartcampus.framework.controller.ApiController;
import com.orhon.smartcampus.modules.student.entity.EclassRecords;
import com.orhon.smartcampus.modules.student.service.IEclassRecordsService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bao
 */

@RestController
@RequestMapping(value = "/student/eclassRecords", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EclassRecordsRestController extends ApiController {

	@Autowired
	private IEclassRecordsService service;

	/**
	 * 条件加分页查询集合
	 * @param EclassRecords
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/getList")
	@ResponseBody
	public R getList(EclassRecords eclassRecords,PageDto dto) {
		IPage<EclassRecords> page = new Page<>(dto.getPage(),dto.getLimit());
		QueryWrapper<EclassRecords> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(eclassRecords);
		IPage<EclassRecords> pagelist = service.page(page, queryWrapper);
		return R.ok().put("data", pagelist);
	}

	/**
	 * id查询一条数据
	 * @param EclassRecords
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/getById/{id}")
	@ResponseBody
	public R getById(@PathVariable("id") Integer id) {
		EclassRecords byId = service.getById(id);
		return R.ok().put("data", byId);
	}

	/**
	 * id删除数据
	 * @param EclassRecords
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
	 * @param EclassRecords
	 * @param dto
	 * @return
	 */
	@PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
		EclassRecords eclassRecords = JSONObject.parseObject(JSONObject.toJSONString(maps), EclassRecords.class);
		boolean save = service.save(eclassRecords);
		if (save) {
			return R.ok().put("data", eclassRecords);
		}
		return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
	}

	/**
	 * id修改一条记录
	 * @param EclassRecords
	 * @param dto
	 * @return
	 */
	@PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
		EclassRecords eclassRecords = JSONObject.parseObject(JSONObject.toJSONString(maps), EclassRecords.class);
		service.updateById(eclassRecords);
		return R.ok().put("data", eclassRecords);
	}


	/**
	 * 多个学生进行 分班调班
	 * @param EclassRecords
	 * @param dto
	 * @return
	 */
	@PostMapping(value="/students/records")
	@ResponseBody
	public R studentsRecords(@RequestBody HashMap<String, Object> maps) {
		String eclassRecords = service.eclassRecordsSave(maps);
		return R.ok().put("msg", eclassRecords);
	}


}
