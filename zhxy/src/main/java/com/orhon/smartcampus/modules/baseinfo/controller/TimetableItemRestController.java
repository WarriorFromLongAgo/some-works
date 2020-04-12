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
import com.orhon.smartcampus.modules.baseinfo.entity.TimetableItem;
import com.orhon.smartcampus.modules.baseinfo.service.ITimetableItemService;
import com.orhon.smartcampus.utils.PageDto;
import com.orhon.smartcampus.utils.R;

/**
 * <p>
 * 作息时间表详情 前端控制器
 * </p>
 *
 * @author Orhon
 */

@RestController
@RequestMapping(value = "/baseinfo/timetableItem", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TimetableItemRestController extends ApiController {
	@Autowired
	private ITimetableItemService service;
	
	/**
	 * 条件加分页查询集合
	 * @param TimetableItem
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/getList")
	@ResponseBody
	public R getList(TimetableItem timetableItem,PageDto dto) {
		IPage<TimetableItem> page = new Page<>(dto.getPage(),dto.getLimit());
		QueryWrapper<TimetableItem> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(timetableItem);
	    IPage<TimetableItem> pagelist = service.page(page, queryWrapper);
	    return R.ok().put("data", pagelist);
	}
	
	/**
	 * id查询一条数据
	 * @param TimetableItem
	 * @param dto
	 * @return
	 */
	@GetMapping(value="/getById/{id}")
	@ResponseBody
	public R getById(@PathVariable("id") Integer id) {
		TimetableItem byId = service.getById(id);
	    return R.ok().put("data", byId);
	}
	
	/**
	 * id删除数据
	 * @param TimetableItem
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
	 * @param TimetableItem
	 * @param dto
	 * @return
	 * @throws JsonProcessingException 
	 */
	@PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
		TimetableItem timetableItem = JSONObject.parseObject(JSONObject.toJSONString(maps), TimetableItem.class);
	    boolean save = service.save(timetableItem);
	    if (save) {
			return R.ok().put("data",timetableItem );
		}
	    return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
	}
	
	/**
	 * id修改一条记录
	 * @param TimetableItem
	 * @param dto
	 * @return
	 */
	@PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
		TimetableItem timetableItem = JSONObject.parseObject(JSONObject.toJSONString(maps), TimetableItem.class);
		service.updateById(timetableItem);
		return R.ok().put("data",timetableItem );
	}
	
}
