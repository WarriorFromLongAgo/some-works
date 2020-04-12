package com.orhonit.ole.tts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.tts.dto.TaskDTO;
import com.orhonit.ole.tts.service.flow.FlowService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

/**
 * 任务控制器
 * @author ebusu
 *
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {
	
	@Autowired
	private FlowService flowService;

	@GetMapping("/list/{currentPage}")
	public Result<Object> getUserTask(@PathVariable Integer currentPage) {
		User user = UserUtil.getCurrentUser();
		
		Integer pageSize = 10;
		
		currentPage *= pageSize;
		
		List<TaskDTO> taskDTOs = this.flowService.getUserTask(user.getId().toString(), currentPage, pageSize);
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, taskDTOs);
	}
	
	@GetMapping("/listCount")
	public Result<Object> getUserTaskListCount() {
		User user = UserUtil.getCurrentUser();
		
		List<TaskDTO> taskDTOs = this.flowService.getUserTask(user.getId().toString(), null, null);
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, taskDTOs.size());
	}
	
	/**
	 * 根据caseNum获取案件当前的taskId
	 * @param caseNum
	 * @return
	 */
	@GetMapping("/getTaskId/{caseNum}")
	public Result<Object> getTaskId(@PathVariable String caseNum) {
		System.out.println("caseNum："+caseNum);
		String taskId = flowService.getTaskIdByCaseNum(caseNum);
		if(taskId == null||taskId.equals("")){
			return ResultUtil.toResponseWithData(ResultCode.ERROR, null);
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, taskId);
	}
}
