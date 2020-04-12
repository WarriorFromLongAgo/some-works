package com.orhonit.ole.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.report.dto.ReviewResultDTO;
import com.orhonit.ole.report.dto.cases.CaseHeadChartDTO;
import com.orhonit.ole.report.dto.cases.CaseHeadDTO;
import com.orhonit.ole.report.service.cases.ReportCaseHeadService;
import com.orhonit.ole.report.service.cases.ReportCaseHotMapService;
import com.orhonit.ole.report.service.dept.ReportDeptService;
import com.orhonit.ole.report.service.review.ReportReviewService;
import com.orhonit.ole.sys.dto.ReviewRecordDTO;
import com.orhonit.ole.sys.model.AreaEntity;
import com.orhonit.ole.sys.model.DeptDTO;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.repository.AreaRepository;
import com.orhonit.ole.sys.service.SysDeptService;
import com.orhonit.ole.sys.utils.UserUtil;

/**
 * 评查结果统计
 */
@RestController
@RequestMapping("/reportreview")
public class ReportReviewController {

	@Autowired
	private ReportReviewService reviewService;
	
	@Autowired
	SysDeptService sysDeptService;
	
	@Autowired
	AreaRepository areaRepository;


	@GetMapping("/bar")
	public Object caseheadBar() {
		Map<String,Object> params=new HashMap<>();
		User user =UserUtil.getCurrentUser();
		if (!user.isAdmin()) {
			DeptDTO deptDTO = this.sysDeptService.findDeptById(user.getDept_id());
			if (CommonParameters.LawType.JD.equals(deptDTO.getLawType()) && CommonParameters.DeptProperty.XZJG.equals(deptDTO.getDeptProperty())) {
				// 如果是市本级的法制办则查询所有
				AreaEntity areaEntity = areaRepository.findOneById(Integer.parseInt(user.getArea_id()));
				if (CommonParameters.AreaLevel.DJ.equals(areaEntity.getLevel())) {
					
				} else {
					params.put("areaId", user.getArea_id());
				}
			}else{
				params.put("deptId",user.getDept_id());
			}
		}
		
		Map<String, Object> result = new HashMap<>();
		Map<String,Object> deptName= new HashMap<>();

		String[] lenged = { "优秀", "合格", "一般" };
		List<ReviewResultDTO> yearList = reviewService.getReviewResultByScore(params);
		// 构建年份数据表
		List<CaseHeadChartDTO> BarData = new ArrayList<>();
		CaseHeadChartDTO BarChart;
		for (ReviewResultDTO review:yearList) {
			if(!deptName.containsKey(review.getName())){
				deptName.put(review.getName(), review);
				BarChart = new CaseHeadChartDTO(review.getName(),"0","0","0");
			}else{
				BarChart=(CaseHeadChartDTO) deptName.get(review.getName());
			}
			if(review.getType().equals("yx")){
				BarChart.setValue1(review.getNum());
			}else if(review.getType().equals("hg")){
				BarChart.setValue2(review.getNum());
			}else if(review.getType().equals("yb")){
				BarChart.setValue3(review.getNum());
			}
			BarData.add(BarChart);
		}
		result.put("BarData", BarData);
		result.put("lenged", lenged);
		return result;
	}
}
