package com.orhonit.modules.generator.app.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.orhonit.common.exception.RRException;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.app.entity.AppFsoulFileEntity;
import com.orhonit.modules.generator.app.entity.AppHelpMeDianZanEntity;
import com.orhonit.modules.generator.app.entity.ApphelpMeEntity;
import com.orhonit.modules.generator.app.service.AppFsoulFileService;
import com.orhonit.modules.generator.app.service.AppHelpMeDianZanService;
import com.orhonit.modules.generator.app.service.ApphelpMeService;
import com.orhonit.modules.sys.controller.AbstractController;


/**
 * Title: 帖子
 * @Description:
 * @author YaoSC
 * @date 2019年6月20日 
 */
@RestController
@RequestMapping("/app/help")
public class AppHelpMeController extends AbstractController{
	
	
	@Autowired
	ApphelpMeService service;
	@Autowired
	AppHelpMeDianZanService appHelpMeDianZanService;
	@Autowired	
    UploadConfig uploadConfig;
	@Autowired
	AppFsoulFileService fsFileService;
	
	/*
	 * 发帖
	 */
	@PostMapping("/save")
	public R save( @RequestBody HashMap<String, Object> maps) {
		//String l=request.getParameter("t");
		ApphelpMeEntity entity = JSONObject.parseObject(JSONObject.toJSONString(maps), ApphelpMeEntity.class);
		entity.setHelpCreateTime(new Date());
		entity.setHelpUpdateTime(new Date());
		entity.setHelpUserId(getUserId().intValue());
		service.insertHelpMe(entity);
		return R.ok();
	}
	
	/*
	 * 管理端
	 * 删除发帖信息
	 */
	@RequestMapping("/delete")
	public R delete( Integer helpMeId) {
		System.out.println(helpMeId);
		service.deleteId(helpMeId);
		return R.ok();
	}
	
	/*
	 * APP端 
	 * 帖子列表
	 * 
	 */
	  @GetMapping("/list") 
	  public R list(@RequestParam Map<String,Object>params) {
		  params.put("userId", getUserId());
		  PageUtils page = service.queryPage(params);
		  return R.ok().put("page", page);
	  }
	  
	  /*
	   * 修改
	   */
	  @RequestMapping("/update")
	  public R update(@RequestBody ApphelpMeEntity entity) {
		  entity.setHelpUpdateTime(new Date());
		  service.updateHelp(entity);
		  return R.ok();
	  }
	  
	  /**
	   * 帖子点赞
	   * @param entity
	   * @return
	   */
	  @RequestMapping("/dianzan")
	  public R dianzan(@RequestBody AppHelpMeDianZanEntity entity) {
		     entity.setHelpUserIdZan(getUserId().intValue());
			 String msg=  appHelpMeDianZanService.selectDianZan(entity);
			 return R.ok().put("msg", msg);
	  }
	  
	  /**
	   * 帖子  取消点赞
	   * @param entity
	   * @return
	   */
	  @RequestMapping("/quxiaodianzan")
	  public R quxiaodianzan(AppHelpMeDianZanEntity entity) {
		  entity.setHelpUserIdZan(getUserId().intValue());
		  String msg = appHelpMeDianZanService.quxiaodianzan(entity);
		  return R.ok().put("msg", msg);
	  }
	  
	  
	  /**
	      * 上传文件
	   * @param file
	   * @param ORGANIZATIONWORKSHOP
	   * @param fileType
	   * @return
	   * @throws Exception
	   */
	  @RequestMapping("/upload")
	  @ResponseBody
	  public R upload(@RequestParam("file") MultipartFile file,Integer Modular,Integer fileType) throws Exception {
			if (file.isEmpty()) {
				throw new RRException("上传文件不能为空");
			}
			//上传文件
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			String fileLocalName = System.currentTimeMillis() + "";
			Date date = new Date();
			SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMdd");
			String dateStr = dformat.format(date);
			//String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
			String filePath = uploadConfig.getPath() + dateStr + "//";
			try {
				// 创建目录
				File dir = new File(filePath);
				dir.mkdirs();
				// 获取文件输入流	
				InputStream in = file.getInputStream();
				// 创建文件并保存
				File f = new File(filePath + fileLocalName + suffix);
				FileOutputStream fos = new FileOutputStream(f);
				byte[] b = new byte[1024];
				int n = 0;
				while ((n = in.read(b)) != -1) {
					fos.write(b, 0, n);				
				}
				fos.close();
				in.close();
//				oele.save(omf);
			} catch (IOException e) {
				e.printStackTrace();
				return R.error();
			}
			//保存文件信息
			AppFsoulFileEntity soulFileEntity = new AppFsoulFileEntity();
			soulFileEntity.setFsoulFileId(UUID.randomUUID().toString().replaceAll("-",""));
			soulFileEntity.setFsoulFileUrl(filePath);
			soulFileEntity.setFsoulCreateTime(new Date());
			if(Modular==CommonParameters.ProjectModeules.PLEASS_HELP_ME) {
				soulFileEntity.setFsoulModularType(CommonParameters.ProjectModeules.PLEASS_HELP_ME);
			}
			soulFileEntity.setFsoulModularType(CommonParameters.ProjectModeules.IWITH_IDEAS_THE_TEAM);
			if(fileType==CommonParameters.AppEverBodyShareL.file_picture) {
				soulFileEntity.setFileType(CommonParameters.AppEverBodyShareL.file_picture);
			}else if(fileType==CommonParameters.AppEverBodyShareL.file_video){
				soulFileEntity.setFileType(CommonParameters.AppEverBodyShareL.file_video);
			}
			soulFileEntity.setFileType(CommonParameters.AppEverBodyShareL.file_other);
	        soulFileEntity.setFsoulOriginalFileName(file.getOriginalFilename());
			fsFileService.insert(soulFileEntity);
			return R.ok()
					.put("url","http://110.19.104.227:20020/api/image/" + dateStr + "/" + fileLocalName + suffix)
					.put("type", fileType);
	  }
}
