package com.orhonit.ole.sys.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.sys.dao.SysLogsDao;
import com.orhonit.ole.sys.model.SysLogs;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/logs")
public class SysLogsController {

	@Autowired
	private SysLogsDao sysLogsDao;

	@GetMapping
	@RequiresPermissions(value = "sys:log:query")
	@ApiOperation(value = "日志列表")
	public TableResponse<SysLogs> list(TableRequest request) {
		return TableRequestHandler.<SysLogs> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return sysLogsDao.count(request.getParams());
			}
		}).listHandler(new ListHandler<SysLogs>() {

			@Override
			public List<SysLogs> list(TableRequest request) {
				return sysLogsDao.list(request.getParams(), request.getStart(), request.getLength());
			}
		}).build().handle(request);
	}

}
