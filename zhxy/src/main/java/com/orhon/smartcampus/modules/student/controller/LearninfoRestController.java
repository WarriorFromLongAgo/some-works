package com.orhon.smartcampus.modules.student.controller;

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
import com.orhon.smartcampus.modules.student.entity.Learninfo;
import com.orhon.smartcampus.modules.student.entity.SInformation;
import com.orhon.smartcampus.modules.student.service.ILearninfoService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;

/**
 * <p>
 * 学生学籍基本信息表 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/student/learninfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LearninfoRestController extends ApiController {

	@Autowired
	private ILearninfoService service;
	
	@Autowired
	private InfoService infoService;
	
	/**
	 * 条件加分页查询集合
	 * @param Learninfo
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/getList")
	@ResponseBody
	public R getList(Learninfo learninfo,PageDto dto) {
		IPage<Learninfo> page = new Page<>(dto.getPage(),dto.getLimit());
		QueryWrapper<Learninfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(learninfo);
	    IPage<Learninfo> pagelist = service.page(page, queryWrapper);
	    return R.ok().put("data", pagelist);
	}
	
	/**
	 * 条件查询集合
	 * @param Learninfo
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/allList")
	@ResponseBody
	public R allList(Learninfo learninfo) {
		
		QueryWrapper<Learninfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(learninfo);
	    List<Learninfo> list = service.list(queryWrapper);
	    return R.ok().put("data", list);
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
		Learninfo byId = service.getById(id);
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
		Learninfo learninfo = JSONObject.parseObject(JSONObject.toJSONString(maps), Learninfo.class);
	    boolean save = service.save(learninfo);
	    if (save) {
			return R.ok().put("data",learninfo);
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
		Learninfo learninfo = JSONObject.parseObject(JSONObject.toJSONString(maps), Learninfo.class);
		service.updateById(learninfo);
		return R.ok().put("data",learninfo);
	}
	
}
