package com.orhonit.modules.religion.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.R;
import com.orhonit.modules.religion.domain.ReligionSiteDomain;
import com.orhonit.modules.religion.service.ReligionPersonGroupService;
import com.orhonit.modules.religion.service.ReligionSiteService;

/**
 * 活动场所信息
 * @author 	cyf
 * @date	2018/11/12 下午4:16:04
 */
@RestController
@RequestMapping("religionsite")
public class ReligionSiteController {
	
	@Autowired
	private ReligionSiteService siteService;
	
	

	/**
	 * 通过活动场所id删除活动场所信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/site",method=RequestMethod.DELETE)
	public R deleteReligionSiteById( @RequestParam(value="id")Long id) {
		if(null != id) {
			try {
				siteService.deleteReligionSiteById(id);
			} catch (Exception e) {
				return R.error();
			}
			return R.ok();
		}
		return R.parameterIsNul();
	}
	
	
	
	/**
	 * 查询所有活动场所信息
	 * @param religionSiteDomain
	 * @return
	 */
	@RequestMapping(value="/site",method=RequestMethod.GET)
	public R selectReligionSite(
			@RequestParam(value="page",defaultValue="1",required=false)int page,
			@RequestParam(value="pageSize",defaultValue="10",required=false)int pageSize,
			@RequestParam(value="type",required=false)String type,
			@RequestParam(value="gacha",required=false)String gacha) {
		Map<String, Object> results = siteService.selectReligionSiteAll(page, pageSize, type, gacha);
		return R.ok().put("results", results);
	}
		
	
	/**
	 * 插入或更新活动场所信息
	 * @param religionSiteDomain
	 * @return
	 */
	@RequestMapping(value="/site",method=RequestMethod.POST)
	public R insertOrUpdateReligionSite(@RequestBody ReligionSiteDomain religionSiteDomain) {
		if(null != religionSiteDomain) {
			try {
				siteService.insertOrUpdateReligionSite(religionSiteDomain);
			} catch (Exception e) {
				return R.error();
			}
			return R.ok();
		}
		return R.parameterIsNul();
	}
}
