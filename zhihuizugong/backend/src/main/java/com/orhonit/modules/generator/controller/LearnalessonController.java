package com.orhonit.modules.generator.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.app.entity.AppFsoulFileEntity;
import com.orhonit.modules.generator.app.service.AppFsoulFileService;
import com.orhonit.modules.generator.entity.LearnalessonBookstoreEntity;
import com.orhonit.modules.generator.entity.OrganizationWorkshopEntity;
import com.orhonit.modules.generator.entity.SoulTypeEntity;
import com.orhonit.modules.generator.service.LearnalessonBookstoreService;
import com.orhonit.modules.generator.service.OrganizationWorkshopService;
import com.orhonit.modules.generator.service.SoulTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

/**
 * Modular:学一学 组工讲堂 组工书苑
 * 
 * @author YaoSC
 *
 */


@Api(tags="PC端学一学")
@RestController
@RequestMapping("learnalesson")
public class LearnalessonController {

	@Autowired
	OrganizationWorkshopService service;
	@Autowired
	LearnalessonBookstoreService bookeStoreService;
	@Autowired
	SoulTypeService soulTypeService;
	@Autowired
	AppFsoulFileService fsFileService;
	@Autowired
	private UploadConfig uploadConfig;
	@Resource
	@Qualifier("OrganizationRedisTemplate")
	private RedisTemplate<String,OrganizationWorkshopEntity>redisTemplate;

	/**
	 * 组工讲堂 发布
	 * 
	 * @param entity
	 * @return
	 */
	@ApiOperation("组工讲堂 发布接口")
	@RequestMapping("/organizationworkshop/save")
	public R save(@RequestBody OrganizationWorkshopEntity entity) {
		entity.setCreateTime(new Date());
		entity.setStudyNum(0);
		service.insert(entity);
		//redisTemplate.opsForValue().set("OrganizationWorkshopEntity", entity);  保存缓存
		return R.ok();

	}

