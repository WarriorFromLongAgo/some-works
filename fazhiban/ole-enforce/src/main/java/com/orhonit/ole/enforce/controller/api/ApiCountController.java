package com.orhonit.ole.enforce.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dao.PersonDao;
import com.orhonit.ole.enforce.dto.CaseDocDTO;
import com.orhonit.ole.enforce.dto.CheckDocDTO;
import com.orhonit.ole.enforce.dto.DeptDTO;
import com.orhonit.ole.enforce.dto.FlowTaskCommentDTO;
import com.orhonit.ole.enforce.dto.PersonAppDTO;
import com.orhonit.ole.enforce.dto.api.ApiCountDTO;
import com.orhonit.ole.enforce.entity.CaseEntity;
import com.orhonit.ole.enforce.entity.CheckDailyEntity;
import com.orhonit.ole.enforce.entity.LssuedEntity;
import com.orhonit.ole.enforce.service.caseinfo.CaseService;
import com.orhonit.ole.enforce.service.checkdaily.CheckDailyService;
import com.orhonit.ole.enforce.service.dept.DeptService;
import com.orhonit.ole.enforce.service.file.AttachFileService;
import com.orhonit.ole.enforce.service.flow.FlowService;
import com.orhonit.ole.enforce.service.lssued.LssuedService;
import com.orhonit.ole.sys.config.ThreadLocalVariables;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.UserService;
/**
 * 统计相关控制器
 * 1.获取执法人员的办案统计
 * 2.案件细分统计
 * 3.案件材料统计
 * 4.附件细分统计
 * @author Zhangjy
 *
 */
@RestController
@RequestMapping("/api/count")
public class ApiCountController {
	
	@Autowired
	private CaseService caseService;
	
	@Autowired
	private CheckDailyService checkDailyService;
	
	@Autowired
	private LssuedService lssuedService;	

	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private FlowService flowService;
	
	@Autowired
	private AttachFileService attachFileService;
	
