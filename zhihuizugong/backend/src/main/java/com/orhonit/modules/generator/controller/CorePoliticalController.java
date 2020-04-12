package com.orhonit.modules.generator.controller;

import com.orhonit.common.exception.RRException;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.entity.CorePoliticalEntity;
import com.orhonit.modules.generator.service.CorePoliticalService;
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

/**
 * 生活时时讲
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-05 16:36:25
 */
@RestController
@RequestMapping("generator/corepolitical")
public class CorePoliticalController {
    @Autowired
    private CorePoliticalService corePoliticalService;

    @Autowired
    private UploadConfig uploadConfig;

    @Autowired
    private SysOssService sysOssService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list" , method = RequestMethod.GET)
//    @RequiresPermissions("generator:corepolitical:list")
    public R list(@RequestParam Map<String, Object> params){
        String politicalType = (String) params.get("politicalType");
        if(StringUtils.isNotBlank(politicalType)) {
            PageUtils page = corePoliticalService.queryPage(params);

            return R.ok().put("page", page);
        }
        return R.parameterIsNul();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("generator:corepolitical:info")
    public R info(@PathVariable("id") Integer id){
		CorePoliticalEntity corePolitical = corePoliticalService.selectById(id);

        return R.ok().put("corePolitical", corePolitical);
    }

    /**
     * 保存
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
//    @RequiresPermissions("generator:corepolitical:save")
    public R save(@RequestBody CorePoliticalEntity corePolitical){
		corePoliticalService.insert(corePolitical);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value="/update",method = RequestMethod.PUT)
//    @RequiresPermissions("generator:corepolitical:update")
    public R update(@RequestBody CorePoliticalEntity corePolitical){
		corePoliticalService.updateById(corePolitical);

        return R.ok();
    }

    /**
     * 删除根据主键
     */
    @RequestMapping(value="/delete/{id}",method = RequestMethod.DELETE)
//    @RequiresPermissions("generator:corepolitical:delete")
    public R delete(@PathVariable("id") Integer id){
        corePoliticalService.deleteById(id);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value="/deletePolitical",method = RequestMethod.DELETE)
//    @RequiresPermissions("generator:corepolitical:delete")
    public R delete(@RequestBody Integer[] ids){
		corePoliticalService.batchDeletePolitical(ids);

        return R.ok();
    }

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    //@RequiresPermissions("sys:oss:all")
    public R upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }

        //上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileLocalName = System.currentTimeMillis() + "";
        Date date = new Date();
        SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMdd");
        String dateStr = dformat.format(date);
        //String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
        String filePath = uploadConfig.getPath() + dateStr + "//";
        try {
            // 创建目录
            File dir = new File(filePath);

            dir.mkdirs();

            // 获取文件输入流
            InputStream in = file.getInputStream();

            // 创建文件并保存
            File f = new File(filePath + fileLocalName + suffix);
            FileOutputStream fos = new FileOutputStream(f);
            byte[] b = new byte[1024];
            int n = 0;
            while ((n = in.read(b)) != -1) {
                fos.write(b, 0, n);
            }
            fos.close();
            in.close();
//			oele.save(omf);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return R.error();
        }
        //保存文件信息
        SysOssEntity ossEntity = new SysOssEntity();
        ossEntity.setUrl(filePath);
        ossEntity.setCreateDate(new Date());
        sysOssService.insert(ossEntity);

        return R.ok().put("url","http://39.104.121.137:8080/ftp/" + dateStr + "/" + fileLocalName);
    }

}
