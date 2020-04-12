package com.orhonit.ole.enforce.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dto.CheckDailyDTO;
import com.orhonit.ole.enforce.dto.LssuedDTO;
import com.orhonit.ole.enforce.entity.CheckDailyEntity;
import com.orhonit.ole.enforce.service.checkdaily.CheckDailyService;
import com.orhonit.ole.enforce.service.lssued.LssuedService;

/*
 * app专项检查相关接口
 */
@RestController
@RequestMapping("/api/checkDaily")
public class ApiCheckDailyController {
	
	@Autowired
	private CheckDailyService checkDailyService;
	
	
	@Autowired
	private LssuedService lssuedService;
	
	/**
	 * 获取日常检查的当事人信息
	 * @param id
	 * @return
	 */
	@GetMapping("/getPartyInfo/{checkId}")
	public Result<Object>  getPartyInfo(@PathVariable String checkId) {
		CheckDailyEntity checkDailyEntity = this.checkDailyService.getPartyInfo(checkId);
		if(checkDailyEntity == null) {
			return ResultUtil.toResponse(ResultCode.ERROR);
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, checkDailyEntity);
	}
	
	/**
	 * 选择安源为“日常”或者“专项检查”时获取相对应的单据
	 */
	@GetMapping("/getCheckIdsByType/{checkType}")
	public Result<Object> getCheckIdsByType(@PathVariable String checkType) {
		if( checkType.equals(CommonParameters.CaseSourceCheckType.PRO)) {
			//专项检查
			List<LssuedDTO> lssuedDTOs = this.lssuedService.getCaseSourceCheck();
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS,lssuedDTOs);
		} else if (checkType.equals(CommonParameters.CaseSourceCheckType.DAILY)) {
			// 日常检查
			List<CheckDailyDTO> checkDailyDTOs = this.checkDailyService.getCaseSourceCheck();
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS,checkDailyDTOs);
		} else {
			return ResultUtil.toResponseWithData(ResultCode.BUSI_ERROR,"参数错误.");
		}
	}

}
