package com.orhonit.modules.generator.app.controller;

import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.entity.CoreOpinionFileEntity;
import com.orhonit.modules.generator.service.CoreOpinionFileService;
import com.orhonit.modules.generator.vo.FileTypeJudge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * 意见附件
 * @author xiaobai
 * @date 2019-05-13 14:37:50
 */
@RestController
@RequestMapping("app/coreopinionfile")
public class AppCoreOpinionFileController {
    @Autowired
    private CoreOpinionFileService coreOpinionFileService;

    @Autowired
    private UploadConfig uploadConfig;

    /**
     * 信息
     */
    @RequestMapping("/info/{opinionId}")
//    @RequiresPermissions("generator:coreopinion:info")
    public R info(@PathVariable("opinionId") String opinionId){
        List<CoreOpinionFileEntity> coreOpinionFile = coreOpinionFileService.getById(opinionId);

        return R.ok().put("coreOpinionFile", coreOpinionFile);
    }

    /**
     * 上传附件
     *
     * @param file
     * @param request
     */
    @PostMapping(value = "/upload")
    public R uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        System.out.println(request.getParameter("opinionId"));
        // 创建fileEntity
        CoreOpinionFileEntity opinionFile = new CoreOpinionFileEntity();
        opinionFile.setOpinionId(request.getParameter("opinionId"));

        // 文件类型
        String[] temp = file.getOriginalFilename().split("\\.");
        String fileType = temp[temp.length - 1];
        opinionFile.setFileSuffix("."+fileType);
        // 原文件名
        String fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().length() - fileType.length() - 1);
        opinionFile.setFileName(fileName);
        // 新文件名
        String fileLocalName = UUID.randomUUID().toString().replaceAll("-", ""); //新文件名 张元加
        opinionFile.setNewFileName(fileLocalName);
        Date date = new Date();
        SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMdd");
        String dateStr = dformat.format(date);
        // 文件保存路径
        String Path = uploadConfig.getPath() + dateStr + "//";
        String filePath = "/api/image/" ;//uploadConfig.getPath() + "ipb-meeting/" + dateStr + "/"
        opinionFile.setFilePath(filePath);

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
            opinionFile.setFileType(FileTypeJudge.isFileType(FileTypeJudge.getType(new FileInputStream(f))));
            coreOpinionFileService.insert(opinionFile);
            return R.ok()
                    /*.put("url","http:110.19.104.227:20020/api/image/" + dateStr + "/" + fileLocalName + fileType)
                    .put("type", opinionFile.getFileType())*/;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return R.error();
        }
    }

}
