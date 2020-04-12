package com.orhonit.modules.generator.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.orhonit.common.exception.RRException;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.entity.JointCompanyEntity;
import com.orhonit.modules.generator.service.JointCompanyService;
import com.orhonit.modules.generator.vo.JointCompanyVO;
import com.orhonit.modules.generator.vo.UserAndOrgVO;
import com.orhonit.modules.sys.entity.UserOrgEntity;
import com.orhonit.modules.sys.service.SysUserService;
import com.orhonit.modules.sys.service.UserOrgService;


/**
 * 
 * Title: JointCompanyController.java
 * @Description:   监督  联席单位
 * @author YaoSC
 * @date 2019年7月19日
 */
@RestController
@RequestMapping("joint/company")
public class JointCompanyController extends AbstractController{
	
	 @Autowired
	 JointCompanyService jointCompanyService;
	 @Autowired
	 UploadConfig uploadConfig;
	 @Autowired
	 UserOrgService userOrgService;
	 @Autowired
	 SysUserService sysUserService; 
	 //联席单位
	 static final Integer LIANXI_DANWEEI=1;
	
	 /**
	  * 保存
	  * @param t
	  * @return
	  */
	 @PostMapping("/save")
	 public R save(@RequestBody JointCompanyEntity t) {
		 t.setCreateTime(new Date());
		 t.setUpdateTime(new Date());
		 jointCompanyService.insert(t);
		 return  R.ok();
	 }
	 
	 /**
	  * 列表
	  * @param params
	  * @return
	  */
	 @GetMapping("/list")
	 public R list(@RequestParam Map<String, Object> params) {
		 PageUtils page = jointCompanyService.jointqueryPage(params);
		  return R.ok().put("page", page);
	 }
	 
	 /**
	   * 联席单位 详情
	  * @param id
	  * @param name
	  * @return
	  */
	 @GetMapping("/info/{id}")
     public R info( @PathVariable("id")Integer id) {
		 JointCompanyVO entity=new JointCompanyVO();
		 if(id>0) {
			  entity =jointCompanyService.getOneJoint(id);
		 }else {
			return R.parameterIsNul();
		 }
        return R.ok().put("JointCompanyEntity", entity);
	 }
	 
	 /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") Integer id) {
    	JointCompanyEntity jointCompanyEntity = new JointCompanyEntity();
    	jointCompanyEntity.setIsDel(CommonParameters.isDel.IS_DEL_YES);
    	jointCompanyEntity.setUpdateTime(new Date());
    	jointCompanyService.update(jointCompanyEntity, new EntityWrapper<JointCompanyEntity>().eq("id", id));
        return R.ok().put("delete", "删除成功!");
    }
    
    /**
     * 修改
     * @param t
     * @return
     */
    @PutMapping("/update")
    public R update(@RequestBody JointCompanyEntity t ) {
    	t.setUpdateTime(new Date());
    	jointCompanyService.updateById(t);
    	return R.ok().put("update", "更新成功!");
    }
    
    /**
	 * 上传文件
	 */
	@PostMapping("/upload")
	public R upload(@RequestParam("file") MultipartFile file,String type)
			throws Exception {
		//Encoder encoder = new Encoder();
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}
		// 上传文件 后缀
		 String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		//新文件名
		String newFileName =  System.currentTimeMillis() + suffix;
		Date date = new Date();
		SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMdd");
		String dateStr = dformat.format(date);
		String filePath = uploadConfig.getPath() + dateStr + "//";
		String md5=DigestUtils.md5Hex(file.getBytes());
		System.out.println("md5值为"+md5);
		try {
			// 创建目录
			File dir = new File(filePath);
			dir.mkdirs();
			// 获取文件输入流
			InputStream in = file.getInputStream();
			// 创建文件并保存
			File f = new File(filePath + newFileName);
			FileOutputStream fos = new FileOutputStream(f);
			byte[] b = new byte[1024];
			int n = 0;
			while ((n = in.read(b)) != -1) {
				fos.write(b, 0, n);
			}
			fos.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return R.error();
		}
		return R.ok().put("url", "http://110.19.104.227:20020/api/image/" + dateStr + "/" + newFileName);
		//return R.ok().put("url", "http://192.168.124.9:8002/api/image/" + dateStr + "/" + newFileName).put("type:", type);
	}
	
	
	/**
	 * 所有共享单位
	 * @return
	 */
	@GetMapping("/getOrgnList")
	public R getOrgn() {
		List<UserOrgEntity>list=userOrgService.selectList(new EntityWrapper<UserOrgEntity>().eq("org_is_l", JointCompanyController.LIANXI_DANWEEI));
		return R.ok().put("list", list);
	}
	
	
	/**
	  * 个人信息以及所在单位
	 * @param userTrueName  真实姓名
	 * @param userNickname  身份证号
	 * @param userId  用户ID
	 * @param mobile  手机号
	 * @return
	 */
	@GetMapping("/getUserList")
	public R getInfoList(@RequestParam Map<String,Object>params) {
		params.put("userId", getUserId());
		return R.ok().put("list",jointCompanyService.userList(params));
	}
}
