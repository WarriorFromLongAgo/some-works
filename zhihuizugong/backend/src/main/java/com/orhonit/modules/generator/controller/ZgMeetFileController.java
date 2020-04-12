package com.orhonit.modules.generator.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orhonit.common.exception.RRException;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.entity.ZgMeetFileEntity;
import com.orhonit.modules.generator.entity.ZgPlanFileEntity;
import com.orhonit.modules.generator.service.ZgMeetFileService;

/**
 * 会议通知附件
 * @author baiam
 *
 */

@RestController
@RequestMapping("/meet")
public class ZgMeetFileController {

	@Autowired
	private UploadConfig uploadConfig;
	@Autowired
	private ZgMeetFileService ZgMeetFileService;
	
	@PostMapping("/upload")
	public R upload(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}
		System.out.println(request.getParameter("meetId"));
		System.out.println(request.getParameter("fileType"));
		ZgMeetFileEntity zgMeetFileEntity = new ZgMeetFileEntity();
		//主表id
		zgMeetFileEntity.setMeetId(request.getParameter("meetId"));
		//1-会议资料 2-会议记录 3-会议总结
		zgMeetFileEntity.setFileType(Integer.parseInt(request.getParameter("fileType")));
		zgMeetFileEntity.setId(UUID.randomUUID().toString().replace("-", ""));
		zgMeetFileEntity.setCreateTime(new Date());	
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		//文件类型
		zgMeetFileEntity.setFileSuffix(suffix);
		String fileName=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf("."));
		//原文件名
		zgMeetFileEntity.setFileName(fileName);
		String newFileName = UUID.randomUUID().toString().replaceAll("-", ""); 
		//新文件名
		zgMeetFileEntity.setFileNewName(newFileName);
		String filePath = uploadConfig.getPath();
		//附件地址
		zgMeetFileEntity.setFilePath(filePath);		
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
				ZgMeetFileService.saveFile(zgMeetFileEntity);
				//oele.save(omf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return R.error();
		}
		
		return R.ok().put("id", zgMeetFileEntity.getId());
	}
	/**
	 * 删除附件
	 * @param id
	 * @return
	 */
	@RequestMapping("/del")
	public R del(String id) {
		ZgMeetFileService.deleteById(id);
		return R.ok();
	}
	/**
	 * 查看附件信息
	 * @param params
	 * @return
	 */
	@RequestMapping("/findFile")
	public R findFile(@RequestParam Map<String, Object> params) {
		PageUtils page = ZgMeetFileService.findFile(params);
		return R.ok().put("page", page);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
