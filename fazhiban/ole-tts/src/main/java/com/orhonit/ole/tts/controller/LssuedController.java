package com.orhonit.ole.tts.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.AppUtil;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;
import com.orhonit.ole.tts.dao.LssuedDao;
import com.orhonit.ole.tts.dto.CheckDocDTO;
import com.orhonit.ole.tts.dto.DeptDTO;
import com.orhonit.ole.tts.dto.DeptPersonDTO;
import com.orhonit.ole.tts.dto.FlowDTO;
import com.orhonit.ole.tts.dto.LssuedDTO;
import com.orhonit.ole.tts.dto.LssuedDetailInfoDTO;
import com.orhonit.ole.tts.entity.DeptEntity;
import com.orhonit.ole.tts.entity.DeptPersonEntity;
import com.orhonit.ole.tts.entity.LssuedEntity;
import com.orhonit.ole.tts.repository.DeptRepository;
import com.orhonit.ole.tts.service.dept.DeptService;
import com.orhonit.ole.tts.service.lssued.LssuedService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 专项控制器
 * @author lizj
 *
 */
@RestController
@RequestMapping("/lssued")
@Slf4j
public class LssuedController {
	
	@Autowired
	private LssuedService lssuedService;
	
	@Autowired
	private DeptService deptService;
	
	@Autowired
	private LssuedDao lssuedDao;
	
	@Autowired
	private DeptRepository deptRepository;
	
	
	@PostMapping("/temsave")
	public void temsave(@RequestBody @Valid LssuedDTO lssuedDTO) {
		LssuedEntity lssuedEntity = new LssuedEntity();
		BeanUtils.copyProperties(lssuedDTO, lssuedEntity);
		List<FlowDTO> flowDTO = this.lssuedService.temsave(lssuedEntity);
		
		//FlowDTO flowDTO = this.lssuedService.save(lssuedEntity);
		//if ( flowDTO != null ){
		//	this.flowThreadService.setFlowDTO(flowDTO);
		//	this.flowThreadService.setFlowType(1);
		//	Thread ft = new Thread(this.flowThreadService);
		//	ft.start();
		//	this.caseDealService.saveTaskEntity(flowDTO);
		//}
	}
	
	@PostMapping("/save")
	public void saveCase(@RequestBody @Valid LssuedDTO lssuedDTO) {
		LssuedEntity lssuedEntity = new LssuedEntity();
		BeanUtils.copyProperties(lssuedDTO, lssuedEntity);
		List<FlowDTO> flowDTO = this.lssuedService.save(lssuedEntity);
		
		//FlowDTO flowDTO = this.lssuedService.save(lssuedEntity);
		//if ( flowDTO != null ){
		//	this.flowThreadService.setFlowDTO(flowDTO);
		//	this.flowThreadService.setFlowType(1);
		//	Thread ft = new Thread(this.flowThreadService);
		//	ft.start();
		//	this.caseDealService.saveTaskEntity(flowDTO);
		//}
	}
			
	@RequestMapping("/persons/{deptId}")
	@ApiOperation(value = "执法人员列表")
	public List<Map<String, Object>> personList(@PathVariable String deptId) {
		List<DeptPersonDTO> personAll = lssuedDao.personAll(deptId);
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(DeptPersonDTO per:personAll){
			map=new HashMap<>();
			map.put("id",per.getPersonId());
			map.put("text", per.getPersonName());
			listMap.add(map);
		}
		return listMap;
	}
	
	
	@RequestMapping("/dept/{deptId}")
	@ApiOperation(value = "主体")
	public List<Map<String, Object>> deptAll(@PathVariable String deptId) {
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
		return retMap;
	}
	
