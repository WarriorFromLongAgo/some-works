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
import com.orhon.smartcampus.modules.teacher.entity.Wtcationalqualification;
import com.orhon.smartcampus.modules.teacher.service.IWtcationalqualificationService;
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
@RequestMapping(value = "/teacher/wtcationalqualification", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class WtcationalqualificationRestController extends ApiController {

	@Autowired
	private IWtcationalqualificationService service;
	
	/**
	 * 条件加分页查询集合
	 * @param Wtcationalqualification
	 * @param bao
	 * @return
	 */
	@GetMapping(value="/getList")
	@ResponseBody
	public R getList(Wtcationalqualification wtcation,PageDto dto) {
		IPage<Wtcationalqualification> page = new Page<>(dto.getPage(),dto.getLimit());
		QueryWrapper<Wtcationalqualification> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(wtcation);
	    IPage<Wtcationalqualification> pagelist = service.page(page, queryWrapper);
	    return R.ok().put("data", pagelist);
	}
	
	/**
	 * id查询一条数据
	 * @param Wtcationalqualification
	 * @param bao
	 * @return
	 */
	@GetMapping(value="/getById/{id}")
	@ResponseBody
	public R getById(@PathVariable("id") Integer id) {
		Wtcationalqualification byId = service.getById(id);
	    return R.ok().put("data", byId);
	}
	
	/**
	 * id删除数据
	 * @param Wtcationalqualification
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
	 * @param Wtcationalqualification
	 * @param bao
	 * @return
	 */
	@PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
		Wtcationalqualification wtcation = JSONObject.parseObject(JSONObject.toJSONString(maps), Wtcationalqualification.class);
	    boolean save = service.save(wtcation);
	    if (save) {
			return R.ok().put("data", wtcation);
		}
	    return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
	}
		
	/**
	 * id修改一条记录
	 * @param Wtcationalqualification
	 * @param bao
	 * @return
	 */
	@PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
		Wtcationalqualification wtcation = JSONObject.parseObject(JSONObject.toJSONString(maps), Wtcationalqualification.class);
		service.updateById(wtcation);
		return R.ok().put("data", wtcation);
	}
}
