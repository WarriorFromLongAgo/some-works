package com.orhonit.ole.enforce.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dto.CaseListDTO;
import com.orhonit.ole.enforce.dto.FlowDealDTO;
import com.orhonit.ole.enforce.entity.PartyInfoEntity;
import com.orhonit.ole.enforce.entity.PartyLoginEntity;
import com.orhonit.ole.enforce.entity.SmsEntity;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;
import com.orhonit.ole.enforce.service.flow.FlowService;
import com.orhonit.ole.enforce.service.party.PartyLoginService;
import com.orhonit.ole.enforce.service.sms.SmsService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 先行告知控制器
 * @author lizj
 *
 */
@RestController
@RequestMapping("/informing")
@Slf4j
public class InformingController {
	
	@Autowired
	private CaseService caseService;
	
	@Autowired
	private FlowService flowService;
	
	@Autowired
	private PartyLoginService partyLoginService; 
	
	@Autowired
	private SmsService smsService; 
	
	/**
	 * 先行告知列表
	 * @param request
	 * @return
	 */
	@GetMapping(value="/list")
	public TableResponse<CaseListDTO> listCase(TableRequest request) {
		request.getParams().put("typeValue", "1002");
		request.getParams().put("caseStatus", CommonParameters.CaseStatus.XXGZ);
		User user = UserUtil.getCurrentUser();
		//request.getParams().put("zzfryId", user.getPerson_id());
		return TableRequestHandler.<CaseListDTO> builder().countHandler(new CountHandler() {

			@Override 
			public int count(TableRequest request) {
				System.out.println(request.getParams().toString());
				return caseService.getCasecount(request.getParams());
			}
		}).listHandler(new ListHandler<CaseListDTO>() {

			@Override
			public List<CaseListDTO> list(TableRequest request) {
				List<CaseListDTO> list = caseService.getCaseList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}
	
	/**
	 * 先行告知
	 */
	@PostMapping(value="/update")
	public Result<String> caseClosed(@RequestBody FlowDealDTO flowDealDTO) {
		this.caseService.caseClosed(flowDealDTO);
		return ResultUtil.success();
	}
	
}