	@Autowired
	private DeptService deptService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 1.获取执法人员的办案统计
	 * @param certNum 执法证件号
	 * @return 专项检查数量、日常检查数量、案件数量
	 */
	@GetMapping("/caseCount")
	public Result<Object> queryUserCase() {
		Map<String, Object> params = new HashMap<>();
		
		//获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());
		
		params.put("personId", personDTO.getId());
		
		ApiCountDTO caseCount = this.caseService.needDealCount(user.getId());
		ApiCountDTO caseSubmitCount = this.caseService.caseSubmitCount(personDTO.getId());
		ApiCountDTO dailyCheckCount = this.checkDailyService.needDealCount(user.getId());	
		ApiCountDTO proCheckCount = this.lssuedService.needDealCount(user.getId());
		
		List<ApiCountDTO> list = new ArrayList<>();
		
		// 专项检查数量
		proCheckCount.setStatusName("专项检查");
		list.add(proCheckCount);
		// 日常检查数量
		dailyCheckCount.setStatusName("日常检查");
		list.add(dailyCheckCount);
		// 案件数量
		caseCount.setStatusName("案件");
		list.add(caseCount);
		// 案件上报数量
		caseCount.setStatusName("上报");
		list.add(caseSubmitCount);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, list);
	}
	
	/**
	 * 2.案件细分统计
	 * @param mode 类型：案件 6；专项检查 4 ；日常检查 5
	 * @param status 案件（或检查）的状态 ,待办 ：200
	 */
	@GetMapping("/getCount")
	public Result<Object> getCount(
			@RequestParam(value = "caseStatus" , required = false) String caseStatus,
			@RequestParam(value = "mode" , required = false) String mode,
			@RequestParam(value = "deptId" , required = false) String deptId,
			@RequestParam(value = "coordinator" , required = false) String coordinator) {
		
		String[] status = null;
		if(!caseStatus.equals(CommonParameters.FlowType.NEED_CASE)){
			status=caseStatus.split(",");
		}
		
		//获取当前登录人ID
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User userDL = this.userService.getUserByPersonId(personDTO.getId());
		DeptDTO dept = this.deptService.findDeptInfoById(userDL.getDept_id());
		PersonDTO user = this.personDao.findUserInfo(personDTO.getId());
		Map<String, Object> params = new HashMap<>();
		
		List<ApiCountDTO> list = new ArrayList<>();
		ApiCountDTO count = new ApiCountDTO();
		if(!StringUtils.isEmpty(deptId)){
			if(!deptId.equals("null")){
				params.put("deptId", deptId);
			}
			
		}else{
			List<PersonAppDTO> personAppDTO = this.personDao.getPersonRole(personDTO.getId());
			List<Integer> roleId = new ArrayList<Integer>();
		    for(int i = 0;i<personAppDTO.size();i++){
		        if( personAppDTO.get(i).getRoleId() == null ){
		        	return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(), ResultCode.APP_ROLE_ERROR.getMsg());
		        }else{
		        	roleId.add(personAppDTO.get(i).getRoleId());
		        }
		    }
			for(int i=0;i<roleId.size();i++){
				if ( roleId.get(i)==CommonParameters.Role.APPROVE  &&  roleId.get(i)== CommonParameters.Role.OPINION) {
			         return ResultUtil.toResponseWithMsg(ResultCode.APP_ROLE_ERROR.getCode(), ResultCode.APP_ROLE_ERROR.getMsg());
			    }else if ( roleId.get(i)==CommonParameters.Role.APPROVE  ||  roleId.get(i)== CommonParameters.Role.OPINION) {
			    	params.put("deptId", user.getDeptId());
			    }else if( roleId.get(i)==CommonParameters.Role.LAW_ENFORCEMENT_OFFICIALS){		
			    	if(!StringUtils.isEmpty(coordinator)){
			    		if(!coordinator.equals("null")){
			    			params.put("coordinator", personDTO.getId());
			    		}else{
			    			params.put("personId", personDTO.getId());
			    		}
			    	}else{
			    		params.put("personId", personDTO.getId());
			    	}
			    		
			    		
			    }
			}
		}
		if(StringUtils.isEmpty(mode)){
			//参数为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "mode is null");
		
		}else if(mode.equals(CommonParameters.FlowType.CASE)||mode == CommonParameters.FlowType.CASE){
			//案件	
			if(caseStatus.equals(CommonParameters.FlowType.NEED_CASE)){
				//待办案件
				count = this.caseService.needDealCount(user.getId());	
				count.setStatusName("待办案件");
				list.add(count);
			}else{ 
				//按状态统计
				for( int i = 0; i < status.length; i++ ){
					params.put("status", Integer.valueOf(status[i]));	
					count = this.caseService.caseCountByStatus(params);
					count.setStatus(status[i]);
					list.add(count);
				}
				
			}	
		}else if(mode.equals(CommonParameters.FlowType.DAILY_CHECK)||mode == CommonParameters.FlowType.DAILY_CHECK){
			//日常检查
			if(caseStatus.equals(CommonParameters.FlowType.NEED_CASE)){
				//待办检查
				count = this.checkDailyService.needDealCount(user.getId());	
				count.setStatusName("待办日常");
				list.add(count);
			}else{ 
				//按状态统计
				for( int i = 0; i < status.length; i++ ){
					params.put("status", status[i]);
					count = this.checkDailyService.caseCountByStatus(params);
					count.setStatus(status[i]);
					list.add(count);
				}
				
			}
			
		}else if(mode.equals(CommonParameters.FlowType.SPECIAL_CHECK)||mode == CommonParameters.FlowType.SPECIAL_CHECK){
			//专项检查
			if(caseStatus.equals(CommonParameters.FlowType.NEED_CASE)){
				//待办检查
				count = this.lssuedService.needDealCount(user.getId());	
				count.setStatusName("待办专项");
				list.add(count);
			}else{ 
				//按状态统计
				for( int i = 0; i < status.length; i++ ){
					params.put("status", status[i]);
					count = this.lssuedService.caseCountByStatus(params);
					count.setStatus(status[i]);
					list.add(count);
				}
				
			}	
		}else{
			//参数错误
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "mode is error");		
		}
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, list);
	}
	

	/**
	 * 3.案件材料统计
	 * @param certNum 案件编号
	 * @return 文书数量、附件数量、流程审批记录数量
	 */
	@GetMapping("/dataCount")
	public Result<Object> dataCount(
			@RequestParam(value = "caseNum" , required = false) String caseNum,
			@RequestParam(value = "mode" , required = false) String mode
			) {
		
		if(StringUtils.isEmpty(caseNum)){
			//检查标题为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "案件编号不能为空");
		}
		
		if ( StringUtils.isEmpty(mode)) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "类型不能为空");
		}
		
		String flowKey = "";
		int docSize=0;
		if ( mode.equals(CommonParameters.FlowType.DAILY_CHECK)) {
			flowKey = CommonParameters.FlowKey.DAY_CHECK;
			//文书统计
			CheckDailyEntity checkDailyEntity = this.checkDailyService.findByCheckNum(caseNum);
			List<CheckDocDTO> list = this.checkDailyService.queryDocContentByCheckId(checkDailyEntity.getId());
			docSize=list.size();
		} else if ( mode.equals(CommonParameters.FlowType.SPECIAL_CHECK)) {
			flowKey = CommonParameters.FlowKey.PRO_CHECK;
			//文书统计
			LssuedEntity lssuedEntity = this.lssuedService.getLssuedByCheckNum(caseNum);
			List<CheckDocDTO> list = this.lssuedService.queryDocContentByCaseId(lssuedEntity.getId());
			docSize=list.size();
		} else if ( mode.equals(CommonParameters.FlowType.CASE)) {
			flowKey = CommonParameters.FlowKey.CASE;
			//文书统计
			CaseEntity caseEntity = this.caseService.getCaseByCaseNum(caseNum);
			List<CaseDocDTO> list = this.caseService.queryDocContentByCaseId(caseEntity.getId());
			docSize=list.size();
		} else {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "类型错误");
		}
		
		//流程审批记录统计
		String pid = this.flowService.getProcessInstanceIdByKeyAndBusinessId(flowKey, caseNum);
		List<FlowTaskCommentDTO> comments = this.flowService.getCommemntByProcessInstanceId(pid);
		
		//附件统计
		int fileCount = this.attachFileService.getFileCountByCaseNum(caseNum);
		
		Map<String, Object> resultMap = new HashMap<>();
		
		// 流程审批数量
		resultMap.put("flowCount", comments.size());
		// 文书数量
		resultMap.put("docCount", docSize);
		// 附件数量
		resultMap.put("fileCount", fileCount);	
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);
	}
	
	/**
	 * 4.附件细分统计
	 * @param caseNum 案件编号
	 * @return 视频数量、音频数量、图片数量、PDF数量
	 */
	@GetMapping("/getFileCount")
	public Result<Object> getFileCount(
			@RequestParam(value = "caseNum" , required = false) String caseNum
			) {
		int fileCount = this.attachFileService.getFileCountByCaseNum(caseNum);
		
		int mp4Count = this.attachFileService.getMp4CountCountByCaseNum(caseNum);
		
		int mp3Count = this.attachFileService.getMp3CountCountByCaseNum(caseNum);
		
		int picCount = this.attachFileService.getPicCountByCaseNum(caseNum);
		
		int pdfCount = fileCount-mp4Count-mp3Count-picCount;
		Map<String, Object> resultMap = new HashMap<>();
		
		// 视频数量
		resultMap.put("mp4Count", mp4Count);
		// 音频数量
		resultMap.put("mp3Count", mp3Count);
		// 图片数量
		resultMap.put("picCount", picCount);	
		// PDF数量
		resultMap.put("pdfCount", pdfCount);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);
	}
	
}
