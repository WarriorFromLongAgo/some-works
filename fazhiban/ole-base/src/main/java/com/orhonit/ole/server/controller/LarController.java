package com.orhonit.ole.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.utils.AppUtil;
import com.orhonit.ole.server.dao.LarDao;
import com.orhonit.ole.server.dao.LepDao;
import com.orhonit.ole.server.dao.LtcAttDao;
import com.orhonit.ole.server.model.LarAtt;
import com.orhonit.ole.server.model.LrlAtt;
import com.orhonit.ole.server.model.LtcAtt;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;
/**
 * 权责清单控制器
 * 
 */
@RestController
@RequestMapping("/LarLists")
public class LarController {

	@Autowired
	private LarDao larDao;
	
	@Autowired
	private LepDao lepDao;
	
	@Autowired
	private LtcAttDao ltcAttDao;

	@GetMapping
	@RequiresPermissions(value = "sys:log:query")
	@ApiOperation(value = "权责清单列表")
	public TableResponse<LarAtt> list(TableRequest request) {
		User user = UserUtil.getCurrentUser();
		LtcAtt ltcAtt = ltcAttDao.getLtc(user.getDept_id());
		request.getParams().put("dept_id", user.getDept_id());
		if(ltcAtt != null && ltcAtt.getDept_property() == 3 && ltcAtt.getLaw_type().equals("2")){
			//法制办
			//如果是市本级的法制办则显示所有
			if(!user.getArea_id().equals("15")){
				request.getParams().put("lx_type", 1);
			}
		}else if(user.getUsername().equals("admin")){
			//管理员不需要任何操作
		}
		else{
			//委办局
			request.getParams().put("lx_type", 2);
			request.getParams().put("deptIds", lepDao.execFunction(null, user.getDept_id()));
		}
		
		return TableRequestHandler.<LarAtt> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				int result=larDao.count(request.getParams());
				return result;
			}
		}).listHandler(new ListHandler<LarAtt>() {

			@Override
			public List<LarAtt> list(TableRequest request) {
				 List<LarAtt> list= larDao.list(request.getParams(), request.getStart(), request.getLength());				
				 return list;
			}
		}).build().handle(request);
	}
	
	@GetMapping("/serconns")
	@ApiOperation(value = "主体列表")
	public TableResponse<LrlAtt> conlist(TableRequest request) {

		return TableRequestHandler.<LrlAtt> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
					return larDao.larcount(request.getParams());
			}
			}).listHandler(new ListHandler<LrlAtt>() {
				@Override
				public List<LrlAtt> list(TableRequest request) {
					List<LrlAtt> larlists=larDao.larlists(request.getParams());
					return larlists;
				}
			}).build().handle(request);
		
	}
	
	@GetMapping("/coms")
	@ApiOperation(value = "主体列表")
	public TableResponse<LrlAtt> comlist(TableRequest request) {

		return TableRequestHandler.<LrlAtt> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
					return 1;
			}
			}).listHandler(new ListHandler<LrlAtt>() {
				@Override
				public List<LrlAtt> list(TableRequest request) {
					List<LrlAtt> larlists=larDao.comlists(request.getParams());;
					return larlists;
				}
			}).build().handle(request);
		
	}

	@GetMapping("/parents")
	@ApiOperation(value = "一级菜单")
	public List<Map<String, Object>> parentList() {
		String name=null;
		List<LarAtt> parents = larDao.getLtcs(name);
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(LarAtt per:parents){
			map=new HashMap<>();
			map.put("id",per.getId());
			map.put("name", per.getName());
			listMap.add(map);
		}
		return listMap;
	}
	
	@GetMapping("/lars")
	@ApiOperation(value = "区划")
	public List<Map<String, Object>> ltcAll(@RequestParam(value = "name" , required = false) String name) {
		List<LarAtt> areaAll = larDao.getLtcs(name);
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(LarAtt per:areaAll){
			map=new HashMap<>();
			map.put("id",per.getId());
			map.put("parentId",per.getParent_id()==null?"0":per.getParent_id());
			map.put("text", per.getName());
			listMap.add(map);
		}
		List<Map<String,Object>> retMap=AppUtil.list2Tree(listMap, "parentId", "id",null);
		
		return retMap;
	}
	
	
}
