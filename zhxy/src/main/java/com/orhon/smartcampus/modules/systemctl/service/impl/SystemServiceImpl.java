package com.orhon.smartcampus.modules.systemctl.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.orhon.smartcampus.utils.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.systemctl.entity.Menus;
import com.orhon.smartcampus.modules.systemctl.entity.Modules;
import com.orhon.smartcampus.modules.systemctl.entity.Operations;
import com.orhon.smartcampus.modules.systemctl.entity.OrgDepartments;
import com.orhon.smartcampus.modules.systemctl.entity.OrgDuties;
import com.orhon.smartcampus.modules.systemctl.entity.Widgets;
import com.orhon.smartcampus.modules.systemctl.mapper.SystemMapper;
import com.orhon.smartcampus.modules.systemctl.service.IMenusService;
import com.orhon.smartcampus.modules.systemctl.service.IModulesService;
import com.orhon.smartcampus.modules.systemctl.service.IOperationsService;
import com.orhon.smartcampus.modules.systemctl.service.IOrgDepartmentsService;
import com.orhon.smartcampus.modules.systemctl.service.IOrgDutiesService;
import com.orhon.smartcampus.modules.systemctl.service.ISystemService;
import com.orhon.smartcampus.modules.systemctl.service.IWidgetsService;
import com.orhon.smartcampus.utils.R;
import com.orhon.smartcampus.utils.TreeUtil;

@Service
public class SystemServiceImpl implements ISystemService {

	@Autowired
	private SystemMapper mapper;
	
	@Autowired
	private InfoService infoService;
	
	@Autowired
	private IOrgDepartmentsService departmentsService;
	
	@Autowired
	private IOrgDutiesService dutiesService;
	
	@Autowired
	private IOperationsService operationsService;
	
	@Autowired
	private IModulesService modulesService;
	
	@Autowired
	private IMenusService menusService;
	
	@Autowired
	private IWidgetsService widgetsService;

	@Override
	public R inserts(HashMap<String, Object> maps) {
		Integer duty_id = (Integer)maps.get("duty_id");
		Integer department_id = (Integer)maps.get("department_id");
		JSONArray user_ids  = (JSONArray) maps.get("user_id");
		if(department_id!=null&&duty_id!=null&&user_ids!=null) {
			for (Object user_id : user_ids) {
				if(mapper.getList(department_id,duty_id,(Integer)user_id)==null) {
					mapper.inserts(department_id,duty_id,(Integer)user_id);}
			}
			return R.ok();
		}
		return R.error("参数错误！");
	}

	@Override
	public boolean getList(Integer department_id, Integer duty_id, Integer user_id) {
		HashMap<String,Object> sdur = mapper.getList(department_id,duty_id,user_id);
		if(sdur==null) {
			return true;
		}
		return false;
	}

	@Override
	public R Delete(HashMap<String, Object> maps) {
		Integer duty_id = (Integer)maps.get("duty_id");
		Integer department_id = (Integer)maps.get("department_id");
		JSONArray user_ids  = (JSONArray) maps.get("user_id");
		SimpleDateFormat sf_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String leave_at = sf_.format(new Date());
		if(department_id!=null&&duty_id!=null&&user_ids!=null) {
			for (Object user_id : user_ids) {
				mapper.Delete(department_id,duty_id,(Integer)user_id,leave_at);
			}
			return R.ok();
		}
		return R.error("参数错误！");
	}

	@Override
	public R isLeaders(HashMap<String, Object> maps) {
		Integer duty_id = (Integer)maps.get("duty_id");
		Integer department_id = (Integer)maps.get("department_id");
		Integer user_id  = (Integer) maps.get("user_id");
		//0不是领导  1是领导
		Integer is_leaders  = (Integer) maps.get("is_leaders");
		if(department_id!=null&&duty_id!=null&&user_id!=null&&is_leaders==null) {
			mapper.isLeaders(department_id,duty_id,user_id,is_leaders);
			return R.ok();
		}
		return R.error("参数错误！");
	}

	@Override
	public R getUserDepartments() {
		Long userId = infoService.getCurrentLoginUserId();
		List<HashMap<String, Object>>  departmentsIds = mapper.getDepartmentsIds(userId);
		List<Integer> list=new ArrayList<Integer>();
		for (HashMap<String, Object> hashMap : departmentsIds) {
			list.add((Integer)hashMap.get("department_id"));
		}
		QueryWrapper<OrgDepartments> queryWrapper = new QueryWrapper<>();
		queryWrapper.in("id",list);
		queryWrapper.isNull("deleted_at");
		return R.ok().put("data",departmentsService.list(queryWrapper) );
		
	}

