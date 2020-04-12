package com.orhonit.ole.tts.controller;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.tts.dto.TaskDTO;
import com.orhonit.ole.tts.service.caseinfo.TaskService;
import com.orhonit.ole.tts.task.CaseDealTask;
import com.orhonit.ole.tts.task.TestTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/task")
public class TaskController {
	
	@Autowired
	private TaskService taskService;

	/**
	 * 获取案件列表
	 * @param request
	 * @return
	 */
	@GetMapping(value="/list")
	public TableResponse<TaskDTO> listCase(TableRequest request) {
		
		return TableRequestHandler.<TaskDTO> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				request.getParams().put("typeValue", "1002");
				System.out.println(request.getParams().toString());
				return taskService.getTaskCount(request.getParams());
			}
		}).listHandler(new ListHandler<TaskDTO>() {

			@Override
			public List<TaskDTO> list(TableRequest request) {
				List<TaskDTO> list = taskService.getTaskList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}
	
	@PostMapping("/save")
	public Result<Object> saveTask(@RequestBody TaskDTO taskDTO) {
		this.taskService.save(taskDTO);
		
		return ResultUtil.success();
	}
	
	@GetMapping("/getTask")
	public Result<Object> getTask(@RequestParam(value="jobId") Integer jobId) {
		TaskDTO taskDTO = taskService.queryObject(jobId);
		if(taskDTO!=null){
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, taskDTO);	
		}
		return ResultUtil.toResponseWithData(ResultCode.ERROR, null);	
	}
	
	@PostMapping("/delete")
	public Result<Object> deleteTask(@RequestBody Long[] jobIds) {
		taskService.delete(jobIds);
		return ResultUtil.success();	
	}
	
	@PostMapping("/update")
	public Result<Object> updateTask(@RequestBody TaskDTO taskDTO) {
		TaskDTO taskDTOo = taskService.queryObject(taskDTO.getJobId());
		
		taskDTOo.setBeanName(taskDTO.getBeanName());
		taskDTOo.setCronExpression(taskDTO.getCronExpression());
		taskDTOo.setMethodName(taskDTO.getMethodName());
		taskDTOo.setParams(taskDTO.getParams());
		taskDTOo.setRemark(taskDTO.getRemark());
		taskService.update(taskDTOo);
		return ResultUtil.success();	
	}
	
	@GetMapping("/run")
	public Result<Object> run(@RequestParam(value="jobId") Integer jobId) {
		this.taskService.run(jobId);
		return ResultUtil.success();
	}
	
	/**
	 * 暂停定时任务
	 */
	@PostMapping("/pause")
	public Result<Object> pause(@RequestBody Long[] jobIds){
		this.taskService.pause(jobIds);
		return ResultUtil.success();
	}
	
	/**
	 * 恢复定时任务
	 */
	@PostMapping("/resume")
	public Result<Object> resume(@RequestBody Long[] jobIds){
		this.taskService.resume(jobIds);
		
		return ResultUtil.success();
	}

	public TaskDTO getTaskByJobId(Integer jobId){
	    return taskService.getTaskByJobId(jobId);
    }
}
