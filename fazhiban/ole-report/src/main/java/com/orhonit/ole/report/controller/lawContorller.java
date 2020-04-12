package com.orhonit.ole.report.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.report.dto.LawYearDTO;
import com.orhonit.ole.report.service.lawYear.LawService;

@RestController
@RequestMapping(value="/lawYear")
public class lawContorller {
@Autowired
private LawService LawService;

/**
 * 新增法律法规年度统计
 * @return selectLaw 查询历年新增法律法规
 */
@GetMapping("/getLawAddByEveryYear")
public Object lawAddByEveryYear(){
	List<LawYearDTO> selectLaw = this.LawService.selectLaw();
	return selectLaw;
	}
}
