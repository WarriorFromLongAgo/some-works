package com.orhonit.ole.tts.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.tts.entity.ScheduleJobLogEntity;
import com.orhonit.ole.tts.service.log.ScheduleJobLogService;

/**
 * 定时任务日志
 */
@RestController
@RequestMapping("/taskLog")
public class ScheduleJobLogController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;
	
	/**
	 * 定时任务日志列表
	 */
	@RequestMapping("/list")
	public TableResponse<ScheduleJobLogEntity> list(TableRequest request){
		//@RequestParam Map<String, Object> params
		return TableRequestHandler.<ScheduleJobLogEntity> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return scheduleJobLogService.queryTotal(request.getParams());
			}
		}).listHandler(new ListHandler<ScheduleJobLogEntity>() {

			@Override
			public List<ScheduleJobLogEntity> list(TableRequest request) {
				List<ScheduleJobLogEntity> list = scheduleJobLogService.queryList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
		//查询列表数据
		//List<ScheduleJobLogEntity> jobList = scheduleJobLogService.queryList(params);
		
		//return ResultUtil.toResponseWithData(ResultCode.SUCCESS, jobList);
	}
	
	/**
	 * 定时任务日志信息
	 */
	@RequestMapping("/info/{logId}")
	public Result<Object> info(@PathVariable("logId") Long logId){
		ScheduleJobLogEntity log = scheduleJobLogService.queryObject(logId);
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, log);
	}
}
