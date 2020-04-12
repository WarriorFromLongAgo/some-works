package com.orhonit.modules.generator.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.app.entity.AppEverybodyShareEntity;
import com.orhonit.modules.generator.app.service.AppEverybodyShareService;


/**
  * 管理端 大家来分享
 * @author YaoSC
 *
 */
@RestController
@RequestMapping("/everybodyshare")
public class everybodyShareController {
	@Autowired
	AppEverybodyShareService appEverybodyShareService;
	
	
	/**
	  * 列表
	 * @param params
	 * @return
	 */
	@GetMapping("/list")
	public R list(@RequestParam Map<String,Object>params) {
		PageUtils page =appEverybodyShareService.queryPage(params);
		return R.ok().put("page", page);
	}
	
	
	/**
	 * 删除
	 * @param shareId
	 * @return
	 */
	@DeleteMapping("/delete")
	public R delete(@RequestBody Integer shareId ) {
		if(shareId>0) {
			appEverybodyShareService.deleteEveryBodyById(shareId);
			return R.ok();
		}
		return R.parameterIsNul();
	}
	
	/**
	 * 详情
	 * @param shareId
	 * @return
	 */
	@GetMapping("/info/{shareId}")
	public R info(@PathVariable("shareId") Integer shareId) {
		AppEverybodyShareEntity entity =appEverybodyShareService.selectOne(shareId);
		return R.ok().put("AppEverybodyShareEntity", entity);
	}
	
}
