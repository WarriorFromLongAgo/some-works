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
import com.orhon.smartcampus.modules.teacher.entity.Apcationalqualification;
import com.orhon.smartcampus.modules.teacher.service.IApcationalqualificationService;
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
@RequestMapping(value = "/teacher/apcationalqualification", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ApcationalqualificationRestController extends ApiController {

	@Autowired
	private IApcationalqualificationService service;
	
	/**
	 * 条件加分页查询集合
	 * @param Apcationalqualification
	 * @param bao
	 * @return
	 */
	@GetMapping(value="/getList")
	@ResponseBody
	public R getList(Apcationalqualification apcation,PageDto dto) {
		IPage<Apcationalqualification> page = new Page<>(dto.getPage(),dto.getLimit());
		QueryWrapper<Apcationalqualification> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(apcation);
	    IPage<Apcationalqualification> pagelist = service.page(page, queryWrapper);
	    return R.ok().put("data", pagelist);
	}
	
	/**
	 * id查询一条数据
	 * @param Apcationalqualification
	 * @param bao
	 * @return
	 */
	@GetMapping(value="/getById/{id}")
	@ResponseBody
	public R getById(@PathVariable("id") Integer id) {
		Apcationalqualification byId = service.getById(id);
	    return R.ok().put("data", byId);
	}
	
	/**
	 * id删除数据
	 * @param Apcationalqualification
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
	 * @param Apcationalqualification
	 * @param bao
	 * @return
	 */
	@PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
		Apcationalqualification apcation = JSONObject.parseObject(JSONObject.toJSONString(maps), Apcationalqualification.class);
	    boolean save = service.save(apcation);
	    if (save) {
			return R.ok().put("data", apcation);
		}
	    return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
	}
		
	/**
	 * id修改一条记录
	 * @param Apcationalqualification
	 * @param bao
	 * @return
	 */
	@PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
		Apcationalqualification apcation = JSONObject.parseObject(JSONObject.toJSONString(maps), Apcationalqualification.class);
		service.updateById(apcation);
		return R.ok().put("data", apcation);
	}
}
