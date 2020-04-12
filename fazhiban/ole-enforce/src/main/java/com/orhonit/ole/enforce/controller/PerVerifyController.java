package com.orhonit.ole.enforce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
import com.orhonit.ole.enforce.dto.CaseDetailDTO;
import com.orhonit.ole.enforce.dto.CaseListDTO;
import com.orhonit.ole.enforce.dto.PerVerifyInfoDTO;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;
import com.orhonit.ole.enforce.service.perverify.PerVerifyService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

/**
 * 初步核实控制器
 * @author ebusu
 *
 */
@RestController
@RequestMapping("/perVerify")
public class PerVerifyController {
	
	@Autowired
	private PerVerifyService perVerifyService;
	
	@Autowired
	private CaseService caseService;
	
	/**
	 * 初步核实列表
	 * @param request
	 * @return
	 */
	@GetMapping(value="/list")
	public TableResponse<CaseListDTO> listCase(TableRequest request) {
		
		request.getParams().put("typeValue", "1002");
		request.getParams().put("caseStatus", CommonParameters.CaseStatus.CBHS);
		User user = UserUtil.getCurrentUser();
		request.getParams().put("deptId", user.getDept_id());
		//request.getParams().put("zzfryId", user.getPerson_id());
		request.getParams().put("userId", user.getUsername());
		return TableRequestHandler.<CaseListDTO> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return perVerifyService.getCasecount(request.getParams());
			}
		}).listHandler(new ListHandler<CaseListDTO>() {

			@Override
			public List<CaseListDTO> list(TableRequest request) {
				
				List<CaseListDTO> list = perVerifyService.getCaseList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}
	
	
	@GetMapping(value="/getVerifyByCaseNum/{caseNum}")
	public Result<Object> getVerifyByCaseNum(@PathVariable String caseNum) {
		Boolean partyTag = this.perVerifyService.haveParty(caseNum);
		Map<String,Object> data = new HashMap<>();
		if ( partyTag ) {
			data.put("hasParty", "1");
		} else {
			data.put("hasParty", "0");
		}
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, data);
	}
	
	/**
	 * 保存初步核实数据
	 * @return
	 */
	@PostMapping("/save")
	public Result<String> savePerVerify(@RequestBody @Valid PerVerifyInfoDTO perVerifyInfoDTO) {
		this.perVerifyService.savePerVerify(perVerifyInfoDTO);
		return null;
	}
	
	/**
	 * 获取案件详细信息
	 * @param caseId
	 * @return
	 */
	@GetMapping("/query/{caseId}")
	public Result<CaseDetailDTO> getCaseDetailByCaseId(@PathVariable String caseId) {
		Map<String,Object> params=new HashMap<>();
		params.put("caseId", caseId);
		params.put("typeCaseSource", CommonParameters.DictType.CASE_RES);
		CaseDetailDTO caseDetailDTO = this.caseService.findCaseDetail(params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, caseDetailDTO);
	}
}
