package com.orhonit.modules.generator.app.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orhonit.common.exception.RRException;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.app.config.UploadConfig;
import com.orhonit.modules.generator.app.dao.AppEverybodyShareDao;
import com.orhonit.modules.generator.app.entity.AppEverybodyShareEntity;
import com.orhonit.modules.generator.app.service.AppEverybodyShareService;
import com.orhonit.modules.oss.entity.SysOssEntity;
import com.orhonit.modules.oss.service.SysOssService;
import com.orhonit.modules.sys.controller.AbstractController;

/**
 * APP端大家来分享
 * @author YaoSC
 *
 */
@RestController
@RequestMapping("/app/everybodyshare")
public class AppEverybodyShareController extends AbstractController{
	
	
	@Autowired
	AppEverybodyShareService appEverybodyShareService;
	@Autowired	
    UploadConfig uploadConfig;
	@Autowired
	AppEverybodyShareDao appEverybodyShareDao;
	@Autowired
	SysOssService sysOssService;
	
	/**
	 * 保存
	 * @param appEverybodyShareEntity
	 * @return
	 */
	//@Login
	@PostMapping("/save")
	public R save(@RequestBody AppEverybodyShareEntity appEverybodyShareEntity) {
		appEverybodyShareEntity.setShareId(getUserId().intValue());
		appEverybodyShareEntity.setShareCreateTime(new Date());
		appEverybodyShareService.insertEveryBody(appEverybodyShareEntity);
		return R.ok().put("url",appEverybodyShareEntity.getShareUrl());
	}
	
	/**
	 * 分享列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String,Object>params) {
		PageUtils page =appEverybodyShareService.queryPage(params);
		return R.ok().put("page", page);
	}
	
	/**
	  * 我的分享列表
	 * @param params
	 * @return
	 */
	@GetMapping("/Mylist")
	public R Mylist(@RequestParam Map<String,Object>params) {
		params.put("shareUserId", getUserId());
		PageUtils page =appEverybodyShareService.MylistPage(params);
		return R.ok().put("page", page);
	}
	
	
	/**
	 * 上传文件
	 */
	@PostMapping("/upload")
	//@RequiresPermissions("sys:oss:all")
	public R upload(@RequestParam("file") MultipartFile file ) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}

		//上传文件 后缀
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String originalName=file.getOriginalFilename();
		//String fileLocalName = System.currentTimeMillis() + "";
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
			File f = new File(filePath + originalName);
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
			e.printStackTrace();
			return R.error();
		}
		//保存文件信息
		 SysOssEntity ossEntity = new SysOssEntity();
		 ossEntity.setUrl(filePath);
		 ossEntity.setCreateDate(new Date());
		 sysOssService.insert(ossEntity);
		//appFsoulFileService.insertFsoulFileEntity(fileType);
		return R.ok().put("url","http://110.19.104.227:20020/api/image/" + dateStr + "/" + originalName );
	}
	
	
	/**
	  * 下载
	 * @param fileName
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/download",method = RequestMethod.GET)
	public R download( String fileName,HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		if(fileName!=null) {
			//AppEverybodyShareEntity entity =appEverybodyShareDao.selectEverBodyById(shareId);
			//设置文件路径
	        String realPath = "D:\\image\\";
	        String realPathFIleName=realPath+fileName;
			//String realPathFIleName=entity.getShareUrl();
	        File file = new File(realPathFIleName);
	        // 如果文件名存在，则进行下载
	        if(file.exists()) {
	        	 // 配置文件下载
	        	response.setCharacterEncoding("utf-8");
	        	response.addHeader("content-type", "application/json");
                response.setContentType("text/html;charset=utf-8");
                // 下载文件能正常显示中文
               // response.setHeader("Content-Disposition","attachment;filename=" + new String(fileName.getBytes("UTF-8"),"UTF-8"));
                // 实现文件下载
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("成功下载!");
                }
                catch (Exception e) {
                    System.out.println("下载失败!");
                }
                finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
	        }
		}
		return null;
	}
}
