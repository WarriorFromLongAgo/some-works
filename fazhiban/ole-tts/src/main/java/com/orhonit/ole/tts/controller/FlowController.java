package com.orhonit.ole.tts.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;
import com.orhonit.ole.tts.dto.FlowDTO;
import com.orhonit.ole.tts.dto.NextOpinionDTO;
import com.orhonit.ole.tts.entity.PartyInfoEntity;
import com.orhonit.ole.tts.service.casedeal.CaseDealService;
import com.orhonit.ole.tts.service.flow.FlowService;
import com.orhonit.ole.tts.service.perverify.PerVerifyService;
import com.orhonit.ole.tts.utils.EnforceException;

@RequestMapping("/flow")
@RestController
public class FlowController {
	
	@Autowired
	private FlowService flowService;
	
	@Autowired
	private PerVerifyService perVerifyService;
	
	@Autowired
	private CaseDealService caseDealService;
	
	
	
	@PostMapping("/taskComplate")
	public Result<Object> startFlow(@RequestBody FlowDTO flowDTO) {
		this.caseDealService.saveTaskEntity(flowDTO, false);
		this.flowService.taskComplete(flowDTO);
		return ResultUtil.toResponse(ResultCode.SUCCESS);
	}
	
	/**
	 * 初步核实的流程提交
	 * @param flowDTO
	 * @return
	 */
	@PostMapping("/taskComplatePer")
	public Result<Object> perFlow(@RequestBody FlowDTO flowDTO) {
		if(perVerifyService.haveParty(flowDTO.getBusinessId())){
			this.caseDealService.saveTaskEntity(flowDTO, false);
			this.flowService.taskComplete(flowDTO);
			return ResultUtil.toResponse(ResultCode.SUCCESS);
		}
		return ResultUtil.toResponse(ResultCode.ERROR);
	}
	
	/**
	 * 获取当前登录人所在部门的人员列表
	 * @return
	 */
	@GetMapping("/getPersonByDeptId")
	public Result<List<NextOpinionDTO>> getPersonByDeptId() {
		User user = UserUtil.getCurrentUser();
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getPersonByDeptId(user.getDept_id());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}
	
	/**
	 * 根据部门Id获取部门的人员列表
	 * @return
	 */
	@GetMapping("/getPerson")
	public List<Map<String, String>> getPersonByDeptId(@RequestParam String deptId) {
		
//		List<Dept> deptAll = permissionDao.deptAll(id);
//        List<Map<String, Object>> listMap = new ArrayList<>();
//        Map<String, Object> map;
//        for (Dept per : deptAll) {
//            map = new HashMap<>();
//            map.put("id", per.getId());
//            map.put("parent_id", per.getParent_id());
//            map.put("text", per.getName());
//            map.put("refId", per.getArea_id());
//            listMap.add(map);
//        }
//        List<Map<String, Object>> retMap = AppUtil.list2Tree(listMap, "parent_id", "id", id);
		System.out.println(deptId);
        return flowService.getPerson(deptId);
	}
	
	/**
	 * 获取当前登录人所在部门的并且有审核权限的人员列表
	 * @return
	 */
	@GetMapping("/getHaveRoleOpinionByDeptId")
	public Result<List<NextOpinionDTO>> getHaveRoleOpinionByDeptId() {
		User user = UserUtil.getCurrentUser();
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getHaveRoleOpinionByDeptId(user.getDept_id());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}
	
	/**
	 * 获取当前登录人所在部门的并且有审批权限的人员列表
	 * @return
	 */
	@GetMapping("/getApproveUserByDeptId")
	public Result<List<NextOpinionDTO>> getApproveUserByDeptId() {
		User user = UserUtil.getCurrentUser();
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getApproveUserByDeptId(user.getDept_id());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}
	
	/**
	 * 获取当前登录人所在部门的并且有法制受理权限的人员列表
	 * @return
	 */
	@GetMapping("/getFzHaveRoleCaseByDeptId")
	public Result<List<NextOpinionDTO>> getFzHaveRoleCaseByDeptId() {
		User user = UserUtil.getCurrentUser();
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getFzHaveRoleCaseByDeptId(user.getDept_id());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}
	
	/**
	 * 获取当前登录人所在部门的并且有法制审核权限的人员列表
	 * @return
	 */
	@GetMapping("/getFzHaveRoleOpinionByDeptId")
	public Result<List<NextOpinionDTO>> getFzHaveRoleOpinionByDeptId() {
		User user = UserUtil.getCurrentUser();
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getFzHaveRoleOpinionByDeptId(user.getDept_id());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}
	
	/**
	 * 获取当前登录人所在部门的并且有法制审批权限的人员列表
	 * @return
	 */
	@GetMapping("/getFzApproveUserByDeptId")
	public Result<List<NextOpinionDTO>> getFzApproveUserByDeptId() {
		User user = UserUtil.getCurrentUser();
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getFzApproveUserByDeptId(user.getDept_id());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}
	
