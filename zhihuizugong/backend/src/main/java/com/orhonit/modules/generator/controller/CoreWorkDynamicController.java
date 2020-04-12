package com.orhonit.modules.generator.controller;

import com.orhonit.common.exception.RRException;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.entity.CoreWorkDynamicEntity;
import com.orhonit.modules.generator.service.CoreWorkDynamicService;
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
import java.util.UUID;

/**
 * 工作队动态表
 * @author xiaobai
 * @date 2019-05-10 16:46:14
 */
@RestController
@RequestMapping("generator/Coreworkdynamic")
public class CoreWorkDynamicController {
    @Autowired
    private CoreWorkDynamicService coreWorkDynamicService;

    @Autowired
    private UploadConfig uploadConfig;
    @Autowired
    SysOssService sysOssService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        String workId = (String) params.get("workId");
        if(StringUtils.isNotBlank(workId)) {
     	   PageUtils page = coreWorkDynamicService.queryPage(params);
     	   
     	   return R.ok().put("page", page);
        }
        return R.parameterIsNul();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{dynamicId}")
    public R info(@PathVariable("dynamicId") String dynamicId){
        CoreWorkDynamicEntity workDynamic = coreWorkDynamicService.selectById(dynamicId);

        return R.ok().put("workDynamic", workDynamic);
    }

    /**
     * 保存
     */
    @RequestMapping("/insertDynamic")
    public R save(@RequestBody CoreWorkDynamicEntity coreWorkDynamic){
		coreWorkDynamicService.insertDynamic(coreWorkDynamic);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{dynamicId}")
    public R delete(@PathVariable("dynamicId") String dynamicId){
		coreWorkDynamicService.deleteByDynamicId(dynamicId);

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

        //上传文件 后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String originalName=file.getOriginalFilename();
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "");
        //String fileLocalName = System.currentTimeMillis() + "";
        Date date = new Date();
        SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMdd");
        String dateStr = dformat.format(date);
        //String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
        //String filePath = uploadConfig.getPath() + dateStr + "//";
        String filePath = uploadConfig.getPath()+"coreworkdynamic/";
        try {
            // 创建目录
/*            File dir = new File(filePath);
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
            in.close();*/
//			oele.save(omf);

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
        } catch (IOException e) {
            e.printStackTrace();
            return R.error();
        }
        //保存文件信息
/*        SysOssEntity ossEntity = new SysOssEntity();
        ossEntity.setUrl(filePath);
        ossEntity.setCreateDate(new Date());
        sysOssService.insert(ossEntity);*/
        //appFsoulFileService.insertFsoulFileEntity(fileType);
        return R.ok().put("url","http://110.19.104.227:20020/api/image/coreworkdynamic/" + newFileName + suffix);
    }

}
