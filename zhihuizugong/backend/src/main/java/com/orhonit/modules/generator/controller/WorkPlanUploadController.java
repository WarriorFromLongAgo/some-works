package com.orhonit.modules.generator.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orhonit.common.exception.RRException;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.dao.WorkPlanDao;
import com.orhonit.modules.generator.entity.ZgPlanFileEntity;
import com.orhonit.modules.generator.service.WorkPlanUploadService;

@RestController
@RequestMapping("/plan")
public class WorkPlanUploadController {

	@Autowired
	private UploadConfig uploadConfig;
	@Autowired
	private WorkPlanDao workPlanDao;
	@Autowired
	private WorkPlanUploadService workPlanUploadService;
	
	/**
	 * 上传文件
	 */
	@PostMapping("/upload")
	//@RequiresPermissions("sys:oss:all")
	public R upload(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}
		System.out.println(request.getParameter("planId"));
		ZgPlanFileEntity zgPlanFileEntity = new ZgPlanFileEntity();
		//主表id
		zgPlanFileEntity.setPlanId(request.getParameter("planId"));
		zgPlanFileEntity.setId(UUID.randomUUID().toString().replace("-", ""));
		zgPlanFileEntity.setCreateTime(new Date());	
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		//文件类型
		zgPlanFileEntity.setFileSuffix(suffix);
		String fileName=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf("."));
		//原文件名
		zgPlanFileEntity.setFileName(fileName);
		String newFileName = UUID.randomUUID().toString().replaceAll("-", ""); 
		//新文件名
		zgPlanFileEntity.setNewFileName(newFileName);
		String filePath = uploadConfig.getPath();
		//附件地址
		zgPlanFileEntity.setFilePath(filePath);		
		//上传文件
			try {
				// 创建目录
				File dir = new File(filePath);
				
				dir.mkdirs();

				// 获取文件输入流
				InputStream in = file.getInputStream();

				// 创建文件并保存
				File f = new File(filePath + newFileName  + suffix);
				FileOutputStream fos = new FileOutputStream(f);
				byte[] b = new byte[1024];
				int n = 0;
				while ((n = in.read(b)) != -1) {
					fos.write(b, 0, n);				
				}
				fos.close();
				in.close();
				workPlanDao.saveFile(zgPlanFileEntity);
				//oele.save(omf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return R.error();
		}
		return R.ok().put("id", zgPlanFileEntity.getId());
	}
	/**
	 * 删除附件
	 * @param id
	 * @return
	 */
	@RequestMapping("/del")
	public R del(String id) {
		workPlanUploadService.deleteById(id);
		return R.ok();
	}
	
	
	
	
}
