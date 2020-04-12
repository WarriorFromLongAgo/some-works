package com.orhonit.ole.server.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
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
import com.orhonit.ole.server.dao.LesDao;
import com.orhonit.ole.server.model.Lesection;
import com.orhonit.ole.server.model.LrlDetAtt;
import com.orhonit.ole.server.model.LtcAtt;
import com.orhonit.ole.server.service.SectionService;
import com.orhonit.ole.sys.dao.UserDao;
import com.orhonit.ole.sys.dto.UserDto;
import com.orhonit.ole.sys.model.Area;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户相关接口
 * 
 * @author caodw
 *
 */
@Slf4j(topic = "adminLogger")
@RestController
@RequestMapping("/sections")
public class SectionController {

	@Autowired
	private SectionService sectionService;
	@Autowired
	private LesDao lesDao;

	@LogAnnotation
	@PostMapping
	@ApiOperation(value = "保存地区")
	public int saveUser(@RequestBody Lesection lesection) {
		User user=UserUtil.getCurrentUser();
		lesection.setCreate_name(user.getNickname());
		lesection.setCreate_by(user.getUsername());
		lesection.setCreate_date(new Date());
		return lesDao.save(lesection);
	}

	@LogAnnotation
	@PutMapping
	@ApiOperation(value = "修改区域")
	public void updateLes(@RequestBody Lesection userDto) {
		User user=UserUtil.getCurrentUser();
		userDto.setUpdate_name(user.getNickname());
		userDto.setUpdate_by(user.getUsername());
		userDto.setUpdate_date(new Date());
		lesDao.updateLes(userDto);
	}


	@ApiOperation(value = "当前登录用户")
	@GetMapping("/current")
	public User currentUser() {
		return UserUtil.getCurrentUser();
	}

	@ApiOperation(value = "根据地区id获取区域")
	@GetMapping("/{id}")
	public Lesection getLes(@PathVariable String id) {
		return lesDao.getLes(id);
	}
	
	@ApiOperation(value = "根据法律法规")
	@GetMapping("/ishave/{id}")
	public Lesection getDetLrl(@PathVariable String id) {
		List<Lesection> less=lesDao.areaAllP(id);
		List<LtcAtt> ltcs=lesDao.areaAllPd(id);
		String text="";
		if(less.size()>0){
			String sectxt="  关联子地区：";
			for(Lesection lesec:less){
				sectxt="  "+sectxt+lesec.getName()+",";
			}
			 text=sectxt.substring(0, sectxt.length()-1);
		}
        if(ltcs.size()>0){
        	String ltctxt="  关联子地区：";
            for(LtcAtt latt:ltcs){
            	ltctxt="  "+ltctxt+latt.getName()+",";
			}
            text=text+" "+ltctxt.substring(0, ltctxt.length()-1);
		}
       
		Lesection les=new Lesection();
		les.setName(text);
		return les;
	}
	
	@LogAnnotation
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除用户")
	public void delete(@PathVariable Long id) {
		sectionService.delSection(id);
	}

	@GetMapping("/area")
	@ApiOperation(value = "区划")
	public List<Map<String, Object>> areaAll() {
		List<Lesection> areaAll = lesDao.areaAll();
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(Lesection per:areaAll){
			map=new HashMap<>();
			map.put("id",per.getId());
			map.put("parentId",per.getParent_id());
			map.put("text", per.getName());
			listMap.add(map);
		}
		List<Map<String,Object>> retMap=AppUtil.list2Tree(listMap, "parentId", "id",null);
		
		return retMap;
	}
	
	@GetMapping("/areamgl")
	@ApiOperation(value = "区划蒙文")
	public List<Map<String, Object>> areaAllMgl() {
		List<Lesection> areaAll = lesDao.areaAll();
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(Lesection per:areaAll){
			map=new HashMap<>();
			map.put("id",per.getId());
			map.put("parentId",per.getParent_id());
			map.put("text", per.getMgl_name());
			listMap.add(map);
		}
		List<Map<String,Object>> retMap=AppUtil.list2Tree(listMap, "parentId", "id",null);
		
		return retMap;
	}
}
