package com.orhonit.ole.enforce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.enforce.dao.LssuedDao;
import com.orhonit.ole.enforce.entity.LssuedEntity;
import com.orhonit.ole.enforce.service.flow.FlowService;
import com.orhonit.ole.enforce.service.lssued.LssuedService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 专项
 * 案件受理控制器
 * @author lizj
 *
 */
@RestController
@RequestMapping("/acceptance")
@Slf4j
public class AcceptanceController {
	
	@Autowired
	private LssuedService lssuedService;
	
	@GetMapping(value="/list")
	@ApiOperation(value = "责令改正列表")
	public TableResponse<LssuedEntity> listCase(TableRequest request) {
		request.getParams().put("status", CommonParameters.CheckStatus.CASE_ADMISS);
		return TableRequestHandler.<LssuedEntity> builder().countHandler(new CountHandler() {
			@Override 
			public int count(TableRequest request) {
				return lssuedService.getCasecount(request.getParams());
			}
		}).listHandler(new ListHandler<LssuedEntity>() {

			@Override
			public List<LssuedEntity> list(TableRequest request) {
				List<LssuedEntity> list = lssuedService.getCaseList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}
}
