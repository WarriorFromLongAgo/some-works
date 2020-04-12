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
import com.orhon.smartcampus.modules.teacher.entity.Lncationalqualification;
import com.orhon.smartcampus.modules.teacher.service.ILncationalqualificationService;
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
@RequestMapping(value = "/teacher/lncationalqualification", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LncationalqualificationRestController extends ApiController {

	@Autowired
	private ILncationalqualificationService service;
	
	/**
	 * 条件加分页查询集合
	 * @param Lncationalqualification
	 * @param bao
	 * @return
	 */
	@GetMapping(value="/getList")
	@ResponseBody
	public R getList(Lncationalqualification lncation,PageDto dto) {
		IPage<Lncationalqualification> page = new Page<>(dto.getPage(),dto.getLimit());
		QueryWrapper<Lncationalqualification> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(lncation);
	    IPage<Lncationalqualification> pagelist = service.page(page, queryWrapper);
	    return R.ok().put("data", pagelist);
	}
	
	/**
	 * id查询一条数据
	 * @param Lncationalqualification
	 * @param bao
	 * @return
	 */
	@GetMapping(value="/getById/{id}")
	@ResponseBody
	public R getById(@PathVariable("id") Integer id) {
		Lncationalqualification byId = service.getById(id);
	    return R.ok().put("data", byId);
	}
	
	/**
	 * id删除数据
	 * @param Lncationalqualification
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
	 * @param Lncationalqualification
	 * @param bao
	 * @return
	 */
	@PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
		Lncationalqualification lncation = JSONObject.parseObject(JSONObject.toJSONString(maps), Lncationalqualification.class);
	    boolean save = service.save(lncation);
	    if (save) {
			return R.ok().put("data", lncation);
		}
	    return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
	}
		
	/**
	 * id修改一条记录
	 * @param Lncationalqualification
	 * @param bao
	 * @return
	 */
	@PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
		Lncationalqualification lncation = JSONObject.parseObject(JSONObject.toJSONString(maps), Lncationalqualification.class);
		service.updateById(lncation);
		return R.ok().put("data", lncation);
	}
}
