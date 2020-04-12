package com.orhonit.ole.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.utils.AppUtil;
import com.orhonit.ole.sys.config.ThreadLocalVariables;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.model.AreaEntity;
import com.orhonit.ole.sys.model.DeptDTO;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.repository.AreaRepository;
import com.orhonit.ole.sys.service.SysDeptService;
import com.orhonit.ole.sys.service.UserService;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;

/**
 * 主体树控制器
 * @author 武跃忠
 *
 */
@RestController
@RequestMapping("/sysDept")
public class SysDeptController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	SysDeptService sysDeptService;
	
	@Autowired
	AreaRepository areaRepository;
	
	/**
	 * 获取当前主体及其下级主体树
	 * @param deptId
	 * @return
	 */
	@RequestMapping("/tree")
	@ApiOperation(value = "主体")
	public Object deptTree() {
		User user =UserUtil.getCurrentUser();
		List<DeptDTO> deptTree = null;
		if(user == null){
			PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
			user = this.userService.getUserByPersonId(personDTO.getId());
		}
		if (!user.isAdmin()) {
			DeptDTO deptDTO = this.sysDeptService.findDeptById(user.getDept_id());
			if (CommonParameters.LawType.JD.equals(deptDTO.getLawType()) && CommonParameters.DeptProperty.XZJG.equals(deptDTO.getDeptProperty())) {
				// 如果是市本级的法制办则显示所有
				AreaEntity areaEntity = areaRepository.findOneById(Integer.parseInt(user.getArea_id()));
				if (CommonParameters.AreaLevel.DJ.equals(areaEntity.getLevel())) {
					deptTree = this.sysDeptService.deptTreeAll();
				} else {
					deptTree = this.sysDeptService.deptTreeByAreaId(user.getArea_id());
				}
			}else{
				deptTree = this.sysDeptService.deptTreeByDeptId(user.getDept_id());
			}
		}else{
			deptTree = this.sysDeptService.deptTreeAll();
		}
		
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(DeptDTO per:deptTree){
			map=new HashMap<>();
			map.put("id",per.getId());
			map.put("parent_id",per.getParentId());
			map.put("text", per.getName());
			map.put("refId", per.getId());
			listMap.add(map);
		}
		for (Map<String, Object> mapw : listMap) {
			Boolean flag = false;
			for (Map<String, Object> mapn : listMap) {
				if(mapw.get("parent_id").equals(mapn.get("id"))){
					flag = true;
					break;
				}
			}
			if(!flag){
				mapw.put("parent_id", "0");
			}
		}
		List<Map<String,Object>> retMap=AppUtil.list2Tree(listMap, "parent_id", "id","0");
		return retMap;
	}
	
	@RequestMapping("/treeLike")
	@ApiOperation(value = "主体")
	public Object deptTreeLike(TableRequest request) {
		User user =UserUtil.getCurrentUser();
		List<DeptDTO> deptTree = null;
		if(user == null){
			PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
			user = this.userService.getUserByPersonId(personDTO.getId());
		}
		if (!user.isAdmin()) {
			DeptDTO deptDTO = this.sysDeptService.findDeptById(user.getDept_id());
			if (CommonParameters.LawType.JD.equals(deptDTO.getLawType()) && CommonParameters.DeptProperty.XZJG.equals(deptDTO.getDeptProperty())) {
				// 如果是市本级的法制办则显示所有
				AreaEntity areaEntity = areaRepository.findOneById(Integer.parseInt(user.getArea_id()));
				if (CommonParameters.AreaLevel.DJ.equals(areaEntity.getLevel())) {
					deptTree = this.sysDeptService.deptTreeAll();
				} else {
					deptTree = this.sysDeptService.deptTreeByAreaId(user.getArea_id());
				}
			}else{
				deptTree = this.sysDeptService.deptTreeByDeptId(user.getDept_id());
			}
		}else{
			deptTree = this.sysDeptService.deptTreeAll();
		}
		
		List<DeptDTO> lists = new ArrayList<>();
		for(DeptDTO per:deptTree){
			if("0".equals(per.getParentId())) {
				lists.addAll(this.sysDeptService.deptTreeByParentId(per.getParentId()));
			}
		}
		
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(DeptDTO per:lists){
			map=new HashMap<>();
			map.put("id",per.getId());
			map.put("parent_id",per.getParentId());
			map.put("text", per.getName());
			map.put("refId", per.getId());
			listMap.add(map);
		}
		for (Map<String, Object> mapw : listMap) {
			Boolean flag = false;
			for (Map<String, Object> mapn : listMap) {
				if(mapw.get("parent_id").equals(mapn.get("id"))){
					flag = true;
					break;
				}
			}
			if(!flag){
				mapw.put("parent_id", "0");
			}
		}
		List<Map<String,Object>> retMap=AppUtil.list2Tree(listMap, "parent_id", "id","0");
		return retMap;
	}
	
	
	/**
	 * 获取当前主体及其下级主体树
	 * @param deptId
	 * @return
	 */
	@RequestMapping("/ztree")
	@ApiOperation(value = "主体")
	public Object ztree() {
		User user =UserUtil.getCurrentUser();
		List<DeptDTO> deptTree = null;
		if(user == null){
			PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
			user = this.userService.getUserByPersonId(personDTO.getId());
		}
		if (!user.isAdmin()) {
			DeptDTO deptDTO = this.sysDeptService.findDeptById(user.getDept_id());
			if (CommonParameters.LawType.JD.equals(deptDTO.getLawType()) && CommonParameters.DeptProperty.XZJG.equals(deptDTO.getDeptProperty())) {
				// 如果是市本级的法制办则显示所有
				AreaEntity areaEntity = areaRepository.findOneById(Integer.parseInt(user.getArea_id()));
				if (CommonParameters.AreaLevel.DJ.equals(areaEntity.getLevel())) {
					deptTree = this.sysDeptService.deptTreeAll();
				} else {
					deptTree = this.sysDeptService.deptTreeByAreaId(user.getArea_id());
				}
			}else{
				deptTree = this.sysDeptService.deptTreeByDeptId(user.getDept_id());
			}
		}else{
			deptTree = this.sysDeptService.deptTreeAll();
		}
		
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(DeptDTO per:deptTree){
			map=new HashMap<>();
			map.put("code",per.getId());
			map.put("parent_id",per.getParentId());
			map.put("name", per.getName());
			map.put("refId", per.getId());
			listMap.add(map);
		}
		for (Map<String, Object> mapw : listMap) {
			Boolean flag = false;
			for (Map<String, Object> mapn : listMap) {
				if(mapw.get("parent_id").equals(mapn.get("code"))){
					flag = true;
					break;
				}
			}
			if(!flag){
				mapw.put("parent_id", "0");
			}
		}
		List<Map<String,Object>> retMap=AppUtil.list2Tree(listMap, "parent_id", "code","0");
		return retMap;
	}
}
