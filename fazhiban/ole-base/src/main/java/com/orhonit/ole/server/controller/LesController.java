package com.orhonit.ole.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.server.dao.LesDao;
import com.orhonit.ole.server.model.LawLevel;
import com.orhonit.ole.server.model.Lesection;
import com.orhonit.ole.server.model.LtcAtt;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;
/**
 * 区域管理控制器
 * 
 */
@RestController
@RequestMapping("/LesLists")
public class LesController {

	@Autowired
	private LesDao lepDao;

	@GetMapping
	@ApiOperation(value = "主体列表")
	public TableResponse<Lesection> list(TableRequest request) {
		return TableRequestHandler.<Lesection> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				int result=lepDao.count(request.getParams());
				return result;
			}
		}).listHandler(new ListHandler<Lesection>() {

			@Override
			public List<Lesection> list(TableRequest request) {
				List<Lesection> lists=lepDao.list(request.getParams(), request.getStart(), request.getLength());
				List<Lesection> lists1=lepDao.getLtcs();
				for(Lesection les:lists){
					for(Lesection les1:lists1){
						if(les.getParent_id().equals(String.valueOf(les1.getId()))){
							les.setParent_id(les1.getName());
							les.setMgl_parent_id(les1.getMgl_name());
							break;
						}
					}
				}
				return lists;
			}
		}).build().handle(request);
	}
	
	@GetMapping("/parents")
	@ApiOperation(value = "一级菜单")
	public List<Map<String, Object>> parentList() {
		List<Lesection> parents = lepDao.getLtcs();
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(Lesection per:parents){
			map=new HashMap<>();
			map.put("id",per.getId());
			map.put("name", per.getName());
			listMap.add(map);
		}
		return listMap;
	}

	@GetMapping("/areaTree")
	@ApiOperation(value = "区域划分")
	public List<Map<String, Object>> parentsTree() {
		Map<String, Object> params = new HashMap<>();
		User user = UserUtil.getCurrentUser();
		if(!user.getArea_id().equals("15")){
			//旗县区
			params.put("area_id", user.getArea_id());
		}else{
			//市本级、管理员不做操作
		}
		List<Lesection> parents = lepDao.getAreaTree(params);
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(Lesection les:parents){
			map=new HashMap<>();
			map.put("id",les.getId());		
			map.put("pId",les.getParent_id());
			map.put("name", les.getName());
			map.put("mgl_name", les.getMgl_name());
			listMap.add(map);
		}
		return listMap;
	}
}