	/**
	 * 获取当前登录人所在部门的并且有复议核查权限的人员列表
	 * @return
	 */
	@GetMapping("/getFyReviewVerificationByDeptId")
	public Result<List<NextOpinionDTO>> getFyReviewVerificationByDeptId() {
		User user = UserUtil.getCurrentUser();
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getFyReviewVerificationByDeptId(user.getDept_id());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}
	
	/**
	 * 获取当前登录人部门所在区域的法制办复议承办人的人员列表
	 * @return
	 */
	@GetMapping("/getFyContractorByDeptIdArea")
	public Result<List<NextOpinionDTO>> getFyContractorByDeptIdArea() {
		User user = UserUtil.getCurrentUser();
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getFyContractorByDeptIdArea(user.getDept_id());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}
	
	/**
	 * 获取当前登录人部门所在区域的法制办复议科长人员列表
	 * @return
	 */
	@GetMapping("/getFySectionChiefByDeptIdArea")
	public Result<List<NextOpinionDTO>> getFySectionChiefByDeptIdArea() {
		User user = UserUtil.getCurrentUser();
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getFySectionChiefByDeptIdArea(user.getDept_id());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}
	
	/**
	 * 获取当前登录人部门所在区域的法制办复议主任的人员列表
	 * @return
	 */
	@GetMapping("/getFyDirectorByDeptIdArea")
	public Result<List<NextOpinionDTO>> getFyDirectorByDeptIdArea() {
		User user = UserUtil.getCurrentUser();
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getFyDirectorByDeptIdArea(user.getDept_id());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}
	
	/**
	 * 获取当前登录人所在部门的并且有复议审理权限的人员列表
	 * @return
	 */
	@GetMapping("/getFyReconsiderationByDeptId")
	public Result<List<NextOpinionDTO>> getFyReconsiderationByDeptId() {
		User user = UserUtil.getCurrentUser();
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getFyReconsiderationByDeptId(user.getDept_id());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}
	
	/**
	 * 获取当前登录人所在部门的并且有听证受理的人员列表
	 * @return
	 */
	@GetMapping("/getTzslReconsiderationByDeptId")
	public Result<List<NextOpinionDTO>> getTzslReconsiderationByDeptId() {
		User user = UserUtil.getCurrentUser();
		List<NextOpinionDTO> nextOpinionDTOs = this.flowService.getTzslReconsiderationByDeptId(user.getDept_id());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}
	
	/**
	 * 获取当前案件的当事人
	 * @return
	 */
	@GetMapping("/getPartyByCaseId")
	public Result<PartyInfoEntity> getPartyByCaseId(@RequestParam String caseId) {
		PartyInfoEntity partyInfoEntity = this.flowService.getPartyByCaseId(caseId);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, partyInfoEntity);
	}
	
	@GetMapping("/flow/image/{deployId}/{imageName}")
	public void getFlowImage(@PathVariable String deployId, @PathVariable String imageName, HttpServletResponse resp) {
		imageName = imageName + ".png";
		InputStream in = this.flowService.getResourceStream(deployId, imageName);
		try {
			OutputStream out = resp.getOutputStream();
			for(int b=-1;(b=in.read())!=-1;){
				out.write(b);
			}
			out.close();
			in.close();
		}catch(Exception e ) {
			e.printStackTrace();
			throw new EnforceException(ResultCode.BUSI_ERROR);
		}
	}
	
	/**
	 * 获取当前流程节点的流程图
	 * @return
	 */
	@GetMapping("/view/current/{taskId}")
	public Result<Map<String, Object>> viewCurrentFlow(@PathVariable String taskId) {
		ProcessDefinition processDefinition = this.flowService.getProcessDefinitionByTaskId(taskId);
		Map<String, Object> map = new HashMap<>();
		map.put("deploymentId", processDefinition.getDeploymentId());
		map.put("imageName", processDefinition.getDiagramResourceName());
		Map<String, Object> imageCoordinate = this.flowService.getCoordinateByTaskId(taskId);
		map.put("imageCoordinate", imageCoordinate);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, map);
	}
	
	
	/**
	 * 获取当前流程节点的流程图,带线
	 * @return
	 */
	@GetMapping("/view/currentFlow/{taskId}/{key}/{businessId}")
	public void viewCurrentFlowAndFlowSeq(@PathVariable String taskId,@PathVariable String key,@PathVariable String businessId, HttpServletResponse response) {
		
		try {
			InputStream in = this.flowService.getCoordinateAndFlowSeqByTaskId(taskId,key,businessId);
			
			OutputStream out = response.getOutputStream();
			
			FileCopyUtils.copy(in, out);
			
//			for(int b = -1 ; ( b = is.read() ) != -1 ; ){
//				out.write(b);
//			}
			out.close();
			in.close();
			
		}catch(Exception e ) {
			e.printStackTrace();
			throw new EnforceException(ResultCode.BUSI_ERROR);
		}
		
	}
}
