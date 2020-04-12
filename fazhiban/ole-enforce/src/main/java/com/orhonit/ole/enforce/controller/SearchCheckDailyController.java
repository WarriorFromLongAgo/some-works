package com.orhonit.ole.enforce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.enforce.dto.CheckDailyDTO;
import com.orhonit.ole.enforce.service.checkdaily.CheckDailyService;
import com.orhonit.ole.sys.utils.UserUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/searchCheckDaily")
@Slf4j
public class SearchCheckDailyController {
	@Autowired
	private CheckDailyService checkDailyService;
	
	@GetMapping(value="/search")
	public TableResponse<CheckDailyDTO> listCheck(TableRequest request) {
		request.getParams().put("typeValue",CommonParameters.DictType.DAILY_CHECK_STATUS.toString());
		request.getParams().put("checkMode",CommonParameters.DictType.CHECK_MODE.toString());
		request.getParams().put("objectType",CommonParameters.DictType.PARTY_TYPE.toString());
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
}
