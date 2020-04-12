package com.orhonit.modules.generator.controller;

import com.orhonit.common.exception.RRException;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.entity.CoreOpenEntity;
import com.orhonit.modules.generator.service.CoreOpenService;
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
 * 党务公开表
 * @author xiaobai
 * @date 2019-05-18 15:14:02
 */
@RestController
@RequestMapping("generator/coreopen")
public class CoreOpenController {
    @Autowired
    private CoreOpenService coreOpenService;

    @Autowired
    private UploadConfig uploadConfig;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:coreopen:list")
    public R list(@RequestParam Map<String, Object> params){
        String openType = (String) params.get("openType");
        if(StringUtils.isNotBlank(openType)) {
            PageUtils page = coreOpenService.queryPage(params);

            return R.ok().put("page", page);
        }
        return R.parameterIsNul();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{openId}")
//    @RequiresPermissions("generator:coreopen:info")
    public R info(@PathVariable("openId") String openId){
		CoreOpenEntity coreOpen = coreOpenService.selectById(openId);

        return R.ok().put("coreOpen", coreOpen);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("generator:coreopen:save")
    public R save(@RequestBody CoreOpenEntity coreOpen){
		coreOpenService.insertOpen(coreOpen);
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String dateString = formatter.format(new Date());
		coreOpen.setCreateTime(dateString);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("generator:coreopen:update")
    public R update(@RequestBody CoreOpenEntity coreOpen){
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = date.format(new Date());
        coreOpen.setCreateTime(time);
		coreOpenService.updateById(coreOpen);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{openId}")
//    @RequiresPermissions("generator:coreopen:delete")
    public R delete(@PathVariable("openId") String openId){
		coreOpenService.deleteById(openId);

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
//		CategoryEntity categoryEntity = new CategoryEntity();
//		categoryEntity.setImage(filePath);
//		categoryEntity.setCreateTime(new Date());
//		categoryService.insert(categoryEntity);

        return R.ok().put("url","http://39.104.121.137:8080/ftp/" + dateStr + "/" + fileLocalName + suffix);
    }

}
