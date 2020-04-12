package com.orhonit.modules.generator.controller;

import com.orhonit.common.exception.RRException;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 公用上传接口
 */
@RestController
@RequestMapping("/common")
public class CommonUploadController {

    @Autowired
    private UploadConfig uploadConfig;

    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) throws Exception{
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }
        //后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //原文件名
        String fileName=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf("."));
        //新文件名
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "");
        String filePath = uploadConfig.getPath();
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
            //oele.save(omf);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return R.error();
        }
        return R.ok().put("url","http://110.19.104.227:20020/api/image/"+newFileName+suffix);
    }
}
