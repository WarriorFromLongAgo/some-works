package com.orhonit.ole.enforce.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.controller.api.ApiMessagePushController;
import com.orhonit.ole.enforce.dto.NextOpinionDTO;
import com.orhonit.ole.enforce.entity.CaseEntity;
import com.orhonit.ole.enforce.entity.PartyInfoEntity;
import com.orhonit.ole.enforce.exception.EnforceException;
import com.orhonit.ole.enforce.repository.CaseRepository;
import com.orhonit.ole.enforce.service.casedeal.CaseDealService;
import com.orhonit.ole.enforce.service.flow.FlowService;
import com.orhonit.ole.enforce.service.perverify.PerVerifyService;
import com.orhonit.ole.sys.dto.FlowDTO;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.UserService;
import com.orhonit.ole.sys.utils.UserUtil;

@RequestMapping("/flow")
@RestController
public class FlowController {

	@Autowired
	private FlowService flowService;

	@Autowired
	private PerVerifyService perVerifyService;

	@Autowired
	private CaseDealService caseDealService;

	@Autowired
	private CaseRepository caseRepository;

	@Autowired
	private UserService userService;

	@PostMapping("/taskComplate")
	public Result<Object> startFlow(@RequestBody FlowDTO flowDTO) {
		String msg = null;
		String type = null;
		if (flowDTO.getBusinessId().contains("CF")) {
			msg = CommonParameters.PushMessage.AUDIT;
			type = CommonParameters.PushMessage.CASE_TYPE_1;
		} else if (flowDTO.getBusinessId().contains("RC")) {
			msg = CommonParameters.PushMessage.DAILY_AUDIT;
			type = CommonParameters.PushMessage.CASE_TYPE_5;
		} else if (flowDTO.getBusinessId().contains("ZX")) {
			msg = CommonParameters.PushMessage.SPECIAL_AUDIT;
			type = CommonParameters.PushMessage.CASE_TYPE_6;
		}

		this.caseDealService.saveTaskEntity(flowDTO, false);
		this.flowService.taskComplete(flowDTO);
		User userInfo = this.userService.getUserById(Long.valueOf(flowDTO.getNextAssignee()));
		if (!StringUtils.isEmpty(flowDTO.getHandleMode())) {
			if (flowDTO.getHandleMode().equals("91")) {
				msg = "您有一条案件已通过审核审批，请点击查看...";
				type = "4";
			}
		}
		if (!StringUtils.isEmpty(msg)) {
			try {
				ApiMessagePushController.send(msg, userInfo.getUsername(), type, flowDTO.getBusinessId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return ResultUtil.toResponse(ResultCode.SUCCESS);
	}

	/**
	 * 初步核实的流程提交
	 * 
	 * @param flowDTO
	 * @return
	 */
	@PostMapping("/taskComplatePer")
	public Result<Object> perFlow(@RequestBody FlowDTO flowDTO) {
		if (perVerifyService.haveParty(flowDTO.getBusinessId())) {
			this.caseDealService.saveTaskEntity(flowDTO, false);
			this.flowService.taskComplete(flowDTO);
			return ResultUtil.toResponse(ResultCode.SUCCESS);
		}
		return ResultUtil.toResponse(ResultCode.ERROR);
	}

	/**
	 * 初步核实的流程提交
	 * 
	 * @param flowDTO
	 * @return
	 */
	@GetMapping("/havePer")
	public Result<Object> havePer(@RequestParam String businessId) {
		if (perVerifyService.haveParty(businessId)) {
			return ResultUtil.toResponse(ResultCode.SUCCESS);
		}
		return ResultUtil.toResponse(ResultCode.ERROR);
	}

	/**
	 * 获取当前登录人所在部门的人员列表
	 * 
	 * @return
	 */
	@GetMapping("/getPersonByDeptId")
	public Result<List<NextOpinionDTO>> getPersonByDeptId() {
		User user = UserUtil.getCurrentUser();
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getPersonByDeptId(user.getDept_id());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}

	/**
	 * 获取当前登录人所在部门的并且有审核权限的人员列表
	 * 
	 * @return
	 */
	// @GetMapping("/getHaveRoleOpinionByDeptId")
	public Result<List<NextOpinionDTO>> getHaveRoleOpinionByDeptId() {
		User user = UserUtil.getCurrentUser();
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getHaveRoleOpinionByDeptId(user.getDept_id());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}

	/**
	 * 获取当前登录人所在部门的并且有执法人权限的人员列表
	 * 
	 * @return
	 */
	@GetMapping("/getHaveZfrRoleOpinionByDeptId")
	public Result<List<NextOpinionDTO>> getHaveZfrRoleOpinionByDeptId() {
		User user = UserUtil.getCurrentUser();
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getHaveZfrRoleOpinionByDeptId(user.getDept_id());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}

	/**
	 * 获取案件所在部门的并且有执法人权限的人员列表
	 * 
	 * @return
	 */
	@GetMapping("/getHaveZfrRoleOpinionByCaseId/{caseId}")
	public Result<List<NextOpinionDTO>> getHaveZfrRoleOpinionByCaseId(@PathVariable String caseId) {
		CaseEntity caseEntity = caseRepository.findOne(caseId);
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getHaveZfrRoleOpinionByDeptId(caseEntity.getDeptId());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}

	/**
	 * 根据当前登录人所在部门性质获取有审核权限的人员列表
	 * 
	 * @return
	 */
	// @GetMapping("/getHaveRoleOpinionByDeptProperty")
	@GetMapping("/getHaveRoleOpinionByDeptId")
	public Result<List<NextOpinionDTO>> getHaveRoleOpinionByDeptProperty() {
		User user = UserUtil.getCurrentUser();
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getHaveRoleOpinionByDeptProperty(user.getDept_id());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}

	/**
	 * 获取当前登录人所在部门的并且有审批权限的人员列getApproveUserByDeptProperty
	 * 
	 * @return
	 */
	// @GetMapping("/getApproveUserByDeptId")
	public Result<List<NextOpinionDTO>> getApproveUserByDeptId() {
		User user = UserUtil.getCurrentUser();
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getApproveUserByDeptId(user.getDept_id());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}

	/**
	 * 根据当前登录人所在部门的性质获取有审批权限的人员列表
	 * 
	 * @return
	 */
	// @GetMapping("/getApproveUserByDeptProperty")
	@GetMapping("/getApproveUserByDeptId")
	public Result<List<NextOpinionDTO>> getApproveUserByDeptProperty() {
		User user = UserUtil.getCurrentUser();
		List<NextOpinionDTO> nextOpinionDTOs = flowService.getApproveUserByDeptProperty(user.getDept_id());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, nextOpinionDTOs);
	}

	/**
	 * 获取当前登录人所在部门的并且有法制受理权限的人员列表
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
			for (int b = -1; (b = in.read()) != -1;) {
				out.write(b);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new EnforceException(ResultCode.BUSI_ERROR);
		}
	}

	/**
	 * 获取当前流程节点的流程图
	 * 
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
	 * 
	 * @return
	 */
	@GetMapping("/view/currentFlow/{taskId}/{key}/{businessId}")
	public void viewCurrentFlowAndFlowSeq(@PathVariable String taskId, @PathVariable String key,
			@PathVariable String businessId, HttpServletResponse response) {

		try {
			InputStream in = this.flowService.getCoordinateAndFlowSeqByTaskId(taskId, key, businessId);

			OutputStream out = response.getOutputStream();

			FileCopyUtils.copy(in, out);

			// for(int b = -1 ; ( b = is.read() ) != -1 ; ){
			// out.write(b);
			// }
			out.close();
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new EnforceException(ResultCode.BUSI_ERROR);
		}

	}
}
