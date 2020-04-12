package com.orhonit.modules.generator.controller;

import com.orhonit.common.exception.RRException;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.entity.CoreWorkEntity;
import com.orhonit.modules.generator.service.CoreWorkService;
import com.orhonit.modules.oss.entity.SysOssEntity;
import com.orhonit.modules.oss.service.SysOssService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 民心连心桥表
 * @author xiaobai
 * @date 2019-05-10 16:46:14
 */
@RestController
@RequestMapping("generator/CoreWork")
public class CoreWorkController extends AbstractController{
    @Autowired
    private CoreWorkService coreWorkService;

    @Autowired
    private UploadConfig uploadConfig;
    @Autowired
    SysOssService sysOssService;

    /**
     * 连心桥列表
     */
    @RequestMapping("/selectBy")
    public R list(@RequestParam Map<String, Object> params){
       String workId = (String) params.get("workId");
       if(StringUtils.isNotBlank(workId)) {
    	   PageUtils page = coreWorkService.queryPage(params);
    	   
    	   return R.ok().put("page", page);
       }
       return R.parameterIsNul();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{serveId}")
    public R info(@PathVariable("serveId") String serveId){
		CoreWorkEntity coreWork = coreWorkService.selectByServeId(serveId);
        return R.ok().put("coreWork", coreWork);
    }

    /**
     * 保存
     */
    @RequestMapping("/insert")
    public R save(@RequestBody CoreWorkEntity coreWork){
		coreWorkService.save(coreWork);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CoreWorkEntity coreWork){
		coreWorkService.updateById(coreWork);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{serveId}")
    public R delete(@PathVariable("serveId") String serveId){
		coreWorkService.deleteByServeId(serveId);

        return R.ok();
    }

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    //@RequiresPermissions("sys:oss:all")
    public R upload(@RequestParam("file") MultipartFile file ) throws Exception {
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
        //保存文件信息
        SysOssEntity ossEntity = new SysOssEntity();
        ossEntity.setUrl(filePath);
        ossEntity.setCreateDate(new Date());
        sysOssService.insert(ossEntity);
        //appFsoulFileService.insertFsoulFileEntity(fileType);
        //return R.ok().put("url","http://192.168.124.9:8002/api/image/" + dateStr + "/" + newFileName);
        return R.ok().put("url", "http://110.19.104.227:20020/api/image/" + dateStr + "/" + newFileName);
    }

}
