package com.orhonit.ole.server.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.annotation.LogAnnotation;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.utils.AppUtil;
import com.orhonit.ole.server.model.LawLevel;
import com.orhonit.ole.server.service.LawLevelService;
import com.orhonit.ole.sys.dto.UserDto;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;
/**
 * 法律级别管理控制器
 * 
 */
@RestController
@RequestMapping("/LawLevel")
public class LawLevelController {

	@Autowired
	private LawLevelService lawLevelService;

	@GetMapping("/list")
	@ApiOperation(value = "法律级别列表")
	public TableResponse<LawLevel> list(TableRequest request) {
		return TableRequestHandler.<LawLevel> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				int result=lawLevelService.count(request.getParams());
				return result;
			}
		}).listHandler(new ListHandler<LawLevel>() {

			@Override
			public List<LawLevel> list(TableRequest request) {
				List<LawLevel> list=lawLevelService.list(request.getParams(), request.getStart(), request.getLength());
				for(LawLevel lawLevel:list){
					String name = lawLevelService.getNameById(lawLevel.getParentId());
					String mglName = lawLevelService.getMglNameById(lawLevel.getParentId());
					lawLevel.setParentName(name);
					lawLevel.setMglPanrentName(mglName);
				}
				return list;
			}
		}).build().handle(request);
	}
	
	@GetMapping("/parents")
	@ApiOperation(value = "法律级别划分")
	public List<Map<String, Object>> parentList() {
		List<LawLevel> parents = this.lawLevelService.getAllLawLevel();
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(LawLevel lawLevel:parents){
			map=new HashMap<>();
			map.put("id",lawLevel.getId());		
			map.put("parentId",lawLevel.getParentId());
			map.put("text", lawLevel.getName());
			listMap.add(map);
		}
		List<Map<String, Object>> retMap = AppUtil.list2Tree(listMap, "parentId", "id", null);
		return retMap;
	}

	@GetMapping("/parentsTree")
	@ApiOperation(value = "法律级别划分")
	public List<Map<String, Object>> parentsTree() {
		List<LawLevel> parents = this.lawLevelService.getAllLawLevel();
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(LawLevel lawLevel:parents){
			map=new HashMap<>();
			map.put("id",lawLevel.getId());		
			map.put("pId",lawLevel.getParentId());
			map.put("name", lawLevel.getName());
			map.put("mglName", lawLevel.getMglName());
			listMap.add(map);
		}
		return listMap;
	}
	
	@LogAnnotation
	@PostMapping
	@ApiOperation(value = "保存法律级别")
	public void save(@RequestBody LawLevel lawLevel) {
		User user = UserUtil.getCurrentUser();
		lawLevel.setId(UUID.randomUUID().toString());
		lawLevel.setDelFlag("0");
		lawLevel.setCreateBy(user.getUsername());
		lawLevel.setCreateName(user.getNickname());
		lawLevel.setCreateDate(new Date());
		this.lawLevelService.save(lawLevel);
	}
	
	@LogAnnotation
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除法律级别")
	public void delete(@PathVariable String id) {
		this.lawLevelService.updateDel(id);
	}
	
	@GetMapping("/getInfo/{id}")
	@ApiOperation(value = "法律级别详情")
	public LawLevel getInfoById(@PathVariable String id) {
		return this.lawLevelService.getInfoById(id);
	}
	
	@PutMapping("/update")
	@ApiOperation(value = "修改法律级别")
	public void update(@RequestBody LawLevel lawLevel) {
		User user = UserUtil.getCurrentUser();
		lawLevel.setUpdateBy(user.getUsername());
		lawLevel.setUpdateName(user.getNickname());
		lawLevel.setUpdateDate(new Date());
		this.lawLevelService.update(lawLevel);
	}
}
