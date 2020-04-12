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
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.server.Utils.SerializerUtils;
import com.orhonit.ole.server.dao.LepDao;
import com.orhonit.ole.server.dao.LtcAttDao;
import com.orhonit.ole.server.model.LarAtt;
import com.orhonit.ole.server.model.Lepeson;
import com.orhonit.ole.server.model.LtcAtt;
import com.orhonit.ole.server.model.LtcAttHi;
import com.orhonit.ole.server.model.LtcConnAtt;
import com.orhonit.ole.server.model.SeqSerializerNum;
import com.orhonit.ole.server.repository.SeqNumRepository;
import com.orhonit.ole.server.service.LtcAttService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 执法主体相关操作控制器
 * 
 * @author caodw
 *
 */
@Slf4j(topic = "adminLogger")
@RestController
@RequestMapping("/ltctions")
public class LtcOperController {
	
	private static final String SERIAL_PREFIX = "15";
	
	SeqSerializerNum str;

	@Autowired
	private SeqNumRepository seqNumRepository;

	@Autowired
	private LtcAttService ltcAttService;
	
	@Autowired
	private LtcAttDao ltcAttDao;
	
	@Autowired
	private LepDao lepDao;

	@LogAnnotation
	@PostMapping
	@ApiOperation(value = "保存主体")
	public void saveLtc(@RequestBody LtcAtt ltcAtt) {
		User user=UserUtil.getCurrentUser();
		ltcAtt.setCreate_name(user.getNickname());
		ltcAtt.setCreate_by(user.getUsername());
		ltcAtt.setCreate_date(new Date());
		ltcAtt.setDel_flag("0");
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		ltcAtt.setId(uuid);
		seqNumRepository.save(str);
		ltcAttDao.save(ltcAtt);
		saveHi(ltcAtt);
	}
	
	private void saveHi(LtcAtt ltcAtt){
		if(ltcAtt == null){
			return ;
		}
		
		LtcAttHi ltcAttHi = new LtcAttHi();
		ltcAttHi.setDept_id(ltcAtt.getId());
		ltcAttHi.setCode(ltcAtt.getCode());
		ltcAttHi.setName_spell(ltcAtt.getName_spell());
		ltcAttHi.setAddress(ltcAtt.getAddress());
		ltcAttHi.setLegal_person(ltcAtt.getLegal_person());
		ltcAttHi.setLevel(ltcAtt.getLevel());
		ltcAttHi.setDept_property(ltcAtt.getDept_property());
		ltcAttHi.setParent_id(ltcAtt.getParent_id());
		ltcAttHi.setSort(ltcAtt.getSort());
		ltcAttHi.setLaw_type(ltcAtt.getLaw_type());
		ltcAttHi.setTel(ltcAtt.getTel());
		ltcAttHi.setArea_id(ltcAtt.getArea_id());
		ltcAttHi.setLawarea(ltcAtt.getLawarea());
		ltcAttHi.setIf_effect(ltcAtt.getIf_effect());
		ltcAttHi.setDel_flag(ltcAtt.getDel_flag());
		ltcAttHi.setCreate_by(ltcAtt.getCreate_by());
		ltcAttHi.setCreate_date(ltcAtt.getCreate_date());
		ltcAttHi.setCreate_name(ltcAtt.getCreate_name());
		ltcAttHi.setUpdate_by(ltcAtt.getUpdate_by());
		ltcAttHi.setUpdate_date(ltcAtt.getUpdate_date());
		ltcAttHi.setUpdate_name(ltcAtt.getUpdate_name());
		ltcAttHi.setShort_name(ltcAtt.getShort_name());
		ltcAttHi.setName(ltcAtt.getName());
		ltcAttHi.setIs_ps(ltcAtt.getIs_ps());
		ltcAttHi.setMgl_address(ltcAtt.getMgl_address());
		ltcAttHi.setMgl_name(ltcAtt.getMgl_name());
		ltcAttHi.setMgl_short_name(ltcAtt.getMgl_short_name());
		ltcAttHi.setMgl_legal_person(ltcAtt.getMgl_legal_person());
		ltcAttHi.setMgl_create_name(ltcAtt.getMgl_create_name());
		ltcAttHi.setMgl_update_name(ltcAtt.getMgl_update_name());
		ltcAttHi.setIs_deal("0");
		
		ltcAttDao.saveHi(ltcAttHi);
	}

	@LogAnnotation
	@PutMapping
	@ApiOperation(value = "修改主体")
	public void updateUser(@RequestBody LtcAtt ltcAtt) {
		User user=UserUtil.getCurrentUser();
		ltcAtt.setUpdate_name(user.getNickname());
		ltcAtt.setUpdate_by(user.getUsername());
		ltcAtt.setUpdate_date(new Date());
		
		LtcAtt ltc = ltcAttDao.getLtc(ltcAtt.getId());
		ltcAtt.setCreate_by(ltc.getCreate_by());
		ltcAtt.setCreate_date(ltc.getCreate_date());
		ltcAtt.setCreate_name(ltc.getCreate_name());
		ltcAtt.setDel_flag(ltc.getDel_flag());
		saveHi(ltcAtt);
		ltcAttService.updateLtc(ltcAtt);
	}
	
