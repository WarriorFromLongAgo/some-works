package com.orhon.smartcampus.modules.document.controller;

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
import com.orhon.smartcampus.modules.document.entity.Approval;
import com.orhon.smartcampus.modules.document.service.IApprovalService;
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
@RequestMapping(value = "/document/approval", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ApprovalRestController extends ApiController {

	@Autowired
	private IApprovalService service;
	
	/**
	 * 条件加分页查询集合
	 * @param Approval
	 * @param bao
	 * @return
	 */
	@GetMapping(value="/getList")
	@ResponseBody
	public R getList(Approval approval,PageDto dto) {
		IPage<Approval> page = new Page<>(dto.getPage(),dto.getLimit());
		QueryWrapper<Approval> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntity(approval);
	    IPage<Approval> pagelist = service.page(page, queryWrapper);
	    return R.ok().put("data", pagelist);
	}
	
	/**
	 * id查询一条数据
	 * @param Approval
	 * @param bao
	 * @return
	 */
	@GetMapping(value="/getById/{id}")
	@ResponseBody
	public R getById(@PathVariable("id") Integer id) {
		Approval byId = service.getById(id);
	    return R.ok().put("data", byId);
	}
	
	/**
	 * id删除数据
	 * @param Approval
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
	 * @param Approval
	 * @param bao
	 * @return
	 */
	@PostMapping(value="/save")
	@ResponseBody
	public R save(@RequestBody HashMap<String, Object> maps) {
		Approval approval = JSONObject.parseObject(JSONObject.toJSONString(maps), Approval.class);
	    boolean save = service.save(approval);
	    if (save) {
			return R.ok().put("data",approval);
		}
	    return R.error().put("msg" , service.getValidationData().iterator().next().getMessage());
	}
		
	/**
	 * id修改一条记录
	 * @param Approval
	 * @param bao
	 * @return
	 */
	@PutMapping("/update")
	@ResponseBody
	public R update(@RequestBody HashMap<String, Object> maps) {
		Approval approval = JSONObject.parseObject(JSONObject.toJSONString(maps), Approval.class);
		service.updateById(approval);
		return R.ok().put("data",approval);
	}
}
