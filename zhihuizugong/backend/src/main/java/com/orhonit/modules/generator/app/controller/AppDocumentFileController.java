package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.entity.DocumentFileEntity;
import com.orhonit.modules.generator.service.DocumentFileService;
import com.orhonit.modules.generator.vo.FileTypeJudge;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * 公文管理表
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-06 15:13:07
 */
@RestController
@RequestMapping("/app/documentfile")
public class AppDocumentFileController {
    @Autowired
    private DocumentFileService documentFileService;

    @Autowired
    private UploadConfig uploadConfig;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:documentfile:list")
    public R list(@RequestParam Map<String, Object> params) {
        String documentId = (String) params.get("documentId");
        if (StringUtils.isNotBlank(documentId)) {
            PageUtils page = documentFileService.queryPage(params);

            return R.ok().put("page", page);
        }
        return R.parameterIsNul();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{documentId}")
//    @RequiresPermissions("generator:documentfile:info")
    public R info(@PathVariable("documentId") String documentId) {
        List<DocumentFileEntity> documentFile = documentFileService.getById(documentId);

        return R.ok().put("documentFile", documentFile);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("generator:documentfile:save")
    public R save(@RequestBody DocumentFileEntity documentFile) {
        documentFileService.insert(documentFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("generator:documentfile:update")
    public R update(@RequestBody DocumentFileEntity documentFile) {
        documentFileService.updateById(documentFile);

        return R.ok();
    }

    /**
     * 删除主键ID
     */
    @RequestMapping("/delete/{id}")
//    @RequiresPermissions("generator:documentfile:delete")
    public R delete(@PathVariable("id") Integer id) {
        documentFileService.deleteById(id);

        return R.ok();
    }

    /**
     * 上传附件
     *
     * @param file
     * @param request
     */
    @PostMapping(value = "/upload")
    public R uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        System.out.println(request.getParameter("documentId"));
        // 创建fileEntity
        DocumentFileEntity documentFile = new DocumentFileEntity();
        documentFile.setDocumentId(request.getParameter("documentId"));

        // 文件类型
        String[] temp = file.getOriginalFilename().split("\\.");
        String fileType = temp[temp.length - 1];
        documentFile.setFileSuffix(fileType);
        // 原文件名
        String fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().length() - fileType.length() - 1);
        documentFile.setFileName(fileName);
        // 新文件名
        String fileLocalName = UUID.randomUUID().toString().replaceAll("-", ""); //新文件名 张元加
        documentFile.setNewFileName(fileLocalName);
        // 文件保存路径
        String filePath = uploadConfig.getPath();//uploadConfig.getPath() + "ipb-meeting/" + dateStr + "/"
        documentFile.setFilePath(filePath);
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
            documentFile.setFileType(FileTypeJudge.isFileType(FileTypeJudge.getType(new FileInputStream(f))));
            documentFileService.insert(documentFile);
            return R.ok();
        } catch (IOException e) {
            e.printStackTrace();
            return R.error();
        }
    }

}