	@ApiOperation(value = "根据法律法规")
	@GetMapping("/ishave/{id}")
	public LtcAtt getDetLrl(@PathVariable String id) {
		List<LtcAtt> ltcs=ltcAttDao.getPLtc(id);
		List<Lepeson> lepcs=ltcAttDao.getDLtc(id);
		List<LarAtt> ltcons=ltcAttDao.getPDLtc(id);
		
		String text="";
		if(ltcs.size()>0){
			String sectxt="  关联子主体：";
			for(LtcAtt lesec:ltcs){
				sectxt="  "+sectxt+lesec.getName()+",";
			}
			 text=sectxt.substring(0, sectxt.length()-1);
		}
        if(lepcs.size()>0){
        	String ltctxt="  关联执法人：";
            for(Lepeson latt:lepcs){
            	ltctxt="  "+ltctxt+latt.getName()+",";
			}
            text=text+" "+ltctxt.substring(0, ltctxt.length()-1);
		}
        if(ltcons.size()>0){
        	String ltctxt="  关联权责：";
            for(LarAtt latt:ltcons){
            	ltctxt="  "+ltctxt+latt.getName()+",";
			}
            text=text+" "+ltctxt.substring(0, ltctxt.length()-1);
		}
       
        LtcAtt les=new LtcAtt();
		les.setName(text);
		return les;
	}
	
	@LogAnnotation
	@PutMapping("/conn")
	@ApiOperation(value = "关联")
	public void updateConn(@RequestBody LtcConnAtt ltcAtt) {
		User user=UserUtil.getCurrentUser();
		ltcAttDao.deleteCon(String.valueOf(ltcAtt.getId()));
		
		List<LtcAtt> listconns=ltcAttDao.sameConn(ltcAtt.getPotence_id(),ltcAtt.getDept_id(),ltcAtt.getDept_id_agent());
		if(listconns.size()<1){
			ltcAtt.setCreate_name(user.getNickname());
			ltcAtt.setCreate_by(user.getUsername());
			ltcAtt.setCreate_date(new Date());
			ltcAtt.setUpdate_name(user.getNickname());
			ltcAtt.setUpdate_by(user.getUsername());
			ltcAtt.setUpdate_date(new Date());
			ltcAttDao.saveConn(ltcAtt);
		}
	}


	@ApiOperation(value = "当前登录用户")
	@GetMapping("/current")
	public User currentUser() {
		return UserUtil.getCurrentUser();
	}

	@ApiOperation(value = "根据用户id获取用户")
	@GetMapping("/{id}")
	public LtcAtt getLtc(@PathVariable String id) {
		return ltcAttDao.getLtc(id);
	}
	
	@GetMapping("/conns")
	@ApiOperation(value = "主体列表")
	public TableResponse<LtcConnAtt> conlist(TableRequest request) {
		return TableRequestHandler.<LtcConnAtt> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
					return ltcAttDao.conncount(request.getParams());
			}
			}).listHandler(new ListHandler<LtcConnAtt>() {
				@Override
				public List<LtcConnAtt> list(TableRequest request) {
					request.getParams().put("start", request.getStart());
					request.getParams().put("length", request.getLength());
					List<LtcConnAtt> lists=ltcAttDao.getConnLtcs(request.getParams());
					//List<LtcAtt> listss=ltcAttDao.getAllLtcs();
					//List<LarAtt> larsList=ltcAttDao.getAllLars();
					return lists;
				}
			}).build().handle(request);
		
	}
	
	@ApiOperation(value = "根据用户id获取用户")
	@GetMapping("/connss/{id}")
	public LtcConnAtt getConnLtcs(@PathVariable String id) {
		LtcConnAtt lists=ltcAttDao.getConnLtcss(id);
		return lists;
	}
	
	@LogAnnotation
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除主体")
	public void delete(@PathVariable String id) {
		LtcAtt ltcAtt = ltcAttDao.getLtc(id);
		ltcAtt.setDel_flag("1");
		saveHi(ltcAtt);
		ltcAttService.updateLtc(ltcAtt);
	}
	
	@LogAnnotation
	@DeleteMapping("/deleteConn/{id}")
	@ApiOperation(value = "删除主体")
	public void deleteConn(@PathVariable String id) {
		ltcAttDao.deleteConn(id);
	}

	@GetMapping("getSerialNum")
	public String getSerialNum() {
		str = seqNumRepository.findOne("dept_id_seq");
		String lastSerializer = str.getSeqValue();
		SerializerUtils utils = new SerializerUtils();
		String num = utils.getMoveOrderNo(lastSerializer, SERIAL_PREFIX,10,0);
		str.setSeqValue(num.substring(3));
		return num;
	}
	
	@GetMapping("/deptTree/{areaId}")
	@ApiOperation(value = "主体划分")
	public List<Map<String, Object>> parentsTree(@PathVariable String areaId) {
		Map<String, Object> params = new HashMap<>();
		User user = UserUtil.getCurrentUser();
		LtcAtt ltcAtt = ltcAttDao.getLtc(user.getDept_id());
		params.put("dept_id", user.getDept_id());
		if(ltcAtt != null && ltcAtt.getDept_property() == 3 && ltcAtt.getLaw_type().equals("2")){
			//法制办
			//如果是市本级的法制办则显示所有
			if(!user.getArea_id().equals("15")){
				params.put("lx_type", 1);
			}
		}else if(user.getUsername().equals("admin")){
			//管理员不需要任何操作
		}
		else{
			//委办局
			params.put("lx_type", 2);
			params.put("deptIds", lepDao.execFunction(null, user.getDept_id()));
		}
		List<LtcAtt> parents = ltcAttDao.getDeptTree(params,areaId);
		List<Map<String,Object>> listMap=new ArrayList<>();
		Map<String,Object> map;
		for(LtcAtt ltc:parents){
			map=new HashMap<>();
			map.put("id",ltc.getId());		
			map.put("pId",ltc.getParent_id());
			map.put("name", ltc.getName());
			listMap.add(map);
		}
		return listMap;
	}
}
