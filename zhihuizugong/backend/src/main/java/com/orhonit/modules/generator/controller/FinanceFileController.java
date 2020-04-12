package com.orhonit.modules.generator.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.entity.FinanceFileEntity;
import com.orhonit.modules.generator.service.FinanceFileService;
import com.orhonit.modules.generator.vo.FileTypeJudge;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;



/**
 * 财务管理附件表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-24 15:12:00
 */
@RestController
@RequestMapping("generator/financefile")
public class FinanceFileController extends AbstractController {
    @Autowired
    private FinanceFileService financeFileService;
    
    @Autowired	
    private UploadConfig uploadConfig;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:financefile:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = financeFileService.queryPage(params);

        return R.ok().put("page", page);
    }
    
    /**
     * 财务管理主表下属所有附件列表
     */
    @RequestMapping("/allList/{financeId}")
//    @RequiresPermissions("generator:financefile:list")
    public R allList(@PathVariable("financeId") String financeId){
    	List<FinanceFileEntity> list = financeFileService.allList(financeId);

        return R.ok().put("list", list);
    }
    
    /**
     * 文档列表
     */
    @GetMapping("/wordList/{financeId}")
//    @RequiresPermissions("generator:overseefile:list")
    public R wordList(@PathVariable("financeId") String financeId){
        List<FinanceFileEntity> wordList = financeFileService.wordList(financeId);

        return R.ok().put("wordList", wordList);
    }
    /**
     * 图片列表
     */
    @GetMapping("/imageList/{financeId}")
//    @RequiresPermissions("generator:overseefile:list")
    public R imageList(@PathVariable("financeId") String financeId){
        List<FinanceFileEntity> imageList = financeFileService.imageList(financeId);

        return R.ok().put("imageList", imageList);
    }
    /**
     * 音频列表
     */
    @GetMapping("/audioList/{financeId}")
//    @RequiresPermissions("generator:overseefile:list")
    public R audioList(@PathVariable("financeId") String financeId){
        List<FinanceFileEntity> audioList = financeFileService.audioList(financeId);

        return R.ok().put("audioList", audioList);
    }
    /**
     * 视频列表
     */
    @GetMapping("/videoList/{financeId}")
//    @RequiresPermissions("generator:overseefile:list")
    public R videoList(@PathVariable("financeId") String financeId){
        List<FinanceFileEntity> videoList = financeFileService.videoList(financeId);

        return R.ok().put("videoList", videoList);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:financefile:info")
    public R info(@PathVariable("id") Integer id){
		FinanceFileEntity financeFile = financeFileService.selectById(id);

        return R.ok().put("financeFile", financeFile);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:financefile:save")
    public R save(@RequestBody FinanceFileEntity financeFile){
		financeFileService.insert(financeFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:financefile:update")
    public R update(@RequestBody FinanceFileEntity financeFile){
		financeFileService.updateById(financeFile);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
//    @RequiresPermissions("generator:financefile:delete")
    public R delete(@PathVariable("id") Integer id){
		financeFileService.deleteById(id);

        return R.ok();
    }
    
    /**
	 * 上传附件
	 * 
	 * @param file
	 * @param request
	 */
	@PostMapping(value = "/upload")
	public boolean uploadFile(@RequestParam("file") MultipartFile[] file, HttpServletRequest request) {
		System.out.println(request.getParameter("financeId"));
		if(file.length > 0) {
			for (MultipartFile multipartFile : file) {
				// 创建fileEntity
				FinanceFileEntity omf = new FinanceFileEntity();
				omf.setFinanceId(request.getParameter("financeId").toString());
				// 文件类型
				String[] temp = multipartFile.getOriginalFilename().split("\\.");
				String fileType = temp[temp.length - 1];
				omf.setFileSuffix(fileType);
				// 原文件名
				String fileName = multipartFile.getOriginalFilename().substring(0,multipartFile.getOriginalFilename().length() - fileType.length() - 1);
				omf.setFileName(fileName);
				// 新文件名
				String fileLocalName = UUID.randomUUID().toString().replaceAll("-", ""); //新文件名 张元加
				omf.setFileNewName(fileLocalName);
				// 文件保存路径
				String filePath = uploadConfig.getPath();//uploadConfig.getPath() + "ipb-meeting/" + dateStr + "/"
				omf.setFilePath(filePath);
				try {
					// 创建目录
					File dir = new File(filePath);
					
					dir.mkdirs();

					// 获取文件输入流
					InputStream in = multipartFile.getInputStream();

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
					financeFileService.insert(omf);
					return true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
		
	}

}
