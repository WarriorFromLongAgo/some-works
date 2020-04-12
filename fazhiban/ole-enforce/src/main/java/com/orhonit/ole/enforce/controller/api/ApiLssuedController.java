package com.orhonit.ole.enforce.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.entity.CheckRecordEntity;
import com.orhonit.ole.enforce.repository.CheckRecordRepository;

/*
 * app专项检查相关接口
 */
@RestController
@RequestMapping("/api/lssued")
public class ApiLssuedController {
	
	@Autowired
	private CheckRecordRepository checkRecordRepository;
	
	
	/**
	 * 获取专项检查当事人
	 * @param id
	 * @return
	 */
	@GetMapping("/getCheckParty/{checkId}")
	public Result<Object>  getCheckParty(@PathVariable String checkId) {
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, checkRecordRepository.findOneByCheckId(checkId));
	}

}
