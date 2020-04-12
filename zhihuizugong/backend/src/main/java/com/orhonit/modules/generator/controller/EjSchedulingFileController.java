package com.orhonit.modules.generator.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.entity.EjSchedulingFileEntity;
import com.orhonit.modules.generator.service.EjSchedulingFileService;
import com.orhonit.modules.generator.vo.FileTypeJudge;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * 调度附件表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 21:04:44
 */
@RestController
@RequestMapping("generator/ejschedulingfile")
public class EjSchedulingFileController {
    @Autowired
    private EjSchedulingFileService ejSchedulingFileService;
    @Autowired
    private UploadConfig uploadConfig;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:ejschedulingfile:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ejSchedulingFileService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 调度附件列表
     */
    @GetMapping("/fileList")
//    @RequiresPermissions("generator:ejschedulingfile:list")
    public R fileList(@RequestParam("schedulingId") String schedulingId){
        List<EjSchedulingFileEntity> fileEntityList = ejSchedulingFileService.selectList(new EntityWrapper<EjSchedulingFileEntity>().and(StringUtils.isNotBlank(schedulingId),"scheduling_id ="+schedulingId));

        return R.ok().put("fileEntityList", fileEntityList);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:ejschedulingfile:info")
    public R info(@PathVariable("id") Integer id){
		EjSchedulingFileEntity ejSchedulingFile = ejSchedulingFileService.selectById(id);

        return R.ok().put("ejSchedulingFile", ejSchedulingFile);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:ejschedulingfile:save")
    public R save(@RequestBody EjSchedulingFileEntity ejSchedulingFile){
		ejSchedulingFileService.insert(ejSchedulingFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:ejschedulingfile:update")
    public R update(@RequestBody EjSchedulingFileEntity ejSchedulingFile){
		ejSchedulingFileService.updateById(ejSchedulingFile);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:ejschedulingfile:delete")
    public R delete(@RequestBody Integer[] ids){
		ejSchedulingFileService.deleteBatchIds(Arrays.asList(ids));

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
        System.out.println(request.getParameter("schedulingId"));
        // 创建fileEntity
        EjSchedulingFileEntity omf = new EjSchedulingFileEntity();
        if(request.getParameter("schedulingId") != null && !request.getParameter("schedulingId").equals("")){
            omf.setSchedulingId(request.getParameter("schedulingId").toString());
        }
        if(request.getParameter("finishId") != null && !request.getParameter("finishId").equals("")){
            omf.setFinishId(Integer.parseInt(request.getParameter("finishId")));
        }
        // 文件类型
        omf.setType(Integer.parseInt(request.getParameter("type")));
        String[] temp = file.getOriginalFilename().split("\\.");
        String fileType = temp[temp.length - 1];
        omf.setFileSuffix(fileType);
        // 原文件名
        String fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().length() - fileType.length() - 1);
        omf.setFileName(fileName);
        // 新文件名
        String fileLocalName = UUID.randomUUID().toString().replaceAll("-", ""); //新文件名 张元加
        omf.setFileNewName(fileLocalName);
        // 文件保存路径
        String filePath = uploadConfig.getPath();//uploadConfig.getPath() + "ipb-meeting/" + dateStr + "/"
        omf.setFilePath(filePath);
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
            omf.setFileType(FileTypeJudge.isFileType(FileTypeJudge.getType(new FileInputStream(f))));
            ejSchedulingFileService.insert(omf);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error();
        }
        return R.ok().put("id", omf.getId());
    }

}
