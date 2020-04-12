package com.orhonit.ole.enforce.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.AppUtil;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dao.FilingDao;
import com.orhonit.ole.enforce.dao.LssuedDao;
import com.orhonit.ole.enforce.dao.PersonDao;
import com.orhonit.ole.enforce.dto.DeptDTO;
import com.orhonit.ole.enforce.dto.PersonAppDTO;
import com.orhonit.ole.enforce.dto.RightDTO;
import com.orhonit.ole.enforce.dto.api.ApiBaseLawMapDTO;
import com.orhonit.ole.enforce.dto.api.ApiCaseDealDTO;
import com.orhonit.ole.enforce.entity.CheckTypeEntity;
import com.orhonit.ole.enforce.service.checktype.CheckTypeService;
import com.orhonit.ole.enforce.service.flow.FlowService;
import com.orhonit.ole.enforce.service.person.PersonService;
import com.orhonit.ole.sys.config.ThreadLocalVariables;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.model.SysDictEntity;
import com.orhonit.ole.sys.service.SysDictService;

/**
 * 基础信息相关控制器
 * 1. 协办人列表
 * 2. 权利清单列表
 * 3. 法律依据
 * 5. 获取字典表
 * 6.专项检查获取下达通知部门列表
 * 7.检查类型
 * 8.修改密码
 * @author zhangjingy
 */

@RestController
@RequestMapping("/api/base")
public class ApiBaseController {
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private FilingDao filingDao;
	
	@Autowired
	private SysDictService sysDictService;
	
	@Autowired
	private LssuedDao lssuedDao;
	
	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private CheckTypeService checkTypeService;
	
	@Autowired
	private FlowService flowService;
	
	/**
	 * 
	 * @param deptId 需要查询的部门
	 * @param pageNum 起始分页
	 * @param pageSize 每页显示数量
	 * @param personName 执法人员姓名
	 * @param querySubDept 是否查询下级部门的执法人员
	 * @return
	 */
	@GetMapping("/personList")
	public Result<Object> getPersonList(@RequestParam(value = "deptId" , required = false) String deptId,
			@RequestParam(value = "pageNum" , required = false) Integer pageNum,
			@RequestParam(value = "pageSize" , required = false) Integer pageSize,
			@RequestParam(value = "personName" , required = false) String personName,
			@RequestParam(value = "querySubDept" , defaultValue = "0") Integer querySubDept){
		
		if(StringUtils.isEmpty(deptId)){
			//部门ID为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "部门编号为空.");
		}
		Map<String, Object> param = new HashMap<>();
		param.put("deptId", deptId);
		param.put("pageNum"	, pageNum);
		param.put("pageSize", pageSize);
		param.put("querySubDept", querySubDept);
		param.put("personName", personName);
		param.put("roleId", CommonParameters.Role.LAW_ENFORCEMENT_OFFICIALS);
		List<PersonAppDTO> personList = this.personService.getAppPersonListByParam(param);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, personList);
		
	}
	
	/**
	 * 权利清单列表
	 * @param deptId 部门ID
	 * @return 权利清单列表
	 * 
	 * */
	@GetMapping("/potenceList/{dept_id}")
	public Result<Object> getPotenceList(@PathVariable String dept_id){
		
		if(StringUtils.isEmpty(dept_id)){
			//部门ID为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "部门ID为空！");
		}
		List<RightDTO> potence = filingDao.rightAll(dept_id);
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("potence", potence);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);
	}
	
	/**
	 * 法律依据
	 * @param id 权责ID
	 * @return 违责依据列表
	 * 
	 * */
	@GetMapping("/lawList/{id}")
	public Result<Object> getWzList(@PathVariable String id){
		
		if(StringUtils.isEmpty(id)){
			//权责id为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "权责ID为空！");
		}
		List<ApiBaseLawMapDTO> list = this.filingDao.wzList(id);
		
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("wzList", list);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);
		
	}

	/**
	 * 字典表
	 * */
	@GetMapping("/dict")
	public Result<Object> getDictByTypeValue(@RequestParam(value="typeValue", required = false) String typeValue) {
		if(StringUtils.isEmpty(typeValue)){
			//检查标题为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "字典大类不能为空.");
		}
		
		List<SysDictEntity> sysDictEntities = this.sysDictService.getDictByTypeValue(typeValue);
		
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("sysDictEntities", sysDictEntities);
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);
	}
	
	/**
	 *专项检查获取下达通知部门列表
	 * @param deptId 部门编号
	 * @author 许家毅
	 */
	@GetMapping("/getCheckDeptList")
	public  Result<List<Map<String, Object>>>  getCheckDeptList(
			@RequestParam(value="deptId", required = false) String deptId
			) {
		if(StringUtils.isEmpty(deptId)){
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "部门ID不能为空");
		}
		List<DeptDTO> deptAll = lssuedDao.deptAll(deptId);
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(DeptDTO per:deptAll){
			map=new HashMap<>();
			map.put("id",per.getId());
			map.put("parent_id",per.getParent_id());
			map.put("text", per.getName());
			map.put("refId", per.getId());
			listMap.add(map);
		}
		List<Map<String,Object>> retMap=AppUtil.list2Tree(listMap, "parent_id", "id",deptId);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, retMap);
	}
		
	/**
	 * 检查类型
	 * 
	 * */
	@GetMapping("getCheckType")
	public  Result<Object>  getCheckType(){
		//当前登录人
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		PersonDTO user =this.personDao.findPersonInfo(personDTO.getId());
		List<CheckTypeEntity> checkTypeByDeotId = this.checkTypeService.checkTypeByDeotId(user.getDeptId());
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS,checkTypeByDeotId);
		
	}
	
	/**
	 * 根据部门Id获取部门的人员列表
	 * @return
	 */
	@GetMapping("/getPerson")
	public Result<Object> getPersonByDeptId(@RequestParam String deptId) {
		List<ApiCaseDealDTO> apiCaseDealDTO = this.flowService.getPerson(deptId);
        return ResultUtil.toResponseWithData(ResultCode.SUCCESS,apiCaseDealDTO);
	}
}
