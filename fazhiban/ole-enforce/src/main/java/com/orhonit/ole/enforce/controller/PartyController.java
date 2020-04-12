package com.orhonit.ole.enforce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.service.party.PartyLoginService;

/**
 * 当事人控制器
 */
@RestController
@RequestMapping("/party")
public class PartyController {
	
	@Autowired
	private PartyLoginService partyLoginService;
	
	/**
	 * 根据caseId创建当事人公示平台登录账号
	 * @param caseId
	 * @return
	 */
	@PostMapping(value="/createPartyUser")
	public Result<String> createPartyUser(@RequestParam String caseId,@RequestParam String hearingDate) {
		if(this.partyLoginService.createPartyLogin(caseId,hearingDate)){
			return ResultUtil.success();
		}else{
			return ResultUtil.toResponseWithData(ResultCode.ERROR,null);
		}
	}
}
