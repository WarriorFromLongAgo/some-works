package com.orhonit.modules.generator.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.entity.CoreAppraisalFileEntity;
import com.orhonit.modules.generator.service.CoreAppraisalFileService;
import com.orhonit.modules.generator.vo.FileTypeJudge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 评比附件表
 *
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-06-26 17:20:44
 */
@RestController
@RequestMapping("generator/coreappraisalfile")
public class CoreAppraisalFileController {
    @Autowired
    private CoreAppraisalFileService coreAppraisalFileService;

    @Autowired
    private UploadConfig uploadConfig;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:coreappraisalfile:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = coreAppraisalFileService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{appraisalId}")
//    @RequiresPermissions("generator:coreappraisalfile:info")
    public R info(@PathVariable("appraisalId") String appraisalId){
		List<CoreAppraisalFileEntity> coreAppraisalFile = coreAppraisalFileService.getByAppraisalId(appraisalId);

        return R.ok().put("coreAppraisalFile", coreAppraisalFile);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("generator:coreappraisalfile:save")
    public R save(@RequestBody CoreAppraisalFileEntity coreAppraisalFile){
		coreAppraisalFileService.insert(coreAppraisalFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("generator:coreappraisalfile:update")
    public R update(@RequestBody CoreAppraisalFileEntity coreAppraisalFile){
		coreAppraisalFileService.updateById(coreAppraisalFile);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("generator:coreappraisalfile:delete")
    public R delete(@RequestBody Integer[] ids){
		coreAppraisalFileService.deleteBatchIds(Arrays.asList(ids));

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
        System.out.println(request.getParameter("appraisalId"));
        // 创建fileEntity
        CoreAppraisalFileEntity appraisalFile = new CoreAppraisalFileEntity();
        appraisalFile.setAppraisalId(request.getParameter("appraisalId"));

        // 文件类型
        String[] temp = file.getOriginalFilename().split("\\.");
        String fileType = temp[temp.length - 1];
        appraisalFile.setFileSuffix("."+fileType);
        // 原文件名
        String fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().length() - fileType.length() - 1);
        appraisalFile.setFileName(fileName);
        // 新文件名
        String fileLocalName = UUID.randomUUID().toString().replaceAll("-", ""); //新文件名 张元加
        appraisalFile.setNewFileName(fileLocalName);
        Date date = new Date();
        SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMdd");
        String dateStr = dformat.format(date);
        // 文件保存路径
        String Path = uploadConfig.getPath() + dateStr + "//";
        String filePath = "/api/image/";//uploadConfig.getPath() + "ipb-meeting/" + dateStr + "/"
        appraisalFile.setFilePath(filePath);
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
            appraisalFile.setFileType(FileTypeJudge.isFileType(FileTypeJudge.getType(new FileInputStream(f))));
            coreAppraisalFileService.insert(appraisalFile);
            return R.ok();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return R.error();
        }
    }

}