	/**
	 * 组工讲堂 列表
	 */
	@RequestMapping("/organizationworkshop/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = service.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 组工讲堂 删除
	 * 
	 * @param courseId
	 * @return
	 */
	@RequestMapping("/organizationworkshop/delete")
	public R deleteShop(Integer courseId) {
		if (courseId > 0) {
			service.deleteWorkShop(courseId);
		} else {
			R.parameterIsNul();
		}
		return R.ok();
	}

	/**
	 * 组工讲堂 修改
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping("/organizationworkshop/update")
	public R updateShop(@RequestBody OrganizationWorkshopEntity entity) {
		service.updateShop(entity);
		return R.ok();
	}
	
	/*
	 * @RequestMapping("/get/info") public R
	 * inf1o(@RequestParam(required=false)Integer courseId) {
	 * ValueOperations<String, OrganizationWorkshopEntity> operations =
	 * redisTemplate.opsForValue(); String key = String.valueOf(courseId);
	 * OrganizationWorkshopEntity entity=null; boolean haskey =
	 * redisTemplate.hasKey(key); if(haskey) { entity= service.selectById(courseId);
	 * System.out.println("从缓存中获取数据"); System.out.println(entity); }
	 * operations.set(key,entity,5,TimeUnit.HOURS); return R.ok().put("t",entity);
	 * 
	 * }
	 */
	/**
	 * 组工书苑 发布
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping("/bookestore/save")
	public R saveBookStore(@RequestBody LearnalessonBookstoreEntity entity) {
		entity.setCreateTime(new Date());
		bookeStoreService.insert(entity);
		return R.ok();
	}

	/**
	 * 组工书苑 列表
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/bookestore/list")
	public R bookestorelist(@RequestParam Map<String, Object> params) {
		PageUtils page = bookeStoreService.queryPage(params);
		return R.ok().put("page", page);
	}

	/**
	 * 书苑 详情
	 * 
	 * @param bookstoreId
	 * @return
	 */
	@ApiModelProperty
	@ApiOperation("根据ID查询书苑的接口")
	@ApiImplicitParam(name="bookstoreId",value="书苑ID",dataType="int",defaultValue ="99",required=true)
	@RequestMapping("/bookestore/selectBookStoreById")
	public R selectBookStore(@RequestParam(required=false) Integer bookstoreId) {
		LearnalessonBookstoreEntity entity = null;
		if (bookstoreId > 0) {
			entity = bookeStoreService.selectStore(bookstoreId);
		} else {
			R.parameterIsNul();
		}
		return R.ok().put("LearnalessonBookstoreEntity", entity);
	}

	/**
	 * 书苑 单条删
	 * 
	 * @param bookstoreId
	 * @return
	 */
	@DeleteMapping("/bookestore/deleteBookStoreById")
	public R deleteBookStoreById(Integer bookstoreId) {
		if (bookstoreId > 0) {
			bookeStoreService.deleteStore(bookstoreId);
		} else {
			R.parameterIsNul();
		}
		return R.ok();
	}

	/**
	 * 书苑 单条修改
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping("/bookestore/updateBookeStoreById")
	public R updateBookeStore(@RequestBody LearnalessonBookstoreEntity entity) {
		bookeStoreService.updateStore(entity);
		return R.ok();
	}

	/**
	 * 添加 书籍分类
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping("/type/bookstore/inserto")
	public R Typeinsert(@RequestBody SoulTypeEntity entity) {
		entity.setCreateTime(new Date());
		if (entity.getTypeIdentifier() == CommonParameters.TypeSoul.BOOKSTORE_TYPE) {
			entity.setTypeIdentifier(CommonParameters.TypeSoul.BOOKSTORE_TYPE);
		} else {
			entity.setTypeIdentifier(CommonParameters.TypeSoul.VIDEO_TYPE);
		}
		soulTypeService.insert(entity);
		return R.ok();
	}

	/**
	 * 书籍分类 列表
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/type/bookestore/typelist")
	public R typelist(@RequestParam Map<String, Object> params) {
		PageUtils page = soulTypeService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 书籍分类 删除
	 * 
	 * @param typeId
	 * @return
	 */
	@RequestMapping("/type/bookestore/deletetype")
	public R deleteType(@RequestParam Integer typeId) {
		if (typeId > 0) {
			System.out.println("typeId=" + typeId);
			soulTypeService.deleteType(typeId);
		} else {
			R.parameterIsNul();
		}
		return R.ok();
	}

	@GetMapping("/type/bookestore/info/{typeId}")
	public R info(@PathVariable("typeId") Integer typeId) {
		SoulTypeEntity entity = null;
		if (typeId > 0) {
			entity = soulTypeService.selectById(typeId);
		} else {
			return R.parameterIsNul();
		}
		return R.ok().put("SoulTypeEntity", entity);

	}

	/**
	 * 书籍分类 修改
	 * 
	 * @param entity
	 * @return
	 */
	@PutMapping("/type/bookestore/updatetype")
	public R updateType(@RequestBody SoulTypeEntity entity) {
		soulTypeService.updateType(entity);
		return R.ok();
	}

	/**
	 * 上传文件
	 */
	//@SuppressWarnings({ "unlikely-arg-type", "static-access" })
	@PostMapping("/upload")
	// @RequiresPermissions("sys:oss:all")
	public R upload(@RequestParam("file") MultipartFile file, Integer ORGANIZATIONWORKSHOP, String fileType)
			throws Exception {
		//Encoder encoder = new Encoder();
		String shichang = "";
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}
		// 上传文件 后缀
		 String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		//原文件名
		String originalName = file.getOriginalFilename();
		//新文件名
		String newFileName =  System.currentTimeMillis() + suffix;
		/*
		 * try { if (ORGANIZATIONWORKSHOP ==
		 * CommonParameters.ProjectModeules.LEARNALESSON_ORGANIZATIONWORKSHOP) { //
		 * 获取文件类型 String fileName = file.getContentType(); // 获取文件后缀 String pref =
		 * fileName.indexOf("/") != -1 ? fileName.substring(fileName.lastIndexOf("/") +
		 * 1, fileName.length()) : null; String prefix = "." + pref; //
		 * 用uuid作为文件名，防止生成的临时文件重复 final File excelFile =
		 * File.createTempFile(UUID.randomUUID().toString(), prefix);
		 * file.transferTo(excelFile); MultimediaInfo m = encoder.getInfo(excelFile);
		 * long ls = m.getDuration() / 1000; shichang = String.valueOf(ls); //
		 * 程序结束时，删除临时文件 //this.deleteFile(excelFile); } } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		Date date = new Date();
		SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMdd");
		String dateStr = dformat.format(date);
		String filePath = uploadConfig.getPath() + dateStr + "//";
		try {
			// 创建目录
			File dir = new File(filePath);
			dir.mkdirs();
			// 获取文件输入流
			InputStream in = file.getInputStream();
			// 创建文件并保存
			File f = new File(filePath + newFileName);
			FileOutputStream fos = new FileOutputStream(f);
			byte[] b = new byte[1024];
			int n = 0;
			while ((n = in.read(b)) != -1) {
				fos.write(b, 0, n);
			}
			fos.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return R.error();
		}
		// 保存文件信息
		AppFsoulFileEntity soulFileEntity = new AppFsoulFileEntity();
		soulFileEntity.setFsoulFileId(UUID.randomUUID().toString().replaceAll("-", ""));
		soulFileEntity.setFsoulFileUrl(filePath);
		soulFileEntity.setFsoulCreateTime(new Date());
		if (ORGANIZATIONWORKSHOP == 3) {
			soulFileEntity.setFsoulModularType(CommonParameters.ProjectModeules.LEARNALESSON_ORGANIZATIONWORKSHOP);
			soulFileEntity.setFileType(CommonParameters.AppEverBodyShareL.file_video);
		} else {
			soulFileEntity.setFsoulModularType(CommonParameters.ProjectModeules.LEARNALESSON_BOOKESTORE);
			if (fileType.equals(CommonParameters.AppEverBodyShareL.file_picture)) {
				soulFileEntity.setFileType(CommonParameters.AppEverBodyShareL.file_picture);
			}
			soulFileEntity.setFileType(CommonParameters.AppEverBodyShareL.file_other);
		}
		soulFileEntity.setFsoulOriginalFileName(originalName);
		soulFileEntity.setNewFileName(newFileName);
		fsFileService.insert(soulFileEntity);
		// appFsoulFileService.insertFsoulFileEntity(fileType);
		//return R.ok().put("url", "http://110.19.104.227:20021/api/image/" + dateStr + "/" + originalName).put("courseTime",
		//		shichang);
		if(ORGANIZATIONWORKSHOP == 3) {
			//return R.ok().put("url", "http://110.19.104.227:20021/ipb/" + dateStr + "/" + newFileName+suffix).put("courseTime",
				//	shichang);
			return R.ok().put("url", "http://110.19.104.227:20020/api/image/" + dateStr + "/" + newFileName).put("courseTime",
					shichang);
		}else {
			return R.ok().put("url", "http://110.19.104.227:20020/api/image/" + dateStr + "/" + newFileName);
		}
		
	}

	/**
	 * 计算视频时长
	 * 
	 * @param source
	 * @return
	 */
	@SuppressWarnings("unused")
	private String ReadVideoTime(File source) {
		Encoder encoder = new Encoder();
		String shichang = "";
		try {
			MultimediaInfo m = encoder.getInfo(source);
			long ls = m.getDuration() / 1000;
			int hour = (int) (ls / 3000);
			int minute = (int) (ls % 3600) / 60;
			int second = (int) (ls - hour * 3600 - minute * 60);
			shichang = hour + "'" + minute + "''" + second + "'''";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return shichang;
	}

	/**
	 * 将时长计算完后 删除临时文件
	 * 
	 * @param files
	 */
	private static void deleteFile(File... files) {
		for (File file : files) {
			if (file.exists()) {
				file.delete();
			}
		}
	}
}