	@Override
	public R getUserDuties() {
		Long userId = infoService.getCurrentLoginUserId();
		List<HashMap<String, Object>> dutiesIds = mapper.getDutiesIds(userId);
		List<Integer> list=new ArrayList<Integer>();
		for (HashMap<String, Object> hashMap : dutiesIds) {
			list.add((Integer)hashMap.get("duty_id"));
		}
		QueryWrapper<OrgDuties> queryWrapper = new QueryWrapper<>();
		queryWrapper.in("id",list);
		queryWrapper.isNull("deleted_at");
		return R.ok().put("data", dutiesService.list(queryWrapper));	
	}
	
	
	@Override
	public R getUserOperations() {
		Long userId = infoService.getCurrentLoginUserId();		
		List<HashMap<String, Object>> OperationsIds = mapper.getOperationIds(userId);	
		List<Integer> list=new ArrayList<Integer>();
		for (HashMap<String, Object> hashMap : OperationsIds) {
			list.add((Integer)hashMap.get("operation_id"));
		}
		QueryWrapper<Operations> operationsWrapper = new QueryWrapper<>();
		operationsWrapper.in("id", list);
		operationsWrapper.isNull("deleted_at");
		return R.ok().put("data", operationsService.list(operationsWrapper));
		
	}

	@Override
	public R insertsSoddr(HashMap<String, Object> maps) {
		Integer department_id = (Integer)maps.get("department_id");
		JSONArray duty_ids = (JSONArray)maps.get("duty_id");
	    List<Object> list = new ArrayList<Object>();
		if(department_id!=null&&duty_ids!=null) {
			for (Object duty_id : duty_ids) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("department_id", department_id.toString());
				map.put("duty_id", duty_id.toString());
				list.add(map);
			}
			mapper.insertsSoddr(list);
			return R.ok();
		}
		return R.error("参数错误！");	
	}
	@Override
	public R insertsSoddr1(HashMap<String, Object> maps) {
		Integer department_id = (Integer)maps.get("department_id");
		JSONArray duty_ids  = (JSONArray) maps.get("duty_id");
		if(department_id!=null&&duty_ids!=null) {
			for (Object duty_id : duty_ids) {
				if(mapper.getSoddrList(department_id,(Integer)duty_id)==null) {
					mapper.insertsSoddr1(department_id,(Integer)duty_id);
				}
			}
			return R.ok();
		}
		return R.error("参数错误！");
	}
	
	

	@Override
	public R delSoddr(HashMap<String, Object> maps) {
		Integer department_id = (Integer)maps.get("department_id");
		JSONArray duty_ids = (JSONArray)maps.get("duty_id");
		if(department_id!=null&&duty_ids!=null) {
			for (Object duty_id : duty_ids) {
				mapper.delSoddr(department_id,(Integer)duty_id);
			}
			return R.ok();
		}
		return R.error("参数错误！");
	}

	@Override
	public void insertsSoddr(Integer department_id, Integer duty_id) {
		// TODO Auto-generated method stub
		mapper.insertsSoddr1(department_id,duty_id);
	}

	@Override
	public boolean getSoddrList(Integer department_id, Integer duty_id) {
		HashMap<String,Object> sdur = mapper.getSoddrList(department_id,duty_id);
		if(sdur==null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean getSourList(Integer user_id, Integer operation_id) {
		HashMap<String,Object> sdur = mapper.getSourList(user_id,operation_id);
		if(sdur==null) {
			return true;
		}
		return false;
	}

	@Override
	public R insertsSour(HashMap<String, Object> maps) {
		Integer user_id = (Integer)maps.get("user_id");
		JSONArray operation_ids  = (JSONArray) maps.get("operation_id");
		if(user_id!=null&&operation_ids!=null) {
			for (Object operation_id : operation_ids) {
				if(mapper.getSourList(user_id,(Integer)operation_id)==null) {
					mapper.insertsSour(user_id,(Integer)operation_id);}
			}
			return R.ok();
		}
		return R.error("参数错误！");
	}

	@Override
	public R delSour(HashMap<String, Object> maps) {
		
		Integer user_id = (Integer)maps.get("user_id");
		JSONArray operation_ids = (JSONArray)maps.get("operation_id");
		if(user_id!=null&&operation_ids!=null) {
			for (Object operation_id : operation_ids) {
				mapper.delSour(user_id,(Integer)operation_id);
			}
			return R.ok();
		}
		return R.error("参数错误！");
	}

	//@Override
	//public R getUserModules() {
	//	Integer school_id = (Integer) infoService.getCurrentUser().get("school_id");
	//	List<HashMap<String, Object>> modulesIds = mapper.getUserModulesIds(school_id);
	//	List<Integer> list=new ArrayList<Integer>();
	//	for (HashMap<String, Object> hashMap : modulesIds) {
	//		list.add((Integer)hashMap.get("module_id"));
	//	}
	//	QueryWrapper<Modules> modulesWrapper = new QueryWrapper<>();
	//	modulesWrapper.in("id", list);
	//	modulesWrapper.isNull("deleted_at");
	//	return R.ok().put("data", modulesService.list(modulesWrapper));
	//}
	@Override
	public R getUserModules() {
		Integer school_id = (Integer) infoService.getCurrentUser().get("school_id");
		Long user_id = infoService.getCurrentLoginUserId();
		List<HashMap<String, Object>> modules = mapper.getUserModules(school_id,user_id);
		return R.ok().put("data", modules);
	}

	@Override
	public R getUserMenus() {
		Integer school_id = (Integer) infoService.getCurrentUser().get("school_id");
		Long user_id = infoService.getCurrentLoginUserId();
		List<HashMap<String, Object>> modules = mapper.getUserMenus(school_id,user_id);
		return R.ok().put("data", modules);
	}
	@Override
	public R getUsersDepartmentOprations() {
		Integer school_id = (Integer) infoService.getCurrentUser().get("school_id");
		Long user_id = infoService.getCurrentLoginUserId();
		List<HashMap<String, Object>> departmentOprations = mapper.getUsersDepartmentOprations(user_id);
		return R.ok().put("data", departmentOprations);
	}

	@Override
	public R getModeuleMenus(HashMap<String, Object> maps) {
		Integer module_id = Integer.parseInt((String) maps.get("module_id"));
		QueryWrapper<Menus> modeulWrapper = new QueryWrapper<>();
		modeulWrapper.eq("module_id", module_id);
		modeulWrapper.isNull("deleted_at");
		return R.ok().put("data", menusService.list(modeulWrapper));
	}

	@Override
	public R getModeuleWidgets(HashMap<String, Object> maps) {
		Integer module_id = (Integer)maps.get("id");
		QueryWrapper<Widgets> widgetsWrapper = new QueryWrapper<>();
		widgetsWrapper.eq("module_id", module_id);
		widgetsWrapper.isNull("deleted_at");
		return R.ok().put("data", widgetsService.list(widgetsWrapper));
	}

	@Override
	public R getMenusAndWidgets(HashMap<String, Object> maps) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		if(maps==null) {
			return R.error();
		}
		if(maps.containsKey("menus")) {
			JSONArray menus  = (JSONArray) maps.get("menus");
			QueryWrapper<Menus> menussWrapper = new QueryWrapper<>();
			menussWrapper.in("name", menus);
			menussWrapper.isNull("deleted_at");
			List<Menus> list = menusService.list(menussWrapper);
			map.put("menus", list);
		}
		if(maps.containsKey("widgets")) {
			JSONArray widgets  = (JSONArray) maps.get("widgets");
			QueryWrapper<Widgets> widgetsWrapper = new QueryWrapper<>();
			widgetsWrapper.in("name", widgets);
			widgetsWrapper.isNull("deleted_at");
			List<Widgets> list1 = widgetsService.list(widgetsWrapper);
			map.put("widgets", list1);
		}
		return R.ok().put("data", map);
	}

	@Override
	public R getRenderMenu(Long moudle_id) {

		List<HashMap<String,Object>> modules = this.mapper.getUserRenderedMenu(moudle_id);
		//modules.forEach(item ->{
//			HashMap<String , Object> curVal = item;
//			HashMap<String , Object> rootMenu = new HashMap<>();
//			//如果是根部菜单,增加到返回集里
//			if (curVal.get("parent_id") == null){
//				System.out.println(item);
//			}
//			//rootMenu.put("level" , curVal.get("level"));

			//如果item.parent_id == null , 则存放到root表里

			//

		List<JSONObject> json = new ArrayList<>();
		List<JSONObject> json_parents = new ArrayList<>();
		List<JSONObject> json_final = new ArrayList<>();


		modules.forEach(item-> {
			JSONObject obj = (JSONObject) JSON.toJSON(item);
			json.add(obj);
		});

		json_parents = this.parents(json);
		json_final = this.orgChilds(json_parents , json);



		return R.ok().put("data", json_final);
	}

	private List<JSONObject> parents(List<JSONObject> objects ){
		List<JSONObject> json = new ArrayList<>();
		objects.forEach( item -> {
			JSONObject obj = (JSONObject) item;
			if (obj.get("parent_id") == null){
				JSONObject temp = new JSONObject();
				temp.put("level" , Integer.valueOf((Integer)obj.get("level")) + 1);
				temp.put("title" , obj.get("title"));
				temp.put("icon" , obj.get("icon"));
				temp.put("open" ,true);
				temp.put("selected" ,false);
				temp.put("disabled" ,false);
				json.add(temp);
			}
		});
		return json;
	}

	private List<JSONObject> orgChilds(List<JSONObject> parents , List<JSONObject> childrens ){
		parents.forEach(parent->{
			   parent.put("children" , new JSONArray());
			   childrens.forEach(child -> {
			   		 JSONObject par = (JSONObject) parent;
				     JSONObject chi = (JSONObject) child;
				     if (par.get("id") == chi.get("parent_id")){
						 JSONObject temp = new JSONObject();
						 temp.put("level" , Integer.valueOf((Integer)child.get("level")) + 1);
						 temp.put("title" , child.get("title"));
						 temp.put("icon" , child.get("icon"));
						 temp.put("open" ,true);
						 temp.put("selected" ,false);
						 temp.put("disabled" ,false);
						 ((JSONArray)parent.get("children")).add(temp);
					 }
			   });
			   //如果有第三层，在这里实现递归....
		});
		return parents;
	}





}
