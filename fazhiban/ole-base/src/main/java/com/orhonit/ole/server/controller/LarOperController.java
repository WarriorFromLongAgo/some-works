package com.orhonit.ole.server.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orhonit.ole.common.annotation.LogAnnotation;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.utils.FileUtil;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.server.Utils.SerializerUtils;
import com.orhonit.ole.server.dao.LarDao;
import com.orhonit.ole.server.model.LarAtt;
import com.orhonit.ole.server.model.LarAttHi;
import com.orhonit.ole.server.model.LarConAtt;
import com.orhonit.ole.server.model.LrlAtt;
import com.orhonit.ole.server.model.LrlDetAtt;
import com.orhonit.ole.server.model.LtcAtt;
import com.orhonit.ole.server.model.SeqSerializerNum;
import com.orhonit.ole.server.repository.SeqNumRepository;
import com.orhonit.ole.sys.model.FileInfo;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 权责清单相关操作控制器
 * 
 * @author caodw
 *
 */
@Slf4j(topic = "adminLogger")
@RestController
@RequestMapping("/lartions")
public class LarOperController {
	
	private static final String SERIAL_PREFIX = "QZ_15";
	
	SeqSerializerNum str;

	@Autowired
	private LarDao larDao;
	
	@Autowired
	private SeqNumRepository seqNumRepository;
	
	@Value("${files.potencePath}")
	private String filesPath;
	
	@Value("${upload.serverUrl}")
	private String serverUrl;

	@LogAnnotation
	@PostMapping
	@ApiOperation(value = "保存权责")
	public int saveLar(@RequestBody LarAtt larAtt) {
		User user=UserUtil.getCurrentUser();
		larAtt.setCreate_name(user.getNickname());
		larAtt.setCreate_by(user.getUsername());
		larAtt.setCreate_date(new Date());
		larAtt.setId(UUID.randomUUID().toString());
		larAtt.setDel_flag("0");
		saveHi(larAtt);
		seqNumRepository.save(str);
		return larDao.save(larAtt);
	}
	
