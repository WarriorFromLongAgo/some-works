package com.orhonit.ole.enforce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
import com.orhonit.ole.enforce.dto.CheckDailyDTO;
import com.orhonit.ole.enforce.dto.CheckDocDTO;
import com.orhonit.ole.enforce.dto.FlowTaskCommentDTO;
import com.orhonit.ole.enforce.entity.CheckDailyEntity;
import com.orhonit.ole.enforce.service.casedeal.CaseDealService;
import com.orhonit.ole.enforce.service.checkdaily.CheckDailyService;
import com.orhonit.ole.enforce.service.flow.FlowService;
import com.orhonit.ole.sys.dto.FlowDTO;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

@RestController
@RequestMapping("/checkDaily")
public class CheckDailyController {
	@Autowired
	private CheckDailyService checkDailyService;
	@Autowired
	private FlowService flowService;
	
	@Autowired
	private CaseDealService caseDealService;
	
	
	/**
	 * 日常检查暂存和提交
	 * @param checkDailyDTO
	 * @return
	 */
	@PostMapping("/save")
	public Result<Object> save(@RequestBody @Valid CheckDailyDTO checkDailyDTO) {
		CheckDailyEntity checkEntity = new CheckDailyEntity();
		//日常检查暂存
		if(checkDailyDTO.getStatus().equals(CommonParameters.CheckStatus.CHECK_ZC.toString())){
			BeanUtils.copyProperties(checkDailyDTO, checkEntity);
			User user  = UserUtil.getCurrentUser();
			checkEntity.setPersonId(user.getPerson_id());
			this.checkDailyService.save(checkEntity);
			 return ResultUtil.toResponseWithData(ResultCode.SUCCESS,checkEntity);
		}else{
			//日常检查提交
			BeanUtils.copyProperties(checkDailyDTO, checkEntity);
			FlowDTO flowDTO = this.checkDailyService.save(checkEntity);
		    if ( flowDTO != null ){
		    	flowDTO.setHandleMode(checkDailyDTO.getHandleMode());
		    	this.flowService.startFlowByKey(flowDTO);
				this.caseDealService.saveTaskEntity(flowDTO, true);
			}
		    return ResultUtil.toResponseWithData(ResultCode.SUCCESS,checkEntity);
		}
	
	}
	
	
	/**
	 * 提交日常检查
	 * @param flowDTO
	 * @return
	 */
	@PostMapping("/submit")
	public Result<Object> submit(@RequestBody FlowDTO flowDTO) {
		this.flowService.startFlowByKey(flowDTO);
		this.caseDealService.saveTaskEntity(flowDTO, true);
	    return ResultUtil.toResponse(ResultCode.SUCCESS);
	}
	

	
	
	/**
	 * 日常检查编辑页面数据回显
	 * @param id
	 * @return
	 */
	@GetMapping("/getCheck/{id}")
	@ResponseBody
	public Result<CheckDailyDTO> getInfoById(@PathVariable String id) {
		
		CheckDailyDTO checkDailyDTO = this.checkDailyService.findOneCheckDaily(id);
		
		if(Integer.valueOf(checkDailyDTO.getStatus())==CommonParameters.CheckStatus.CHECK_ZC){
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, checkDailyDTO);
		}
		return ResultUtil.toResponseWithData(ResultCode.ERROR,null);
	}
	

	
	/**
	 * 日常检查主页面
	 * @param request
	 * @return
	 */
	@GetMapping(value="/list")
	public TableResponse<CheckDailyDTO> listCheck(TableRequest request) {
		request.getParams().put("typeValue",CommonParameters.DictType.DAILY_CHECK_STATUS.toString());
		request.getParams().put("checkMode",CommonParameters.DictType.CHECK_MODE.toString());
		request.getParams().put("objectType",CommonParameters.DictType.PARTY_TYPE.toString());
		request.getParams().put("checkStatus",CommonParameters.CheckStatus.CHECK_ZC.toString());
		request.getParams().put("deptId", UserUtil.getCurrentUser().getDept_id());
		return TableRequestHandler.<CheckDailyDTO> builder().countHandler(new CountHandler() {
			
			public int count(TableRequest request) {
				return checkDailyService.getCheckcount(request.getParams());
			}
		}).listHandler(new ListHandler<CheckDailyDTO>() {
			public List<CheckDailyDTO> list(TableRequest request) {
				
				List<CheckDailyDTO> list = checkDailyService.getCheckList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}
	
	/**
	 * 日常检查详情
	 * @param checkId
	 * @return
	 */
	@GetMapping("/query/{checkId}")
	public Result<CheckDailyDTO> queryByCheckId(@PathVariable String checkId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", CommonParameters.DictType.DAILY_CHECK_STATUS.toString());
		params.put("checkObjectType", CommonParameters.DictType.PARTY_TYPE.toString());
		CheckDailyDTO dto = this.checkDailyService.queryCheckByCheckId(checkId,params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, dto);
	}
	
	@GetMapping("/query/docContent/{checkId}")
	public Result<List<CheckDocDTO>> queryDocContentByCheckId(@PathVariable String checkId) {
		List<CheckDocDTO> checkDocDTOs = this.checkDailyService.queryDocContentByCheckId(checkId);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, checkDocDTOs);
	}
	
	/**
	 * 获取审批记录
	 * @param id
	 * @return
	 */
	@GetMapping("/flow/comment/{businessId}/{key}")
	public Result<List<FlowTaskCommentDTO>> getFlowCommentByBusiAndKey(@PathVariable String businessId, @PathVariable String key) {
		String pid = this.flowService.getProcessInstanceIdByKeyAndBusinessId(key, businessId);
		List<FlowTaskCommentDTO> comments = this.flowService.getCommemntByProcessInstanceId(pid);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, comments);
	}
	
	/**
	 * 获取日常检查的当事人信息
	 * @param id
	 * @return
	 */
	@GetMapping("/getPartyInfo/{checkId}")
	public Result<CheckDailyEntity> getPartyInfo(@PathVariable String checkId) {
		CheckDailyEntity checkDailyEntity = this.checkDailyService.getPartyInfo(checkId);
		if(checkDailyEntity == null) {
			return ResultUtil.toResponse(ResultCode.ERROR);
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, checkDailyEntity);
	}
}