	/**
	 * 获取当前主体及其下级主体树
	 * @param deptId
	 * @return
	 */
	@RequestMapping("/dept")
	@ApiOperation(value = "主体")
	public List<Map<String, Object>> deptAll() {
		User user = UserUtil.getCurrentUser();
		DeptEntity dept = null;
		if(!user.getUsername().equals("admin")){
			dept = this.deptRepository.findOne(user.getDept_id());
		}
		List<DeptDTO> deptTree = null;
		if(user.getUsername().equals("admin")){
			//管理员不需要任何操作
			deptTree = deptService.deptTreeAll();
		}else if(dept != null && dept.getDeptProperty() == 3 && dept.getLawType().equals("2")){
			//法制办
			//如果是市本级的法制办则显示所有
			if(!user.getArea_id().equals("15")){
				deptTree = deptService.deptTreeByAreaId(dept.getAreaId());
			}else{
				deptTree = deptService.deptTreeAll();
			}
		}
		else{
			//委办局
			deptTree = deptService.deptTreeByDeptId(user.getDept_id());
		}
		
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(DeptDTO per:deptTree){
			map=new HashMap<>();
			map.put("id",per.getId());
			map.put("parent_id",per.getParent_id());
			map.put("text", per.getName());
			map.put("refId", per.getId());
			listMap.add(map);
		}
		List<Map<String,Object>> retMap=AppUtil.list2Tree(listMap, "parent_id", "id",user.getDept_id());
		return retMap;
	}
	
	@GetMapping(value="/list")
	@ApiOperation(value = "下达通知列表")
	public TableResponse<LssuedEntity> listCase(TableRequest request) {
		//request.getParams().put("status", CommonParameters.CaseStatus.ANJA);
		return TableRequestHandler.<LssuedEntity> builder().countHandler(new CountHandler() {
			@Override 
			public int count(TableRequest request) {
				System.out.println(request.getParams().toString());
				return lssuedService.getCasecount(request.getParams());
			}
		}).listHandler(new ListHandler<LssuedEntity>() {

			@Override
			public List<LssuedEntity> list(TableRequest request) {
				List<LssuedEntity> list = lssuedService.getCaseList(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}
	
	@RequestMapping("/find/{id}")
	@ApiOperation(value = "根据专项id获取执法人员")
	public DeptPersonDTO get(@PathVariable String id) {
		return lssuedDao.getById(id);
	}
	
	/**
	 * 获取检查内容
	 * @param id
	 * @return
	 */
	@RequestMapping("/getCheck/{id}")
	@ResponseBody
	public LssuedEntity getCheckById(@PathVariable String id) {
		//根据ID获取案件
		LssuedEntity lssuedEntity = this.lssuedService.findOne(id);
		return lssuedEntity;
	}
	
	/**
	 * 专项检查详情
	 * @param checkId
	 * @return
	 */
	@GetMapping("/query/{checkId}")
	public Result<LssuedDetailInfoDTO> queryByCheckId(@PathVariable String checkId) {
		Map<String, Object> params = new HashMap<String, Object>();
		LssuedDetailInfoDTO dto = this.lssuedService.queryCheckByCheckId(checkId,params);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, dto);
	}
	
	@RequestMapping("/findUserName/{id}")
	@ApiOperation(value = "根据专项id获取执法人员")
	public DeptPersonEntity getUserName(@PathVariable String id) {
		return lssuedDao.getUserName(id);
	}
	 
	/**
	 * 获取案件文书
	 * @param id
	 * @return
	 */
	@GetMapping("/query/docContent/{checkId}")
	public Result<List<CheckDocDTO>> queryDocContentByCaseId(@PathVariable String checkId) {
		List<CheckDocDTO> caseDocDTOs = this.lssuedService.queryDocContentByCaseId(checkId);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, caseDocDTOs);
	}
	
	/**
	 * 新增专项字段
	 * @param id
	 * @return
	 */
	@PostMapping("/update")
	public  LssuedEntity updateLssued(@RequestBody @Valid LssuedEntity lssuedEntity) {
		return lssuedService.updateCheck(lssuedEntity);
	}
}
