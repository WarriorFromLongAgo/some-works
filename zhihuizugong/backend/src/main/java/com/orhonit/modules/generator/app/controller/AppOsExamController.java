package com.orhonit.modules.generator.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.app.entity.AppOsExamEntity;
import com.orhonit.modules.generator.app.service.AppOsExamService;
import com.orhonit.modules.generator.app.vo.AppOsExamVO;


/**
 * APP端试卷题
 * @author YaoSC
 *
 */
@RestController
@RequestMapping("/app/osexam")
public class AppOsExamController {
	
	@Autowired
	AppOsExamService service;
	
	
	
	/**
	 * 试卷列表
	 * @param params
	 * @return
	 */
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		 PageUtils page = service.queryPage(params);
		 return R.ok().put("page", page);
	}
	
	/**
	 * 查看试卷
	 * @param examId
	 * @return
	 */
	@GetMapping("/info/{examId}")
	public AppOsExamEntity info(@PathVariable("examId")String examId) {
		return service.selectById(examId);
	}
	
	
	/**
	 *  获取当前可以答题的试卷
	 * @return
	 */
		@GetMapping("/getExamNow")
		public List<AppOsExamVO> getExamNow() {
			return service.getExamNow();
		}


}
