package com.orhonit.modules.generator.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.app.service.AppOsExamScopeService;

@RestController
@RequestMapping("/app/examscope")
public class AppOsExamScopeController {
	
	@Autowired
	AppOsExamScopeService appOsExamScopeService;
	
	
	
	
	/**
	  * 个人答题排行榜
	 * @param params
	 * @return
	 */
	@GetMapping("/selectPersonRank")
	public R selectPersonRank(@RequestParam Map<String, Object> params) {
		//System.out.println("page="+params.get("page")+"limit="+params.get("limit"));
		//if(!params.get("page").toString().equals("")&&!params.get("limit").toString().equals("")) {
			PageUtils page =appOsExamScopeService.selectPersonRank(params);
			return R.ok().put("page", page);
		//}
		//return R.error();
	}
}
