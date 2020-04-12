package com.orhonit.ole.enforce.controller.shareapi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dao.FilingDao;
import com.orhonit.ole.enforce.dao.PersonDao;
import com.orhonit.ole.enforce.dto.RightDTO;
import com.orhonit.ole.enforce.dto.api.ApiBaseLawInfo;
import com.orhonit.ole.enforce.entity.BaseAreaEntity;
import com.orhonit.ole.enforce.entity.DeptEntity;
import com.orhonit.ole.enforce.repository.BaseAreaRepository;
import com.orhonit.ole.enforce.repository.DeptRepository;
import com.orhonit.ole.sys.config.ThreadLocalVariables;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.model.SysDictEntity;
import com.orhonit.ole.sys.service.SysDictService;

import lombok.extern.slf4j.Slf4j;

/**
 * 基础信息同步
 * @author ebusu
 *
 */
@RestController("shareApiBaseController")
@RequestMapping("/shareapi/base")
@Slf4j
public class ShareApiBaseController {
	
	private static final List<String> DATA_TABLE_SET = Arrays.asList(CommonParameters.ShareSyncCode.SYNC_AREA,
			CommonParameters.ShareSyncCode.SYNC_DEPT,
			CommonParameters.ShareSyncCode.SYNC_DEPT_PERSON,
			CommonParameters.ShareSyncCode.SYNC_LAW,
			CommonParameters.ShareSyncCode.SYNC_POTENCE);
	
	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private BaseAreaRepository baseAreaRepository;
	
	@Autowired
	private DeptRepository deptRepository;
	
	@Autowired
	private SysDictService sysDictService;
	
	@Autowired
	private FilingDao filingDao;
	
	@GetMapping("/info")
	public Result<Object> getBaseInfo(@RequestParam(value = "deptCode" , required = false) String deptCode,
			@RequestParam(value = "dataTable" , required = false) String dataTable,
			@RequestParam(value = "dateTime" , required = false) String dateTime) {
		
		if ( StringUtils.isEmpty(deptCode) ) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "部门编号为空.");
		}
		
		if ( StringUtils.isEmpty(dataTable) ) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "数据表为空.");
		}
		
		if ( !DATA_TABLE_SET.contains(dataTable) ) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "数据表错误.");
		}
		
		log.info("deptCode is {}", deptCode);
		log.info("dataTable is {}", dataTable);
		log.info("dateTime is {}", dateTime);
		
		
		PersonDTO personDTO = this.personDao.findPersonInfo(ThreadLocalVariables.getPersonDTO().getId());
		
		log.info("personDTO is {}" , personDTO);
		
		if ( personDTO == null ) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_ERROR_CERT_NUM.getCode(), "执法人员不存在.");
		}
		
		DeptEntity deptEntity = this.deptRepository.findOne(personDTO.getDeptId());
		
		if ( !deptCode.equals(deptEntity.getCode())) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "执法人员所属部门错误.");
		}
		
		Map<String, Object> resultMap = new HashMap<>();
		
		if ( CommonParameters.ShareSyncCode.SYNC_AREA.equals(dataTable) ) {
			this.getAreaData(resultMap);
		} else if ( CommonParameters.ShareSyncCode.SYNC_DEPT.equals(dataTable) ) {
			this.getDept(resultMap, deptCode);
		} else if ( CommonParameters.ShareSyncCode.SYNC_DEPT_PERSON.equals(dataTable) ) {
			this.getDeptPerson(resultMap, deptEntity.getId());
		} else if (CommonParameters.ShareSyncCode.SYNC_LAW.equals(dataTable)) {
			this.getLaw(resultMap, deptEntity.getId());
		} else if (CommonParameters.ShareSyncCode.SYNC_POTENCE.equals(dataTable)) {
			this.getPontence(resultMap, deptEntity.getId());
		}
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);
	}
	
	

	/**
	 * 行政区划
	 * @param resultMap
	 */
	private void getAreaData( Map<String, Object> resultMap ) {
		List<BaseAreaEntity> areas = this.baseAreaRepository.findAll();
		
		resultMap.put("area", areas);
	}
	
	/**
	 * 执法主体
	 * @param resultMap
	 */
	private void getDept( Map<String, Object> resultMap , String deptCode) {
		
		DeptEntity queryDept = new DeptEntity();
		
		queryDept.setCode(deptCode);
		
		Example<DeptEntity> example = Example.of(queryDept);
		
		DeptEntity deptEntity = this.deptRepository.findOne(example);
		
		resultMap.put("dept", deptEntity);
	}
	
	/**
	 * 执法人员
	 * @param resultMap
	 * @param deptCode
	 */
	private void getDeptPerson( Map<String, Object> resultMap , String deptId) {
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("deptId", deptId);
		
		List<PersonDTO> personList = this.personDao.getPersonListByParam(params);
		
		resultMap.put("personList", personList);
	}
	
	/**
	 * 法律数据
	 * @param resultMap
	 * @param deptCode
	 */
	private void getLaw( Map<String, Object> resultMap , String deptId) {
		
		List<ApiBaseLawInfo> apiBaseLawMapDTOs = this.filingDao.getLawList(deptId);
		
		resultMap.put("lawList", apiBaseLawMapDTOs);
	}
	
	/**
	 * 全责清单
	 * @param resultMap
	 * @param id
	 */
	private void getPontence(Map<String, Object> resultMap, String deptId) {
		
		List<RightDTO> potenceList = filingDao.rightAll(deptId);
		
		resultMap.put("potenceList", potenceList);
		
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
		
		resultMap.put("dictList", sysDictEntities);
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);
	}

}
