package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.entity.CoreOrganizationFileEntity;
import com.orhonit.modules.generator.service.CoreOrganizationFileService;
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
 * 党组织附件表
 *
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-06-20 14:14:55
 */
@RestController
@RequestMapping("app/coreorganizationfile")
public class AppCoreOrganizationFileController {
    @Autowired
    private CoreOrganizationFileService coreOrganizationFileService;

    @Autowired
    private UploadConfig uploadConfig;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:coreorganizationfile:list")
    public R list(@RequestParam Map<String, Object> params) {
        String organizationId = (String) params.get("organizationId");
        if (StringUtils.isNotBlank(organizationId)) {
            PageUtils page = coreOrganizationFileService.queryPage(params);

            return R.ok().put("page", page);
        }
        return R.parameterIsNul();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{organizationId}")
//    @RequiresPermissions("generator:coreorganizationfile:info")
    public R info(@PathVariable("organizationId") String organizationId) {
        List<CoreOrganizationFileEntity> coreOrganizationFile = coreOrganizationFileService.getById(organizationId);

        return R.ok().put("coreOrganizationFile", coreOrganizationFile);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("generator:coreorganizationfile:save")
    public R save(@RequestBody CoreOrganizationFileEntity coreOrganizationFile) {
        coreOrganizationFileService.insert(coreOrganizationFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("generator:coreorganizationfile:update")
    public R update(@RequestBody CoreOrganizationFileEntity coreOrganizationFile) {
        coreOrganizationFileService.updateById(coreOrganizationFile);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{id}")
//    @RequiresPermissions("generator:coreorganizationfile:delete")
    public R delete(@PathVariable("id") Integer id) {
        coreOrganizationFileService.deleteById(id);

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
        System.out.println(request.getParameter("organizationId"));
        // 创建fileEntity
        CoreOrganizationFileEntity organizationFile = new CoreOrganizationFileEntity();
        organizationFile.setOrganizationId(request.getParameter("organizationId"));

        // 文件类型o'o
        String[] temp = file.getOriginalFilename().split("\\.");
        String fileType = temp[temp.length - 1];
        organizationFile.setFileSuffix(fileType);
        // 原文件名
        String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().length() - fileType.length() - 1);
        organizationFile.setFileName(fileName);
        // 新文件名
        String fileLocalName = UUID.randomUUID().toString().replaceAll("-", ""); //新文件名 张元加
        organizationFile.setNewFileName(fileLocalName);
        // 文件保存路径
        String filePath = uploadConfig.getPath();//uploadConfig.getPath() + "ipb-meeting/" + dateStr + "/"
        organizationFile.setFilePath(filePath);
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
            organizationFile.setFileType(FileTypeJudge.isFileType(FileTypeJudge.getType(new FileInputStream(f))));
            coreOrganizationFileService.insert(organizationFile);
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

}
