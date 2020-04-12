package com.orhonit.ole.enforce.controller.ps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dto.ps.CheckPersonDTO;
import com.orhonit.ole.enforce.dto.ps.LawPersonDTO;
import com.orhonit.ole.enforce.dto.ps.PeAndArSelectDTO;
import com.orhonit.ole.enforce.dto.ps.PeAndDepDTO;
import com.orhonit.ole.enforce.dto.ps.PssPerDTO;
import com.orhonit.ole.enforce.dto.ps.SelectPersonByAreaIdDTO;
import com.orhonit.ole.enforce.dto.ps.SelectPersonById;
import com.orhonit.ole.enforce.service.person.PersonService;
import com.orhonit.ole.enforce.utils.PageList;
import com.orhonit.ole.sys.dto.PersonDTO;

/**
 * 公示系统
 *执法人员控制器
 * 1. 执法人员查询
 * 2. 执法人员详细
 * 3. 各个区域执法人员执法类型数量统计
 * 4.一个地区下的每个部门的人员统计
 * @author ebusu
 *
 */
@RestController
@RequestMapping("/ps/person")
public class PersonController {

	@Autowired
	private PersonService personService;


	/**
	 * 简要说明以及逻辑
	 * @param param 入参
	 * @return 返回值
	 */
	@GetMapping("/find")
	public Result<List<PersonDTO>> getPersonListByParam(@PathVariable Map<String,Object> param){
		
		List<PersonDTO> deptList = personService.getPersonListByParam(param);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, deptList);
	}
	
	
	/**
	 * ps 
	 * 执法人员查询
	 * @param certNum 执法证账件号 入参
	 * @param name 执法人员姓名 入参
	 * @param deptId 部门ID 入参
	 * @param lawType 执法类型 入参
	 * 
	 */
	@GetMapping("/getPerDTO")
	public Result<List<PersonDTO>> getPerDTO(
			@RequestParam(value="certNum",defaultValue="",required=false) String certNum,
			@RequestParam(value="name",defaultValue="",required=false) String name,
			@RequestParam(value="deptId",defaultValue="",required=false) String deptId,
			@RequestParam(value="lawType",defaultValue="",required=false) String lawType
			){
		
		Map<String, Object> ParamMap = new HashMap<String, Object>();
		ParamMap.put("certNum", certNum);
		ParamMap.put("name", name);
		ParamMap.put("deptId", deptId);
		ParamMap.put("lawType", lawType);
		
		List<PssPerDTO> perMap=this.personService.getPerDTO(ParamMap);
		
		return (Result) ResultUtil.toResponseWithData(ResultCode.SUCCESS, perMap);
		
	}
	
	/**
	 * ps 
	 * 执法人员详细
	 * @param personId 执法人员ID
	 * @return
	 */
	@GetMapping("/getByPersonId")
	public Result<List<CheckPersonDTO>> getSelectByPersonId(
			@RequestParam(value="personId",defaultValue="",required=false) String personId
			){
		
		Map<String, Object> personIdMp = new HashMap<>();
		personIdMp.put("personId", personId);
		
		List<CheckPersonDTO> pmPersonId = this.personService.getSelectByPersonId(personIdMp);
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, pmPersonId);
		
	}

	@GetMapping("/getPeAndArSelect")
	public Result<List<PeAndArSelectDTO>> getPeAndArSelect(){
		List<PeAndArSelectDTO> PeAndSelectlist =this.personService.getPeAndArSelect();
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, PeAndSelectlist);
		
	}
	/**
	 * ps 
	 * PC各个区域执法人员数量统计
	 * @return
	 */
	@GetMapping("/getPeAndAr")
	public Result<List<PeAndArSelectDTO>> getPeAndAr(){
		List<PeAndArSelectDTO> PeAndSelectlist =this.personService.getPeAndAr();
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, PeAndSelectlist);
		
	}
	/**
	 * ps
	 * 各个区域执法主体性质分类统计(条形图用)
	 * @return
	 */
	@GetMapping("/getSelectSup")
	public Result<List<LawPersonDTO>> getSelectSup(){
		
		List<LawPersonDTO> selectLaw = this.personService.getSelectLaw();
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, selectLaw);
		
	}
	/**
	 * ps
	 * 各个区域执法主体性质分类统计(条形图用)
	 * @return
	 */
	@GetMapping("/getSelectCount")
	public Result<List<LawPersonDTO>> getSelectCount(){
		
		List<LawPersonDTO> selectLaw = this.personService.getSelectSup();
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, selectLaw);
		
	}
	/**
	 * ps
	 * 各个区域执法人员统计
	 * @return
	 */
	@GetMapping("/getAllLaw")
	public Result<List<LawPersonDTO>> getAllLaw(){
		
		List<LawPersonDTO> selectLaw = this.personService.getAllLaw();
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, selectLaw);
		
	}
	
	/**
	 * PS
	 * 一个地区下的每个部门的人员统计
	 * @param areaId 区域ID
	 * @return
	 */
	@GetMapping("/getPeAndDepByAre")
	public Result<List<PeAndDepDTO>> getPeAndDepByAre(
			@RequestParam(value="",defaultValue="",required=false) String areaId){
		
		Map<String, Object> PADBMap = new HashMap<>();
		PADBMap.put("areaId", areaId);
		
		List<PeAndDepDTO> MapPADBList=this.personService.getPeAndDepByAre(PADBMap);
	
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, MapPADBList);
		
	}
	/**
	 * ps
	 * 根据区域name查询执法人员详细信息
	 * @param areaId 区域ID
	 * @return
	 */
	@GetMapping("/getSelectByAreaId")
	public Result<PageList<SelectPersonByAreaIdDTO>> getSelectByAreaId(
				@RequestParam(value="areaName",defaultValue="",required=false) String areaName,
				@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
				@RequestParam(value="pageSize",defaultValue="",required=false) int pageSize){
		SelectPersonByAreaIdDTO selectPersonByAreaIdDTO =new SelectPersonByAreaIdDTO();
		if(currentPage != 0 & pageSize != 0){
			selectPersonByAreaIdDTO.setAreaName(areaName);
			selectPersonByAreaIdDTO.setCurrentPage(currentPage);
			selectPersonByAreaIdDTO.setPageSize(pageSize);
		}else{
			selectPersonByAreaIdDTO.setAreaName(areaName);
			selectPersonByAreaIdDTO.setCurrentPage(1);
			selectPersonByAreaIdDTO.setPageSize(20);
		}
		
		PageList<SelectPersonByAreaIdDTO> SelectByAreaIdMap = this.personService.getSelectByAreaId(selectPersonByAreaIdDTO);
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, SelectByAreaIdMap);
		
	}
	/**
	 * ps
	 * 根据区域name查询执法人员详细信息
	 * @param areaId 区域ID
	 * @return
	 */
	@GetMapping("/getByAreaId")
	public Result<PageList<SelectPersonByAreaIdDTO>> getByAreaId(
				@RequestParam(value="areaName",defaultValue="",required=false) String areaName,
				@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
				@RequestParam(value="pageSize",defaultValue="",required=false) int pageSize){
		SelectPersonByAreaIdDTO selectPersonByAreaIdDTO =new SelectPersonByAreaIdDTO();
		if(currentPage != 0 & pageSize != 0){
			selectPersonByAreaIdDTO.setAreaName(areaName);
			selectPersonByAreaIdDTO.setCurrentPage(currentPage);
			selectPersonByAreaIdDTO.setPageSize(pageSize);
		}else{
			selectPersonByAreaIdDTO.setAreaName(areaName);
			selectPersonByAreaIdDTO.setCurrentPage(1);
			selectPersonByAreaIdDTO.setPageSize(20);
		}
		
		PageList<SelectPersonByAreaIdDTO> SelectByAreaIdMap = this.personService.getByAreaId(selectPersonByAreaIdDTO);
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, SelectByAreaIdMap);
		
	}
	/**
	 * ps
	 * 根据deptId查询执法人员详细信息
	 * @param areaId 区域ID
	 * @return
	 */
	@GetMapping("/getSelectBydeptIdPC")
	public Result<PageList<SelectPersonByAreaIdDTO>> getSelectBydeptIdPC(
				@RequestParam(value="deptId",defaultValue="",required=false) String deptId,
				@RequestParam(value="currentPage",defaultValue="",required=false) int currentPage,
				@RequestParam(value="pageSize",defaultValue="",required=false) int pageSize){
		
		
		SelectPersonByAreaIdDTO selectPersonByAreaIdDTO =new SelectPersonByAreaIdDTO();
		if(currentPage != 0 & pageSize != 0){
			selectPersonByAreaIdDTO.setDeptId(deptId);
			selectPersonByAreaIdDTO.setCurrentPage(currentPage);
			selectPersonByAreaIdDTO.setPageSize(pageSize);
		}else{
			selectPersonByAreaIdDTO.setDeptId(deptId);
			selectPersonByAreaIdDTO.setCurrentPage(1);
			selectPersonByAreaIdDTO.setPageSize(20);
		}
		
		PageList<SelectPersonByAreaIdDTO> SelectByAreaIdMap = this.personService.getSelectBydeptIdPC(selectPersonByAreaIdDTO);
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, SelectByAreaIdMap);
		
	}
	/**
	 * ps
	 * 根据deptId查询执法人员详细信息 手机端
	 * @param areaId 区域ID
	 * @return
	 */
	@GetMapping("/getSelectBydeptId")
	public Result<List<SelectPersonByAreaIdDTO>> getSelectBydeptId(
				@RequestParam(value="deptId",defaultValue="",required=false) String deptId){
		Map<String, Object> map = new HashMap<>();
		map.put("deptId", deptId);
		List<SelectPersonByAreaIdDTO> SelectByAreaIdMap = this.personService.getSelectBydeptId(map);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, SelectByAreaIdMap);
		
	}
	/**
	 * ps
	 * 根据人员id查询执法人员详细
	 * @param personId
	 * @return
	 */
	@GetMapping("/getSelectPersonById")
	public Result<List<SelectPersonById>> getSelectPersonById(
				@RequestParam(value="personId",defaultValue="",required=false) String personId
			){
		Map<String, Object> mapGetSelectPersonById = new HashMap<>();
		mapGetSelectPersonById.put("personId", personId);
		List<SelectPersonById> selectPersonByIdMap = this.personService.getSelectPersonById(mapGetSelectPersonById);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, selectPersonByIdMap);
		
	}
	
}
