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
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 专项
 * 案件受理控制器
 * @author lizj
 *
 */
@RestController
@RequestMapping("/toexamine")
@Slf4j
public class ToexamineController {
	
	@Autowired
	private FlowService flowService;
	
	@GetMapping(value="/list")
	@ApiOperation(value = "审批列表")
	public TableResponse<LssuedEntity> listCase(TableRequest request) {
		User user = UserUtil.getCurrentUser();
		request.getParams().put("userId", user.getId());
		request.getParams().put("status","专项领导审批");
		return TableRequestHandler.<LssuedEntity> builder().countHandler(new CountHandler() {
			@Override 
			public int count(TableRequest request) {
				System.out.println(request.getParams().toString());
				return flowService.getPcCheckcount(request.getParams());
			}
		}).listHandler(new ListHandler<LssuedEntity>() {

			@Override
			public List<LssuedEntity> list(TableRequest request) {
				List<LssuedEntity> list = flowService.getPcCheckTask(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}
}