	/**
	 * 保存执法流程图
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@LogAnnotation
	@PostMapping("/potenceIMG")
	@ApiOperation(value = "文件上传")
	public Map<String, String> uploadFile(MultipartFile file) throws IOException {
		Map<String,String> res = new HashMap<String,String>();
		String fileOrigName = file.getOriginalFilename();
		fileOrigName = fileOrigName.substring(fileOrigName.lastIndexOf("."));
		String md5 = FileUtil.fileMd5(file.getInputStream());
		String pathname = "/"+md5+System.currentTimeMillis()+fileOrigName;
		res.put("processId", pathname);
		String fullPath = filesPath + pathname;
		res.put("url", serverUrl+"file/"+filesPath.split("/")[filesPath.split("/").length-1]+pathname);
		FileUtil.saveFile(file, fullPath);
		return res;
	}
	
	private void saveHi(LarAtt larAtt){
		LarAttHi larAttHi = new LarAttHi();
		BeanUtils.copyProperties(larAtt, larAttHi);
		larAttHi.setPotence_id(larAtt.getId());
		larAttHi.setId(null);
		larDao.saveHi(larAttHi);
	}
	
	@LogAnnotation
	@ApiOperation(value = "保存权责关联")
	@RequestMapping("/saveCon")
	public void saveConLar(@RequestBody LarConAtt larAtt) {
		List<LarAtt> lists=larDao.sameConn(larAtt.getRight_duty_id(), larAtt.getWz_catalog_id(), larAtt.getWz_content(), larAtt.getFz_catalog_id(),larAtt.getFz_content());
		if(lists.size()>0){
			for(LarAtt larone:lists){
				larDao.deleteCon(Long.valueOf(larone.getId()));
			}
		}
		User user=UserUtil.getCurrentUser();
		larAtt.setCreate_name(user.getNickname());
		larAtt.setCreate_by(user.getUsername());
		larAtt.setCreate_date(new Date());
		larAtt.setUpdate_by(user.getUsername());
		larAtt.setUpdate_date(new Date());
		larAtt.setUpdate_name(user.getNickname());
		larDao.deleteCon(larAtt.getId());
		larDao.saveCon(larAtt);
	}
	
	@ApiOperation(value = "根据法律法规")
	@GetMapping("/ishave/{id}")
	public LarAtt getDetLrl(@PathVariable String id) {
		List<LarAtt> ltcs=larDao.getPLar(id);
		List<LrlAtt> lepcs=larDao.getlLar(id);
		List<LrlDetAtt> ltcons=larDao.getilLar(id);
		List<LtcAtt> atts=larDao.getPDLtc(id);
		
		String text="";
		if(ltcs.size()>0){
			String sectxt="关联子权责：";
			for(LarAtt lesec:ltcs){
				sectxt=sectxt+lesec.getName()+",";
			}
			 text=sectxt.substring(0,sectxt.length()-1);
		}
        if(lepcs.size()>0){
        	String ltctxt="  关联法律项：";
            for(LrlAtt latt:lepcs){
            	ltctxt=ltctxt+latt.getName()+",";
			}
            text=text+" "+ltctxt.substring(0,ltctxt.length()-1);
		}
        if(ltcons.size()>0){
        	String ltctxt="  关联法律子项：";
            for(LrlDetAtt latt:ltcons){
            	ltctxt=ltctxt+latt.getItem_name()+",";
			}
            text=text+" "+ltctxt.substring(0,ltctxt.length()-1);
		}
        if(atts.size()>0){
        	String ltctxts="  权责关联的主体：";
            for(LtcAtt latt:atts){
            	ltctxts=ltctxts+latt.getName()+",";
			}
            text=text+ltctxts.substring(0,ltctxts.length()-1);
		}
       
        LarAtt les=new LarAtt();
		les.setName(text);
		return les;
	}


	@LogAnnotation
	@PutMapping
	@ApiOperation(value = "修改权责")
	public void updateLar(@RequestBody LarAtt larAtt) {
		User user=UserUtil.getCurrentUser();
		larAtt.setUpdate_name(user.getNickname());
		larAtt.setUpdate_by(user.getUsername());
		larAtt.setUpdate_date(new Date());
		
		LarAtt larAttTemp= larDao.getLar(larAtt.getId());
		larAtt.setCreate_by(larAttTemp.getCreate_by());
		larAtt.setCreate_date(larAttTemp.getCreate_date());
		larAtt.setCreate_name(larAttTemp.getCreate_name());
		larAtt.setDel_flag(larAttTemp.getDel_flag());
		larDao.updateLar(larAtt);
		saveHi(larAtt);
	}


	@ApiOperation(value = "当前登录用户")
	@GetMapping("/current")
	public User currentUser() {
		return UserUtil.getCurrentUser();
	}

	@ApiOperation(value = "根据id权责信息")
	@GetMapping("/{id}")
	public LarAtt getLar(@PathVariable String id) {
		LarAtt larAtt = larDao.getLar(id);
		if(larAtt != null && larAtt.getProcess_id() != null && !larAtt.getProcess_id().equals("")) {
			larAtt.setShowUrl(serverUrl+"file/"+filesPath.split("/")[filesPath.split("/").length-1]+larAtt.getProcess_id());
		}
		return larAtt;
	}
	
	@GetMapping("/getCons")
	@RequiresPermissions(value = "sys:log:query")
	@ApiOperation(value = "主体列表")
	public TableResponse<LarConAtt> list(TableRequest request) {
		return TableRequestHandler.<LarConAtt> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return larDao.conncount(request.getParams());
			}
		}).listHandler(new ListHandler<LarConAtt>() {
			@Override
			public List<LarConAtt> list(TableRequest request) {
				String id=request.getParams().get("id").toString();
				List<LarConAtt> lists=larDao.getConLar(id);
				LarAtt lar=larDao.getLar(String.valueOf(id));
				//List<LrlAtt> lawLists=larDao.getLaws();
				//List<LrlDetAtt> lawItemLists=larDao.getLawsItem();
				if(lists.size()>0){
					String[] itemStr = {"篇","章","节","条","款","项","目"};
					for(LarConAtt att:lists){
						att.setRight_duty_id(lar.getName());
						String[] wzitem = att.getWz_item().split("-");
						String wzItemStr = "";
						for(int i=0;i<wzitem.length;i++) {
							if(!wzitem[i].equals("0")) {
								wzItemStr += "第"+wzitem[i]+itemStr[i];
							}
						}
						att.setWz_item(wzItemStr);
						String[] fzitem = att.getFz_item().split("-");
						String fzItemStr = "";
						for(int i=0;i<fzitem.length;i++) {
							if(!wzitem[i].equals("0")) {
								fzItemStr += "第"+fzitem[i]+itemStr[i];
							}
						}
						att.setFz_item(fzItemStr);
						/*for(LrlAtt lrlatt:lawLists){
							if(String.valueOf(lrlatt.getId()).equals(att.getWz_catalog_id())){
								att.setWz_catalog_id(lrlatt.getName());
							}
							if(String.valueOf(lrlatt.getId()).equals(att.getFz_catalog_id())){
								att.setFz_catalog_id(lrlatt.getName());
							}
						  }*/
						/*for(LrlDetAtt lrlItematt:lawItemLists){
								if(String.valueOf(lrlItematt.getId()).equals(att.getWz_catalog_id())){
									att.setWz_catalog_id(lrlItematt.getItem_name());
								}
								if(String.valueOf(lrlItematt.getId()).equals(att.getFz_catalog_id())){
									att.setFz_catalog_id(lrlItematt.getItem_name());
								}
						  }*/
					}
				}
				return lists;
			}
		}).build().handle(request);
	}
	
	@ApiOperation(value = "根据用户id获取用户")
	@GetMapping("/getCon/{id}")
	public LarConAtt getConLar(@PathVariable String id) {
		LarConAtt lar=larDao.getLarconn(id);
		return lar;
	}
	
	@LogAnnotation
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除用户")
	public void delete(@PathVariable String id) {
		User user=UserUtil.getCurrentUser();
		LarAtt larAtt = larDao.getLar(id);
		larAtt.setUpdate_name(user.getNickname());
		larAtt.setUpdate_by(user.getUsername());
		larAtt.setUpdate_date(new Date());
		larAtt.setDel_flag("1");
		larDao.updateLar(larAtt);
		saveHi(larAtt);
	}
	
	@GetMapping("getSerialNum")
	public String getSerialNum() {
		str = seqNumRepository.findOne("potence_id_seq");
		String lastSerializer = str.getSeqValue();
		SerializerUtils utils = new SerializerUtils();
		String num = utils.getMoveOrderNo(lastSerializer, SERIAL_PREFIX,8,1);
		str.setSeqValue(num.substring(6));
		return num;
	}
}
