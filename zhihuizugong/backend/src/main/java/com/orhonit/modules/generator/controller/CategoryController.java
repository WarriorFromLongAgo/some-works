package com.orhonit.modules.generator.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
//import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orhonit.common.exception.RRException;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.entity.CategoryEntity;
import com.orhonit.modules.generator.service.CategoryService;
import com.orhonit.modules.generator.vo.NewsModelTreeVo;




/**
 * 栏目类别表
 *
 * @author zy
 * @date 2019-04-12 14:32:36
 */
@RestController
@RequestMapping("generator/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired	
    private UploadConfig uploadConfig;

    /**
     * 栏目列表
     */
    @GetMapping("/getNewsModelTree")
//    @RequiresPermissions("generator:category:list")
    public R list(){
        List<NewsModelTreeVo> getCategoryTree = categoryService.getCategoryList();
        return R.ok().put("getCategoryTree", getCategoryTree);
    }


    /**
     * 查看栏目详细
     */
    @GetMapping("/info/{catId}")
//    @RequiresPermissions("generator:category:info")
    public R info(@PathVariable("catId") String catId){
		CategoryEntity category = categoryService.selectById(catId);
        return R.ok().put("category", category);
    }

    /**
     * 添加栏目
     */
    @PostMapping("/save")
//    @RequiresPermissions("generator:category:save")
    public R save(@RequestBody CategoryEntity category){
    	category.setIsmenu(1);
    	category.setDeleteFlag(2);
		categoryService.insert(category);
        return R.ok();
    }

    /**
     * 修改栏目
     */
    @PostMapping("/update")
//    @RequiresPermissions("generator:category:update")
    public R update(@RequestBody CategoryEntity category){
    	category.setUpdateTime(new Date());
		categoryService.updateById(category);
        return R.ok();
    }

    /**
     * 删除栏目
     */
    @DeleteMapping("/delete")
//    @RequiresPermissions("generator:category:delete")
    public R delete(@RequestBody Integer[] catId){
		categoryService.deleteBatchIds(Arrays.asList(catId));
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
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setImage(filePath);
		categoryEntity.setCreateTime(new Date());
		categoryService.insert(categoryEntity);

		return R.ok().put("url","http://39.104.121.137:8080/ftp/" + dateStr + "/" + fileLocalName + suffix);
	}

}
