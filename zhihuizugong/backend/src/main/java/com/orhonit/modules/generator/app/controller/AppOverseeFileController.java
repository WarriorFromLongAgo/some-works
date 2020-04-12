package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.entity.OverseeFileEntity;
import com.orhonit.modules.generator.service.OverseeFileService;
import com.orhonit.modules.generator.vo.FileTypeJudge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * 领导督办附件表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-06 15:02:04
 */
@RestController
@RequestMapping("/app/generator/overseefile")
public class AppOverseeFileController {
    @Autowired
    private OverseeFileService overseeFileService;
    
    @Autowired	
    private UploadConfig uploadConfig;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:overseefile:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = overseeFileService.queryPage(params);

        return R.ok().put("page", page);
    }
    
    /**
     * 文档列表
     */
    @GetMapping("/wordList/{overseeId}")
//    @RequiresPermissions("generator:overseefile:list")
    public R wordList(@PathVariable("overseeId") String overseeId){
        List<OverseeFileEntity> wordList = overseeFileService.wordList(overseeId);

        return R.ok().put("wordList", wordList);
    }
    /**
     * 图片列表
     */
    @GetMapping("/imageList/{overseeId}")
//    @RequiresPermissions("generator:overseefile:list")
    public R imageList(@PathVariable("overseeId") String overseeId){
        List<OverseeFileEntity> imageList = overseeFileService.imageList(overseeId);

        return R.ok().put("imageList", imageList);
    }
    /**
     * 音频列表
     */
    @GetMapping("/audioList/{overseeId}")
//    @RequiresPermissions("generator:overseefile:list")
    public R audioList(@PathVariable("overseeId") String overseeId){
        List<OverseeFileEntity> audioList = overseeFileService.audioList(overseeId);

        return R.ok().put("audioList", audioList);
    }
    /**
     * 视频列表
     */
    @GetMapping("/videoList/{overseeId}")
//    @RequiresPermissions("generator:overseefile:list")
    public R videoList(@PathVariable("overseeId") String overseeId){
        List<OverseeFileEntity> videoList = overseeFileService.videoList(overseeId);

        return R.ok().put("videoList", videoList);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("generator:overseefile:info")
    public R info(@PathVariable("id") Integer id){
		OverseeFileEntity overseeFile = overseeFileService.selectById(id);

        return R.ok().put("overseeFile", overseeFile);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("generator:overseefile:save")
    public R save(@RequestBody OverseeFileEntity overseeFile){
		overseeFileService.insert(overseeFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("generator:overseefile:update")
    public R update(@RequestBody OverseeFileEntity overseeFile){
		overseeFileService.updateById(overseeFile);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("generator:overseefile:delete")
    public R delete(@RequestBody Integer id){
		overseeFileService.deleteById(id);

        return R.ok();
    }
    
    /**
	 * 上传附件
	 * 
	 * @param file
	 * @param request
	 */
	@PostMapping(value = "/upload")
	public boolean uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		System.out.println(request.getParameter("overseeId"));
		// 创建fileEntity
		OverseeFileEntity omf = new OverseeFileEntity();
		omf.setOverseeId(request.getParameter("overseeId").toString());
		// 文件类型
		String[] temp = file.getOriginalFilename().split("\\.");
		String fileType = temp[temp.length - 1];
		omf.setFileSuffix(fileType);
		// 原文件名
		String fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().length() - fileType.length() - 1);
		omf.setFileName(fileName);
		// 新文件名
		String fileLocalName = UUID.randomUUID().toString().replaceAll("-", ""); //新文件名 张元加
		omf.setNewFileName(fileLocalName);
		// 文件保存路径
		String filePath = uploadConfig.getPath();//uploadConfig.getPath() + "ipb-meeting/" + dateStr + "/"
		omf.setFilePath(filePath);
		try {
			// 创建目录
			File dir = new File(filePath);
			
			dir.mkdirs();

			// 获取文件输入流
			InputStream in = file.getInputStream();

			// 创建文件并保存
			File f = new File(filePath + fileLocalName + "." + fileType);
			FileOutputStream fos = new FileOutputStream(f);
			byte[] b = new byte[1024];
			int n = 0;
			while ((n = in.read(b)) != -1) {
				fos.write(b, 0, n);				
			}
			fos.close();
			in.close();
			omf.setFileType(FileTypeJudge.isFileType(FileTypeJudge.getType(new FileInputStream(f))));
			overseeFileService.insert(omf);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
