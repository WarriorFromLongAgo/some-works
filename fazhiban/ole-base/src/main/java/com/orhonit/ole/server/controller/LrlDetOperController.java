package com.orhonit.ole.server.controller;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.orhonit.ole.server.Utils.SerializerUtils;
import com.orhonit.ole.server.dao.LrlAttDao;
import com.orhonit.ole.server.dao.LrlDetAttDao;
import com.orhonit.ole.server.model.LarAtt;
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
 * 关联法律法规相关操作控制器
 * 
 * @author caodw
 *
 */
@Slf4j(topic = "adminLogger")
@RestController
@RequestMapping("/lrlDettionws")
public class LrlDetOperController {
	
	private static final String SERIAL_PREFIX = "FL_ITEM_";
	
	SeqSerializerNum str;

	@Autowired
	private SeqNumRepository seqNumRepository;

	@Autowired
	private SectionService sectionService;
	@Autowired
	private LrlDetAttDao lrlDetAttDao;

	@GetMapping
	@ApiOperation(value = "法律列表")
	public TableResponse<LrlDetAtt> list(TableRequest request) {
		return TableRequestHandler.<LrlDetAtt> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				int result=lrlDetAttDao.count(request.getParams());
				return result;
			}
		}).listHandler(new ListHandler<LrlDetAtt>() {

			@Override
			public List<LrlDetAtt> list(TableRequest request) {
				return lrlDetAttDao.list(request.getParams(), request.getStart(), request.getLength());
			}
		}).build().handle(request);
	}
	
	@LogAnnotation
	@PostMapping
	@ApiOperation(value = "保存地区")
	public int saveLrl(@RequestBody LrlDetAtt lrlDetAtt) {
		User user=UserUtil.getCurrentUser();
		lrlDetAtt.setCreate_name(user.getNickname());
		lrlDetAtt.setCreate_by(user.getUsername());
		lrlDetAtt.setCreate_date(new Date());
		lrlDetAtt.setPublish_date(new Date());
		seqNumRepository.save(str);
		return lrlDetAttDao.save(lrlDetAtt);
	}

	@LogAnnotation
	@PutMapping
	@ApiOperation(value = "修改用户")
	public void updateUser(@RequestBody LrlDetAtt lrlDetAtt) {
		User user=UserUtil.getCurrentUser();
		lrlDetAtt.setUpdate_name(user.getNickname());
		lrlDetAtt.setUpdate_by(user.getUsername());
		lrlDetAtt.setUpdate_date(new Date());
		lrlDetAttDao.updateLrl(lrlDetAtt);
	}

	@ApiOperation(value = "根据法律法规")
	@GetMapping("/ishave/{id}")
	public LrlAtt getDetLrls(@PathVariable String id) {
		List<LrlAtt> ltcs=lrlDetAttDao.getPDLtc(id);
		
		String text="";
		if(ltcs.size()>0){
			String sectxt="已关联法律相：";
			for(LrlAtt lesec:ltcs){
				sectxt="  "+sectxt+lesec.getName()+",";
			}
			 text=sectxt.substring(sectxt.length()-1, sectxt.length());
		}
		LrlAtt les=new LrlAtt();
		les.setName(text);;
		return les;
	}

	@ApiOperation(value = "当前登录用户")
	@GetMapping("/current")
	public User currentUser() {
		return UserUtil.getCurrentUser();
	}

	@ApiOperation(value = "根据法律法规")
	@GetMapping("/{id}")
	public LrlDetAtt getDetLrl(@PathVariable String id) {
		return lrlDetAttDao.getDetLrl(id);
	}
	
	@LogAnnotation
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除用户")
	public void delete(@PathVariable Long id) {
		lrlDetAttDao.delete(id);
	}
	
	@GetMapping("getSerialNum")
	public String getSerialNum() {
		str = seqNumRepository.findOne("law_id_item_seq");
		String lastSerializer = str.getSeqValue();
		SerializerUtils utils = new SerializerUtils();
		String num = utils.getMoveOrderNo(lastSerializer, SERIAL_PREFIX,8,0);
		str.setSeqValue(num.substring(9));
		return num;
	}
	
}
