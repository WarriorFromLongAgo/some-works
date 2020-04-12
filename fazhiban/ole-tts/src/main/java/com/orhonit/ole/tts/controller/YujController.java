package com.orhonit.ole.tts.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.tts.dto.YujDTO;
import com.orhonit.ole.tts.service.yuj.YujService;

import lombok.extern.slf4j.Slf4j;


/**
 * 预警列表
 */
@RestController
@RequestMapping("/yuj")
@Slf4j
public class YujController {

    @Autowired
    private YujService yujService;
    /**
     * 查询某个案件的预警信息
     * @param caseId
     * @return
     */
    @GetMapping("/warnList")
	public Result<Object> list(@RequestParam(value = "caseId" , required = false) String caseId ){
		List<YujDTO> YujDTO = this.yujService.getWarnList(caseId);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS,YujDTO);
	}
}
