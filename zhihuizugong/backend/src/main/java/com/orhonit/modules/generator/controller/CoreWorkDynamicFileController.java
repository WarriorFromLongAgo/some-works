package com.orhonit.modules.generator.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.entity.CoreWorkDynamicFileEntity;
import com.orhonit.modules.generator.service.CoreWorkDynamicFileService;
import com.orhonit.modules.generator.vo.FileTypeJudge;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Map;
import java.util.UUID;

/**
 * 动态附件表
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-05-29 17:43:28
 */
@RestController
@RequestMapping("generator/dynamicfile")
public class CoreWorkDynamicFileController {
    @Autowired
    private CoreWorkDynamicFileService coreWorkDynamicFileService;

    @Autowired
    private UploadConfig uploadConfig;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:corereportfile:list")
    public R list(@RequestParam Map<String, Object> params) {
        String dynamicId = (String) params.get("dynamicId");
        if (StringUtils.isNotBlank(dynamicId)) {
            PageUtils page = coreWorkDynamicFileService.queryPage(params);

            return R.ok().put("page", page);
        }
        return R.parameterIsNul();
    }


    /**
     * 删除根据主表id
     */
    @RequestMapping("/deleteDynamicId/{dynamicId}")
//    @RequiresPermissions("generator:corereportfile:delete")
    public R delete(@PathVariable("dynamicId") String dynamicId) {
        coreWorkDynamicFileService.removeById(dynamicId);

        return R.ok();
    }

    /**
     * 删除主键id
     */
    @RequestMapping("/delete/{id}")
//    @RequiresPermissions("generator:corereportfile:delete")
    public R deleteId(@PathVariable("id") Integer id) {
        coreWorkDynamicFileService.deleteById(id);

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
        System.out.println(request.getParameter("dynamicId"));
        // 创建fileEntity
        CoreWorkDynamicFileEntity dynamicFile = new CoreWorkDynamicFileEntity();
        dynamicFile.setDynamicId(request.getParameter("dynamicId"));

        // 文件类型
        String[] temp = file.getOriginalFilename().split("\\.");
        String fileType = temp[temp.length - 1];
        dynamicFile.setFileSuffix(fileType);
        // 原文件名
        String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().length() - fileType.length() - 1);
        dynamicFile.setFileName(fileName);
        // 新文件名
        String fileLocalName = UUID.randomUUID().toString().replaceAll("-", ""); //新文件名 张元加
        dynamicFile.setNewFileName(fileLocalName);
        // 文件保存路径
        String filePath = uploadConfig.getPath();//uploadConfig.getPath() + "ipb-meeting/" + dateStr + "/"
        dynamicFile.setFilePath(filePath);
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
            dynamicFile.setFileType(FileTypeJudge.isFileType(FileTypeJudge.getType(new FileInputStream(f))));
            coreWorkDynamicFileService.insert(dynamicFile);
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
}
