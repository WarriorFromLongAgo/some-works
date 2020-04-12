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
import com.orhon.smartcampus.modules.teacher.entity.Tscationalqualification;
import com.orhon.smartcampus.modules.teacher.service.ITscationalqualificationService;
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
@RequestMapping(value = "/teacher/tscationalqualification", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TscationalqualificationRestController extends ApiController {

	@Autowired
	private ITscationalqualificationService service;
	
	/**
	 * 条件加分页查询集合
	 * @param Tscationalqualification
	 * @param bao
	 * @return
	 */
	@GetMapping(value="/getList")
	@ResponseBody
	public R getList(Tscationalqualification tscation,PageDto dto) {
		IPage<Tscationalqualification> page = new Page<>(dto.getPage(),dto.getLimit());
		QueryWrapper<Tscationalqualification> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(tscation);
	    IPage<Tscationalqualification> pagelist = service.page(page, queryWrapper);
	    return R.ok().put("data", pagelist);
	}
	
	/**
	 * id查询一条数据
	 * @param Tscationalqualification
	 * @param bao
	 * @return
	 */
	@GetMapping(value="/getById/{id}")
	@ResponseBody
	public R getById(@PathVariable("id") Integer id) {
		Tscationalqualification byId = service.getById(id);
	    return R.ok().put("data", byId);
	}
	
	/**
	 * id删除数据
	 * @param Tscationalqualification
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
	 * @param Tscationalqualification
	 * @param bao
	 * @return
	 */
	@PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
		Tscationalqualification tscation = JSONObject.parseObject(JSONObject.toJSONString(maps), Tscationalqualification.class);
	    boolean save = service.save(tscation);
	    if (save) {
			return R.ok().put("data",tscation);
		}
	    return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
	}
		
	/**
	 * id修改一条记录
	 * @param Tscationalqualification
	 * @param bao
	 * @return
	 */
	@PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
		Tscationalqualification tscation = JSONObject.parseObject(JSONObject.toJSONString(maps), Tscationalqualification.class);
		service.updateById(tscation);
		return R.ok().put("data",tscation);
	}
}
