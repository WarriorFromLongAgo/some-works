package com.orhonit.modules.generator.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.entity.CoreReportFileEntity;
import com.orhonit.modules.generator.service.CoreReportFileService;
import com.orhonit.modules.generator.vo.FileTypeJudge;
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
 * 党务公开附件表
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-05-29 17:43:28
 */
@RestController
@RequestMapping("generator/corereportfile")
public class CoreReportFileController {
    @Autowired
    private CoreReportFileService coreReportFileService;

    @Autowired
    private UploadConfig uploadConfig;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:corereportfile:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = coreReportFileService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{reportId}")
//    @RequiresPermissions("generator:corereportfile:info")
    public R info(@PathVariable("reportId") String reportId){
		List<CoreReportFileEntity> coreReportFile = coreReportFileService.getById(reportId);

        return R.ok().put("coreReportFile", coreReportFile);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("generator:corereportfile:update")
    public R update(@RequestBody CoreReportFileEntity coreReportFile){
		coreReportFileService.updateById(coreReportFile);

        return R.ok();
    }

    /**
     * 删除根据主表id
     */
    @RequestMapping("/deleteReportId/{reportId}")
//    @RequiresPermissions("generator:corereportfile:delete")
    public R delete(@PathVariable("reportId") String reportId){
		coreReportFileService.removeById(reportId);

        return R.ok();
    }

    /**
     * 删除主键id
     */
    @RequestMapping("/delete/{id}")
//    @RequiresPermissions("generator:corereportfile:delete")
    public R deleteId(@PathVariable("id") Integer id){
        coreReportFileService.deleteById(id);

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
        System.out.println(request.getParameter("reportId"));
        // 创建fileEntity
        CoreReportFileEntity reportFile = new CoreReportFileEntity();
        reportFile.setReportId(request.getParameter("reportId"));

        // 文件类型
        String[] temp = file.getOriginalFilename().split("\\.");
        String fileType = temp[temp.length - 1];
        reportFile.setFileSuffix("."+fileType);
        // 原文件名
        String fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().length() - fileType.length() - 1);
        reportFile.setFileName(fileName);
        // 新文件名
        String fileLocalName = UUID.randomUUID().toString().replaceAll("-", ""); //新文件名 张元加
        reportFile.setNewFileName(fileLocalName);
        Date date = new Date();
        SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMdd");
        String dateStr = dformat.format(date);
        // 文件保存路径
        String Path = uploadConfig.getPath() + dateStr + "//";
        String filePath = "/api/image/" ;//uploadConfig.getPath() + "ipb-meeting/" + dateStr + "/"
        reportFile.setFilePath(filePath);
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
            reportFile.setFileType(FileTypeJudge.isFileType(FileTypeJudge.getType(new FileInputStream(f))));
            coreReportFileService.insert(reportFile);
            return R.ok();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return R.error();
        }
    }
}
