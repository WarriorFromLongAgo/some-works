package com.orhonit.ole.tts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.tts.dao.LssuedDao;
import com.orhonit.ole.tts.entity.DeptPersonEntity;
import com.orhonit.ole.tts.entity.LssuedEntity;
import com.orhonit.ole.tts.service.checkQuery.CheckQueryService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 专项综合查询控制器
 * @author lizj
 *
 */
@RestController
@RequestMapping("/check")
@Slf4j
public class CheckQueryController {
	
	@Autowired
	private CheckQueryService checkqueryService;
	
	@Autowired
	private LssuedDao lssuedDao;
	
	@GetMapping(value="/list")
	@ApiOperation(value = "下达通知列表")
	public TableResponse<LssuedEntity> listCase(TableRequest request) {
		return TableRequestHandler.<LssuedEntity> builder().countHandler(new CountHandler() {
			@Override 
			public int count(TableRequest request) {
				return checkqueryService.getCasecount(request.getParams());
			}
		}).listHandler(new ListHandler<LssuedEntity>() {

			@Override
			public List<LssuedEntity> list(TableRequest request) {
				List<LssuedEntity> list = checkqueryService.getCaseList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}
	
	@RequestMapping("/find/{id}")
	@ApiOperation(value = "根据专项id获取执法人员")
	public DeptPersonEntity get(@PathVariable String id) {
		return lssuedDao.getByName(id);
	}
	
	
	
}
