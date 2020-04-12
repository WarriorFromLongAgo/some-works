package com.orhonit.ole.sys.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.sys.dao.InterfaceLogsDao;
import com.orhonit.ole.sys.model.InterfaceLogs;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/interfaceLogs")
public class InterfaceLogsController {
	@Autowired
	private InterfaceLogsDao interfaceLogsDao;
	
	@GetMapping
	@ApiOperation(value = "接口日志列表")
	public TableResponse<InterfaceLogs> list(TableRequest request) {
		return TableRequestHandler.<InterfaceLogs> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return interfaceLogsDao.InterfaceLogcount(request.getParams());
			}
		}).listHandler(new ListHandler<InterfaceLogs>() {

			@Override
			public List<InterfaceLogs> list(TableRequest request) {
				List<InterfaceLogs> list = interfaceLogsDao.InterfaceLoglist(request.getParams(), request.getStart(), request.getLength());
				for(int i = 0;i<list.size();i++){	
					String start = stampToDate(list.get(i).getStartTime());
					list.get(i).setStartTime(start);
					
					String end = stampToDate(list.get(i).getEndTime());
					list.get(i).setEndTime(end);
					
					String execTime = Double.valueOf(list.get(i).getExecTime())/1000+ "秒";
					list.get(i).setExecTime(execTime);
				}
				return list;
			}
		}).build().handle(request);
	}
	
	 public static String stampToDate(String s){
	        String res;
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        long lt = new Long(s);
	        Date date = new Date(lt);
	        res = simpleDateFormat.format(date);
	        return res;
	    }
	 
	@GetMapping("/query/{logId}")
	public Result<Object> queryByLogId(@PathVariable String logId) {
		 Map<String, Object> resultMap = new HashMap<>();
		 InterfaceLogs interfaceLogs = interfaceLogsDao.InterfaceLogInfo(logId);
		 
		 
		 if(interfaceLogs!=null){

			 String start = stampToDate(interfaceLogs.getStartTime());
			 String end = stampToDate(interfaceLogs.getEndTime());
			 String execTime = Double.valueOf(interfaceLogs.getExecTime())/1000+ "秒";
			 
			 interfaceLogs.setStartTime(start);
			 interfaceLogs.setEndTime(end);
			 interfaceLogs.setExecTime(execTime);
			 
			 resultMap.put("interfaceLogs", interfaceLogs);
			 return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);
		 }else{
			 resultMap.put("interfaceLogs", "无数据！");
			 return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);
		 }
	   }
}
