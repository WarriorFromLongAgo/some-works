package com.orhonit.modules.generator.controller;

import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.entity.CoreOpenFileEntity;
import com.orhonit.modules.generator.service.CoreOpenFileService;
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
 * @date 2019-05-22 17:55:44
 */
@RestController
@RequestMapping("generator/coreopenfile")
public class CoreOpenFileController {
    @Autowired
    private CoreOpenFileService coreOpenFileService;

    @Autowired
    private UploadConfig uploadConfig;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:coreopenfile:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = coreOpenFileService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{openId}")
//    @RequiresPermissions("generator:coreopenfile:info")
    public R info(@PathVariable("openId") String openId){
		List<CoreOpenFileEntity> coreOpenFile = coreOpenFileService.getById(openId);

        return R.ok().put("coreOpenFile", coreOpenFile);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("generator:coreopenfile:save")
    public R save(@RequestBody CoreOpenFileEntity coreOpenFile){
		coreOpenFileService.insert(coreOpenFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("generator:coreopenfile:update")
    public R update(@RequestBody CoreOpenFileEntity coreOpenFile){
		coreOpenFileService.updateById(coreOpenFile);

        return R.ok();
    }

    /**
     * 删除根据主表id
     */
    @RequestMapping("/deleteOpenId/{openId}")
//    @RequiresPermissions("generator:coreopenfile:delete")
    public R delete(@PathVariable("openId") String openId){
		coreOpenFileService.deleteByOpenId(openId);

        return R.ok();
    }

    /**
     * 删除主键id
     */
    @RequestMapping("/delete/{id}")
//    @RequiresPermissions("generator:coreopenfile:delete")
    public R deleteId(@PathVariable("id") int id){
        coreOpenFileService.deleteById(id);

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
        System.out.println(request.getParameter("openId"));
        // 创建fileEntity
        CoreOpenFileEntity openFile = new CoreOpenFileEntity();
        openFile.setOpenId(request.getParameter("openId"));

        // 文件类型
        String[] temp = file.getOriginalFilename().split("\\.");
        String fileType = temp[temp.length - 1];
        openFile.setFileSuffix("."+fileType);
        // 原文件名
        String fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().length() - fileType.length() - 1);
        openFile.setFileName(fileName);
        // 新文件名
        String fileLocalName = UUID.randomUUID().toString().replaceAll("-", ""); //新文件名 张元加
        openFile.setNewFileName(fileLocalName);
        Date date = new Date();
        SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMdd");
        String dateStr = dformat.format(date);
        // 文件保存路径
        String Path = uploadConfig.getPath() + dateStr + "//";
        String filePath = "/api/image/" ;//uploadConfig.getPath() + "ipb-meeting/" + dateStr + "/"
        openFile.setFilePath(filePath);
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
            openFile.setFileType(FileTypeJudge.isFileType(FileTypeJudge.getType(new FileInputStream(f))));
            coreOpenFileService.insert(openFile);
            return R.ok();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return R.error();
        }
    }

}
