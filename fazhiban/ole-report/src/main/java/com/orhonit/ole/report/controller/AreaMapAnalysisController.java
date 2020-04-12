package com.orhonit.ole.report.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.report.dto.AreaDTO;
import com.orhonit.ole.report.dto.area.ReportAreaDTO;
import com.orhonit.ole.report.dto.charts.BaseChartDTO;
import com.orhonit.ole.report.service.area.ReportAreaService;
import com.orhonit.ole.report.service.cases.ReportCaseHotMapService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * <p>
 * Title: AreaMapAnalysisController
 * </p>
 * <p>
 * Description: 根据地图/年份/分类/查询全市信息
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author 田俊文
 * @date 2018年1月13日 下午2:48:23
 */
@RestController
@RequestMapping("/areamap")
@Slf4j
public class AreaMapAnalysisController {

	@Autowired
	private ReportAreaService reportAreaService;

	@Autowired
	private ReportCaseHotMapService reportCaseHotMapService;

	/**
	 * 获取初始数据
	 * 
	 * @return
	 */
	@GetMapping("/areaMapInit")
	public Object areaMapInit() {
		Map<String, Object> init = new HashMap<>();
		// 查询案件表的年份字段排序
		List<String> year = reportCaseHotMapService.findCaseYearSort();
		// 功能数组
		//String[] category = { "案件数量", "执法人员数", "复议案件数", "人均执法量" };
		String[] category = { "案件数量"};
		init.put("year", year);
		init.put("category", category);
		return init;
	}

	/**
	 * 各区域不同年份的案件数量
	 * 
	 * @param year
	 * @return
	 */
	@GetMapping("/areaCaseList")
	public Object areacase(@RequestParam(value = "year", required = false, defaultValue = "") String year) {
		// 查询所有区域和面积
		List<AreaDTO> AreaBase = reportAreaService.findAreaSize();
		// 查询各区域某年的案件数量
		Map<String, Object> params = new HashMap<>();
		params.put("year", year);
		List<ReportAreaDTO> AreaCases = reportAreaService.findAreaCaseCount(params);
		// 返回参数
		Map<String, Object> result = new HashMap<>();
		// resultList所有数据
		List<ReportAreaDTO> resultList = DateProcessing(AreaBase, AreaCases);
		String[][] byAreaName = reportAreaService.findCaseCountByAreaName(year);
		result.put("resultList", resultList);
		result.put("countByAreaName", byAreaName);
		return result;
	}

	/**
	 * 各区域不同年份的复议案件数量
	 * 
	 * @param year
	 * @return
	 */
	@GetMapping("/ReconsiderationCasesNum")
	public Object ReconsiderationCasesNum(
			@RequestParam(value = "year", required = false, defaultValue = "") String year) {
		// 查询所有区域和面积
		List<AreaDTO> AreaBase = reportAreaService.findAreaSize();
		// 查询各区域某年的案件数量
		Map<String, Object> params = new HashMap<>();
		params.put("year", year);
		List<ReportAreaDTO> Reconsider = reportAreaService.findReconsiderationCount(params);
		// 返回参数
		Map<String, Object> result = new HashMap<>();
		// resultList所有数据
		List<ReportAreaDTO> resultList = DateProcessing(AreaBase, Reconsider);
		result.put("resultList", resultList);
		return result;
	}

	/**
	 * 各区域不同年份的执法人员数量
	 * 
	 * @param year
	 * @return
	 */
	@GetMapping("/lawPersonNum")
	public Object lawPersonNum(@RequestParam(value = "year", required = false, defaultValue = "") String year) {
		// 查询所有区域和面积
		List<AreaDTO> AreaBase = reportAreaService.findAreaSize();
		// 查询各区域某年的案件数量
		Map<String, Object> params = new HashMap<>();
		params.put("year", year);
		List<ReportAreaDTO> AreaPersons = reportAreaService.findAreaLowPersonCount(params);
		// 返回参数
		Map<String, Object> result = new HashMap<>();
		// resultList所有数据
		List<ReportAreaDTO> resultList = DateProcessing(AreaBase, AreaPersons);
		result.put("resultList", resultList);
		return result;
	}

	
	/**
	 * 各区域不同年份的人均执法
	 * @param year
	 * @return
	 */
	@GetMapping("/personLawAvgNum")
	public Object personLawAvgNum(@RequestParam(value = "year", required = false, defaultValue = "") String year) {
		// 查询所有区域和面积
		List<AreaDTO> AreaBase = reportAreaService.findAreaSize();
		// 查询各区域某年的案件数量
		Map<String, Object> params = new HashMap<>();
		params.put("year", year);
		List<ReportAreaDTO> AreaCases = reportAreaService.findAreaCaseCount(params);
		List<ReportAreaDTO> AreaPersons = reportAreaService.findAreaLowPersonCount(params);
		// resultList所有数据
		List<ReportAreaDTO> Case = DateProcessing(AreaBase, AreaCases);
		List<ReportAreaDTO> Person = DateProcessing(AreaBase, AreaPersons);
		// 返回数据
		List<Object> result = new ArrayList<>();
		List<ReportAreaDTO> data = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		for (int index = 0; index < Case.size(); index++) {
			Double avg = new Double(0.00);
			ReportAreaDTO obj = new ReportAreaDTO();
			obj.setName(Case.get(index).getName());
			obj.setCount(Case.get(index).getCount());
			obj.setValue(avg);
			String cases = Case.get(index).getCount();
			String persons = Person.get(index).getCount();
			if (persons != "0") {// 被除数不能为0
				avg = Double.valueOf(cases) / Double.valueOf(persons);
				BigDecimal bg = new BigDecimal(avg);
				double avgCaseFormat = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				obj.setValue(avgCaseFormat);
			}
			data.add(obj);
		}
		map.put("resultList", data);
		result.add(map);
		return map;
	}

	/**
	 * 公用数据封装
	 * 
	 * @param AreaBase
	 *            所有的区域表
	 * @param AreaCases
	 *            区域对应的数据
	 * @return List<ReportAreaDTO>对象 包含区域名 区域内的统计总数 和平均数
	 */
	public static List<ReportAreaDTO> DateProcessing(List<AreaDTO> AreaBase, List<ReportAreaDTO> AreaCases) {
		List<ReportAreaDTO> resultList = new ArrayList<>();
		// 构建返回对象
		for (int ab = 0; ab < AreaBase.size(); ab++) {
			String areaName = AreaBase.get(ab).getName();
			Double areaArea = AreaBase.get(ab).getArea();
			ReportAreaDTO data = new ReportAreaDTO();
			data.setName(areaName);
			data.setCount("0");
			data.setValue(new Double(0.00));
			for (int ac = 0; ac < AreaCases.size(); ac++) {
				String areaCaseName = AreaCases.get(ac).getName();
				String areaCaseCount = AreaCases.get(ac).getCount();
				if (areaName.equals(areaCaseName)) {
					Double avgCase = (Double.valueOf(areaCaseCount) / areaArea) * 100;
					BigDecimal bg = new BigDecimal(avgCase);
					double avgCaseFormat = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					data.setCount(areaCaseCount);
					data.setValue(avgCaseFormat);
				}
			}
			resultList.add(data);
		}
		return resultList;
	}

}
