package com.orhonit.ole.server.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.annotation.LogAnnotation;
import com.orhonit.ole.common.utils.AppUtil;
import com.orhonit.ole.server.Utils.SerializerUtils;
import com.orhonit.ole.server.dao.LawLevelDao;
import com.orhonit.ole.server.dao.LrlAttDao;
import com.orhonit.ole.server.model.LarAtt;
import com.orhonit.ole.server.model.LawLevel;
import com.orhonit.ole.server.model.LrlAtt;
import com.orhonit.ole.server.model.LrlDetAtt;
import com.orhonit.ole.server.model.LtcAtt;
import com.orhonit.ole.server.model.SeqSerializerNum;
import com.orhonit.ole.server.repository.SeqNumRepository;
import com.orhonit.ole.server.service.SectionService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 法律法规相关操作控制器
 * 
 * @author caodw
 *
 */
@Slf4j(topic = "adminLogger")
@RestController
@RequestMapping("/lrltionws")
public class LrlOperController {
	
	private static final String SERIAL_PREFIX = "FL_15";
	
	SeqSerializerNum str;

	@Autowired
	private SectionService sectionService;
	@Autowired
	private LrlAttDao lrlAttDao;
	@Autowired
	private LawLevelDao lawLevelDao;
	@Autowired
	private SeqNumRepository seqNumRepository;

	@LogAnnotation
	@PostMapping
	@ApiOperation(value = "保存")
	public int saveLrl(@RequestBody LrlAtt lesection) {
		User user=UserUtil.getCurrentUser();
		lesection.setCreate_name(user.getNickname());
		lesection.setCreate_by(user.getUsername());
		lesection.setCreate_date(new Date());
		lesection.setPublish_date(new Date());
		seqNumRepository.save(str);
		return lrlAttDao.save(lesection);
	}

	@LogAnnotation
	@PutMapping
	@ApiOperation(value = "修改")
	public void updateUser(@RequestBody LrlAtt userDto) {
		User user=UserUtil.getCurrentUser();
		userDto.setUpdate_name(user.getNickname());
		userDto.setUpdate_by(user.getUsername());
		userDto.setUpdate_date(new Date());
		lrlAttDao.updateLrl(userDto);
	}


	@ApiOperation(value = "当前登录用户")
	@GetMapping("/current")
	public User currentUser() {
		return UserUtil.getCurrentUser();
	}

	@ApiOperation(value = "根据法律法规")
	@GetMapping("/{id}")
	public LrlAtt getLrl(@PathVariable String id) {
		LrlAtt att=lrlAttDao.getLrl(id);
		//LtcAtt ltc=lrlAttDao.getltc(att.getPub_dept());
		//att.setPub_dept(ltc.getName());
		return att;
	}
	
	@ApiOperation(value = "根据法律法规")
	@GetMapping("/ishave/{id}")
	public LrlAtt getDetLrl(@PathVariable String id) {
		List<LrlDetAtt> att=lrlAttDao.getDettLrl(id);
		List<LarAtt> ltcs=lrlAttDao.getPDLtc(id);
		
		String text="";
		if(att.size()>0){
			String sectxt="  已关法律子项：";
			for(LrlDetAtt lesec:att){
				sectxt="  "+sectxt+lesec.getItem_name()+",";
			}
			 text=sectxt.substring(0, sectxt.length()-1);
		}
        if(ltcs.size()>0){
        	String ltctxt="  已关联权责：";
            for(LarAtt latt:ltcs){
            	ltctxt="  "+ltctxt+latt.getName()+",";
			}
            text=text+" "+ltctxt.substring(0, ltctxt.length()-1);
		}
        LrlAtt les=new LrlAtt();
		les.setName(text);
		return les;
	}
	
	
	@LogAnnotation
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除用户")
	public void delete(@PathVariable Long id) {
		lrlAttDao.delete(id);
	}
	
	@LogAnnotation
	@DeleteMapping("/deleteConn/{id}")
	@ApiOperation(value = "删除用户")
	public void deleteConn(@PathVariable Long id) {
		lrlAttDao.deleteConn(id);
	}

	@GetMapping("/lrlss")
	@ApiOperation(value = "区划")
	public List<Map<String, Object>> areaAlls(@RequestParam(value = "name" , required = false) String name) {
		List<LrlAtt> areaAll = lrlAttDao.lawCateGoryByParam(name);
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(LrlAtt per:areaAll){
			map=new HashMap<>();
			map.put("id",per.getId());
			map.put("parentId","0");
			map.put("text", per.getName());
			listMap.add(map);
		}
		List<Map<String,Object>> retMap=AppUtil.list2Tree(listMap, "parentId", "id",null);
		return retMap;
	}
	
	@GetMapping("/lrlDets")
	@ApiOperation(value = "区划")
	public List<Map<String, Object>> detsAll() {
		List<LrlDetAtt> areaAll = lrlAttDao.lrlDetAll();
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(LrlDetAtt per:areaAll){
			map=new HashMap<>();
			map.put("id",per.getId());
			map.put("parentId",per.getParent_id());
			map.put("text", per.getItem_name());
			listMap.add(map);
		}
		List<Map<String,Object>> retMap=AppUtil.list2Tree(listMap, "parentId", "id",null);
		return retMap;
	}
	
	
	@GetMapping("getSerialNum")
	public String getSerialNum() {
//		SerializerUtils serializerUtils = new SerializerUtils();
//		String lastSerializer = "";
//		
//		List<LrlAtt> list = lrlAttDao.listSerializerNum();
//		
//		lastSerializer = list.get(0).getCode();
//
//		if(lastSerializer.equals("") || lastSerializer == null) {
//			lastSerializer = "00000000";
//		}
//		
//		return serializerUtils.getMoveOrderNo(lastSerializer,"FL_15");
		
		str = seqNumRepository.findOne("law_id_seq");
		String lastSerializer = str.getSeqValue();
		SerializerUtils utils = new SerializerUtils();
		String num = utils.getMoveOrderNo(lastSerializer, SERIAL_PREFIX,8,1);
		
		str.setSeqValue(num.substring(6));
		return num;
	}
	
	@GetMapping("/effectLevel")
	@ApiOperation(value = "法律分类")
	public List<Map<String, Object>> ltcAll() {
		List<LawLevel> list = lawLevelDao.getAllLawLevel();
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(LawLevel per:list){
			map=new HashMap<>();
			map.put("id",per.getId());
			map.put("parentId",per.getParentId());
			map.put("text", per.getName());
			listMap.add(map);
		}
		List<Map<String,Object>> retMap=AppUtil.list2Tree(listMap, "parentId", "id",null);
		return retMap;
	}

}
