package com.orhonit.ole.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.report.service.index.IndexService;

/**
 * 
 * <p>Title: IndexCountController</p>
 * <p>Description: 首页数据图控制器</p>
 * <p>Company: </p> 
 * @author 田俊文
 * @date 2018年2月27日 下午2:10:57
 */
@RestController
@RequestMapping(value="indexCount")
public class IndexCountController {
	
	@Autowired
	private IndexService indexService;

	/**
	 * 查询今年案件数量
	 * @return
	 */
	@GetMapping("getYearCaseCount")
	public Integer getYearCaseCount() {
		return indexService.getYearCaseCount(); 
	}
	
	/**
	 * 查询今天案件受理量
	 * @return
	 */
	@GetMapping("getNowCaseAccept")
	public Integer getNowCaseAccept() {
		return indexService.getNowCaseAccept();
	}
	
	/**
	 * 查询今天案件结案量
	 * @return
	 */
	@GetMapping("getNowCaseClose")
	public Integer getNowCaseClose() {
		return indexService.getNowCaseClose();
	}
	
	/**
	 * 查询今年预警总数
	 * @return
	 */
	@GetMapping("getYearWarnInfoCount")
	public Integer getYearWarnInfoCount() {
		return indexService.getYearWarnInfoCount(); 
	}
	
	/**
	 * 查询今日预警总数
	 * @return
	 */
	@GetMapping("getNowWarnInfoCount")
	public Integer getNowWarnInfoCount() {
		return indexService.getNowWarnInfoCount(); 
	}
}
