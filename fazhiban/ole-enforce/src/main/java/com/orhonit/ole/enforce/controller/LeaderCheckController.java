package com.orhonit.ole.enforce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.enforce.dto.CheckDailyDTO;
import com.orhonit.ole.enforce.entity.CheckDailyEntity;
import com.orhonit.ole.enforce.service.checkdaily.CheckDailyService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.UserService;
import com.orhonit.ole.sys.utils.UserUtil;

@RestController
@RequestMapping("/leaderCheck")
public class LeaderCheckController {
	@Autowired
	private CheckDailyService checkDailyService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value="/list")
	public TableResponse<CheckDailyDTO> listCase(TableRequest request) {
		return TableRequestHandler.<CheckDailyDTO> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				request.getParams().put("deptId", UserUtil.getCurrentUser().getDept_id());
				request.getParams().put("checkStatus",CommonParameters.CheckStatus.LEADER_APPROVE.toString());
				return checkDailyService.getCheckcount(request.getParams());
			}
		}).listHandler(new ListHandler<CheckDailyDTO>() {

			@Override
			public List<CheckDailyDTO> list(TableRequest request) {
				request.getParams().put("typeValue",CommonParameters.DictType.DAILY_CHECK_STATUS.toString());
				request.getParams().put("checkMode",CommonParameters.DictType.CHECK_MODE.toString());
				request.getParams().put("objectType",CommonParameters.DictType.PARTY_TYPE.toString());
				request.getParams().put("deptId", UserUtil.getCurrentUser().getDept_id());
				request.getParams().put("checkStatus",CommonParameters.CheckStatus.LEADER_APPROVE.toString());
				List<CheckDailyDTO> list = checkDailyService.getCheckList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}
	
	@GetMapping(value="/examineList")
	public TableResponse<CheckDailyDTO> examineList(TableRequest request) {
		return TableRequestHandler.<CheckDailyDTO> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				request.getParams().put("deptId", UserUtil.getCurrentUser().getDept_id());
				request.getParams().put("checkStatus",CommonParameters.CheckStatus.LEADER_VERIFY.toString());
				return checkDailyService.getCheckcount(request.getParams());
			}
		}).listHandler(new ListHandler<CheckDailyDTO>() {

			@Override
			public List<CheckDailyDTO> list(TableRequest request) {
				request.getParams().put("typeValue",CommonParameters.DictType.DAILY_CHECK_STATUS.toString());
				request.getParams().put("checkMode",CommonParameters.DictType.CHECK_MODE.toString());
				request.getParams().put("objectType",CommonParameters.DictType.PARTY_TYPE.toString());
				request.getParams().put("deptId", UserUtil.getCurrentUser().getDept_id());
				request.getParams().put("checkStatus",CommonParameters.CheckStatus.LEADER_VERIFY.toString());
				List<CheckDailyDTO> list = checkDailyService.getCheckList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}
	
	@GetMapping(value="/getCreatorId/{checkId}")
	public Result<Object> getCheckCreatorId(@PathVariable String checkId) {
		CheckDailyEntity entity = this.checkDailyService.findByCheckNum(checkId);
		User user = this.userService.getUserByPersonId(entity.getPersonId());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, user.getId());
	}
}
