package com.orhonit.ole.server.controller;

import java.util.Date;
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
import com.orhonit.ole.server.Utils.SerializerUtils;
import com.orhonit.ole.server.dao.LepDao;
import com.orhonit.ole.server.model.Lepeson;
import com.orhonit.ole.server.model.LepesonHi;
import com.orhonit.ole.server.model.SeqSerializerNum;
import com.orhonit.ole.server.repository.SeqNumRepository;
import com.orhonit.ole.server.service.SectionService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.UserService;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 执法人员相关操作控制器
 * 
 * @author caodw
 *
 */
@Slf4j(topic = "adminLogger")
@RestController
@RequestMapping("/leptions")
public class LepOperController {
	
	private static final String SERIAL_PREFIX = "15";
	
	SeqSerializerNum str;

	@Autowired
	private SeqNumRepository seqNumRepository;
	@Autowired
	private UserService userService;

	@Autowired
	private LepDao lepDao;

	@LogAnnotation
	@PostMapping
	@ApiOperation(value = "保存执法人")
	public int saveLep(@RequestBody Lepeson lepeson) {
		User u = userService.getUser(lepeson.getCert_num());
		if (u != null) {
			throw new IllegalArgumentException(lepeson.getCert_num() + "已存在");
		}
		User user=UserUtil.getCurrentUser();
		lepeson.setCreate_name(user.getNickname());
		lepeson.setCreate_by(user.getUsername());
		lepeson.setCreate_date(new Date());
		lepeson.setOther_date(new Date());
		lepeson.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		lepeson.setDel_flag("0");
		seqNumRepository.save(str);
		saveHi(lepeson);
		return lepDao.save(lepeson);
	}
	
	private void saveHi(Lepeson lepeson){
		LepesonHi lepesonHi = new LepesonHi();
		if(lepeson == null){
			return ;
		}
		
		lepesonHi.setPerson_id(lepeson.getId());
		lepesonHi.setCode(lepeson.getCode());
		lepesonHi.setName(lepeson.getName());
		lepesonHi.setSex(lepeson.getSex());
		lepesonHi.setNation(lepeson.getNation());
		lepesonHi.setTel(lepeson.getTel());
		lepesonHi.setPolitical(lepeson.getPolitical());
		lepesonHi.setBirthday(lepeson.getBirthday());
		lepesonHi.setEdu(lepeson.getEdu());
		lepesonHi.setCard_num(lepeson.getCard_num());
		lepesonHi.setPicture(lepeson.getPicture());
		lepesonHi.setDuty(lepeson.getDuty());
		lepesonHi.setDept_id(lepeson.getDept_id());
		lepesonHi.setCert_num(lepeson.getCert_num());
		lepesonHi.setLawarea(lepeson.getLawarea());
		lepesonHi.setCert_type(lepeson.getCert_type());
		lepesonHi.setCert_category(lepeson.getCert_category());
		lepesonHi.setCert_auth(lepeson.getCert_auth());
		lepesonHi.setCert_time(lepeson.getCert_time());
		lepesonHi.setCert_term(lepeson.getCert_term());
		lepesonHi.setLaw_type(lepeson.getLaw_type());
		lepesonHi.setIf_effect(lepeson.getIf_effect());
		lepesonHi.setDel_flag(lepeson.getDel_flag());
		lepesonHi.setCreate_by(lepeson.getCreate_by());
		lepesonHi.setCreate_date(lepeson.getCreate_date());
		lepesonHi.setCreate_name(lepeson.getCreate_name());
		lepesonHi.setUpdate_by(lepeson.getUpdate_by());
		lepesonHi.setUpdate_date(lepeson.getUpdate_date());
		lepesonHi.setUpdate_name(lepeson.getUpdate_name());
		lepesonHi.setMgl_create_name(lepeson.getMgl_create_name());
		lepesonHi.setMgl_name(lepeson.getMgl_name());
		lepesonHi.setMgl_update_name(lepeson.getMgl_update_name());
		lepesonHi.setIs_deal("0");
		lepesonHi.setOther_date(lepeson.getOther_date());
		
		lepDao.saveHi(lepesonHi);
	}

	@LogAnnotation
	@PutMapping
	@ApiOperation(value = "修改执法人")
	public void updateLep(@RequestBody Lepeson userDto) {
		/*User u = userService.getUser(userDto.getCert_num());
		if (u != null) {
			throw new IllegalArgumentException(userDto.getCert_num() + "已存在");
		}*/
		User user=UserUtil.getCurrentUser();
		userDto.setUpdate_name(user.getNickname());
		userDto.setUpdate_by(user.getUsername());
		userDto.setUpdate_date(new Date());
		Lepeson lepeson = lepDao.getLep(userDto.getId());
		userDto.setCreate_by(lepeson.getCreate_by());
		userDto.setCreate_date(lepeson.getCreate_date());
		userDto.setCreate_name(lepeson.getCreate_name());
		userDto.setDel_flag(lepeson.getDel_flag());
		saveHi(lepeson);
		lepDao.updateLep(userDto);
	}


	@ApiOperation(value = "当前登录用户")
	@GetMapping("/current")
	public User currentUser() {
		return UserUtil.getCurrentUser();
	}

	@ApiOperation(value = "根据用户id获取执法人")
	@GetMapping("/{id}")
	public Lepeson getLep(@PathVariable String id) {
		return lepDao.getLep(id);
	}
	
	@ApiOperation(value = "根据用户id获取执法人")
	@GetMapping("/getuser")
	public User getUser(@PathVariable String id) {
		User user=UserUtil.getCurrentUser();
		return user;
	}
	
	@LogAnnotation
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除执法人")
	public void delete(@PathVariable String id) {
		Lepeson lepeson = lepDao.getLep(id);
		lepeson.setDel_flag("1");
		saveHi(lepeson);
		lepDao.updateLep(lepeson);
	}

	@GetMapping("getSerialNum")
	public String getSerialNum() {
		str = seqNumRepository.findOne("people_id_seq");
		String lastSerializer = str.getSeqValue();
		SerializerUtils utils = new SerializerUtils();
		String num = utils.getMoveOrderNo(lastSerializer, SERIAL_PREFIX,12,0);
		str.setSeqValue(num.substring(3));
		return num;
	}
	
}
