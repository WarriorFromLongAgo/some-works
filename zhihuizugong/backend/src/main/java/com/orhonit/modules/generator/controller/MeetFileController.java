package com.orhonit.modules.generator.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.entity.MeetFileEntity;
import com.orhonit.modules.generator.service.MeetFileService;
import com.orhonit.modules.generator.vo.FileTypeJudge;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 会议附件
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-17 16:37:36
 */
@RestController
@RequestMapping("generator/meetfile")
public class MeetFileController {
    @Autowired
    private MeetFileService meetFileService;

    @Autowired	
    private UploadConfig uploadConfig;
    
    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:meetfile:list")
    public R list(@RequestParam Map<String, Object> params){
    	String meetId = (String) params.get("meetId");
    	if(StringUtils.isNotBlank(meetId)) {
	        PageUtils page = meetFileService.queryPage(params);
	
	        return R.ok().put("page", page);
    	}
        return R.parameterIsNul();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{meetId}")
//    @RequiresPermissions("generator:meetfile:info")
    public R info(@PathVariable("meetId") String meetId){
		List<MeetFileEntity> meetFile = meetFileService.getById(meetId);

        return R.ok().put("meetFile", meetFile);
    }

    /**
	 * 上传附件
	 * 
	 * @param file
	 * @param request
	 */
	@PostMapping(value = "/upload")
	public R uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		System.out.println(request.getParameter("meetId"));
		// 创建fileEntity
		MeetFileEntity meetFile = new MeetFileEntity();
		meetFile.setMeetId(request.getParameter("meetId"));

		// 文件类型
		String[] temp = file.getOriginalFilename().split("\\.");
		String fileType = temp[temp.length - 1];
		meetFile.setFileSuffix("."+fileType);
		// 原文件名
		String fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().length() - fileType.length() - 1);
		meetFile.setFileName(fileName);
		// 新文件名
		String fileLocalName = UUID.randomUUID().toString().replaceAll("-", ""); //新文件名 张元加
		meetFile.setNewFileName(fileLocalName);
		// 文件保存路径
		String Path = uploadConfig.getPath() + "meet/";
		String filePath = "/api/image/" ;//uploadConfig.getPath() + "ipb-meeting/" + dateStr + "/"
		meetFile.setFilePath(filePath);
		try {
			// 创建目录
			File dir = new File(Path);
			dir.mkdirs();

			// 获取文件输入流
			InputStream in = file.getInputStream();

			// 创建文件并保存
			File f = new File(Path + fileLocalName + "." + fileType);
			FileOutputStream fos = new FileOutputStream(f);
			byte[] b = new byte[1024];
			int n = 0;
			while ((n = in.read(b)) != -1) {
				fos.write(b, 0, n);
			}
			fos.close();
			in.close();
			meetFile.setFileType(FileTypeJudge.isFileType(FileTypeJudge.getType(new FileInputStream(f))));
			meetFileService.insert(meetFile);
			return R.ok().put("id", meetFile.getId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return R.error();
		}
	}
    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("generator:meetfile:save")
    public R save(@RequestBody List<MeetFileEntity> meetFile){
		meetFileService.insertFile(meetFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("generator:meetfile:update")
    public R update(@RequestBody MeetFileEntity meetFile){
		meetFileService.updateByMeetFile(meetFile);

        return R.ok();
    }

    /**
	 * 删除根据主表id
	 */
	@RequestMapping("/deleteMeetId/{meetId}")
//    @RequiresPermissions("generator:meetfile:delete")
	public R delete(@PathVariable("meetId") String meetId){
		meetFileService.deleteBymeetId(meetId);

		return R.ok();
	}

	/**
	 * 删除主键ID
	 */
	@RequestMapping("/delete/{id}")
//    @RequiresPermissions("generator:meetfile:delete")
	public R deleteId(@PathVariable("id") Integer id){
		meetFileService.deleteById(id);

		return R.ok();
	}

}
