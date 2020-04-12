package com.orhonit.modules.generator.app.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orhonit.common.exception.RRException;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.common.validator.ValidatorUtils;
import com.orhonit.common.validator.group.UpdateGroup;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.app.entity.AppFsoulFileEntity;
import com.orhonit.modules.generator.app.entity.AppWywNoteEntity;
import com.orhonit.modules.generator.app.service.AppFsoulFileService;
import com.orhonit.modules.generator.app.service.AppWywNoteService;
import com.orhonit.modules.sys.controller.AbstractController;
import com.orhonit.modules.sys.service.SysUserService;


/**
 * APP端捂一捂
 * @author C
 *
 */
@RestController
@RequestMapping("/app/wywnote")
public class AppWywNoteController extends AbstractController{
	
	@Autowired
	AppWywNoteService service;
	@Autowired	
    UploadConfig uploadConfig;
	@Autowired
	AppFsoulFileService fsFileService;
	@Autowired
	SysUserService sysUserService;
	
	
	/**
	 * 用户端录入一条笔记
	 * @param entity
	 * @return
	 */
	//@SysLog("保存笔记")
	@PostMapping("/saveAppwywnote")
	public R saveAppwywnote(@RequestBody AppWywNoteEntity entity) {
		if(null==entity.getNoteTitle()) {
			return R.ok().put("noteTitle", "不能为null或者是不能为空!");
		}else {
			entity.setCreateUserid(getUserId().intValue());
			entity.setCreateTime(new Date());
			entity.setUpdateTime(new Date());
			entity.setIsDel(CommonParameters.isDel.IS_DEL_NO);
			service.insert(entity);
		}
		return R.ok();
	}
	
	/**
	 * 查看笔记内容
	 * @param noteId
	 * @return
	 */
	@RequestMapping("/info/{noteId}")
	public R info(@PathVariable("noteId") Integer noteId) {
		return  R.ok().put("AppWywNoteEntity", service.getOneWyw(noteId));
	}
	
	/**
	  *  用户端笔记列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/AppwywnoteList")
	public R list(@RequestParam Map<String,Object>params) {
		params.put("createUserid", getUserId());
		PageUtils page =service.queryPage(params);
		
		return R.ok().put("page", page);
	}
	
	/**
	 * 笔记修改
	 * @param entity
	 * @return
	 */
	@RequestMapping("/update")
	public R update(@RequestBody AppWywNoteEntity entity ) {
		ValidatorUtils.validateEntity(entity, UpdateGroup.class);
		entity.setUpdateTime(new Date());
		service.updateById(entity);
		return  R.ok().put("storagePath", entity.getStoragePath());
	}
	
	
	/**
	 * 上传文件
	 */
	@SuppressWarnings("unlikely-arg-type")
	@PostMapping("/upload")
	//@RequiresPermissions("sys:oss:all")
	public R upload(@RequestParam("file") MultipartFile file,Integer ORGANIZATIONWORKSHOP,String fileType) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}
		int type = Integer.valueOf(fileType);
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
//			oele.save(omf);
		} catch (IOException e) {
			e.printStackTrace();
			return R.error();
		}
		//保存文件信息
		AppFsoulFileEntity soulFileEntity = new AppFsoulFileEntity();
		soulFileEntity.setFsoulFileId(UUID.randomUUID().toString().replaceAll("-",""));
		soulFileEntity.setFsoulFileUrl(filePath);
		soulFileEntity.setFsoulCreateTime(new Date());
		if(ORGANIZATIONWORKSHOP==2) {
			soulFileEntity.setFsoulModularType(CommonParameters.ProjectModeules.COME_TO_REALIZE);
		}else{
			soulFileEntity.setFsoulModularType(CommonParameters.ProjectModeules.LEARNALESSON_BOOKESTORE);
			if(fileType.equals(CommonParameters.AppEverBodyShareL.file_picture)) {
				soulFileEntity.setFileType(CommonParameters.AppEverBodyShareL.file_picture);
			}else {
				soulFileEntity.setFileType(CommonParameters.AppEverBodyShareL.file_other);
			}
			soulFileEntity.setFileType(CommonParameters.AppEverBodyShareL.file_other);
		}
		soulFileEntity.setFsoulOriginalFileName(file.getOriginalFilename());
		//soulFileEntity.setNewFileName(originalName);
		fsFileService.insert(soulFileEntity);
		return R.ok()
				.put("fileType",type )
				.put("url","http://110.19.104.227:20020/api/image/" + dateStr + "/" + fileLocalName + suffix);
				//.put("url","http:localhost:8002/api/image/" + dateStr + "/" + fileLocalName + suffix);
	}

	
	/**
	  * 捂一捂  排行耪
	 * @param params
	 * @return
	 */
	@GetMapping("/allUser/listph")
	public R PageUtils(@RequestParam Map<String,Object> params) {
		PageUtils page =service.phPage(params);
		return R.ok().put("page",page);
	}
	
	

}
