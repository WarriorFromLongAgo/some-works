package com.orhonit.modules.religion.rest;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.R;
import com.orhonit.modules.religion.entity.SysArea;
import com.orhonit.modules.religion.service.SysAreaService;

@RestController
@RequestMapping("/area")
public class SysAreaController {

	@Autowired
	private SysAreaService areaService;

	/**
	 * 通过嘎查村所有
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/name", method = RequestMethod.GET)
	public R selectAreaByLikeName() {
		List<SysArea> areas = null;
		areas = areaService.selectAreaGacha();
		return R.ok().put("areas", areas);
	}

	/**
	 * 通过父级id查询地区信息 默认查询所有省
	 * 
	 * @return
	 */
	@RequestMapping(value = "/parent", method = RequestMethod.GET)
	public R selectAreaByParentId(
			@RequestParam(value = "parentId", defaultValue = "1", required = false) String parentId) {
		List<SysArea> results = null;
		if (StringUtils.isNotBlank(parentId)) {
			results = areaService.selectAreaByParentId(parentId);
		}
		return R.ok().put("results", results);
	}

}
