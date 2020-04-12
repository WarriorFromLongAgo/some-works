package com.orhonit.modules.enterprise.rest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orhonit.common.utils.DateUtils;
import com.orhonit.common.utils.R;
import com.orhonit.common.utils.SplitUtils;
import com.orhonit.modules.enterprise.entity.NopublicFile;
import com.orhonit.modules.enterprise.service.NopublicFileService;

/**
 * 文件上传controller
 * @author 	cyf
 * @date	2018/11/12 上午11:24:12
 */
@RestController
@RequestMapping("file")
public class FileController {
	
	@Value(value="${upload.filepath}")
	private String filepath;
	
	@Autowired
	private NopublicFileService fileService;
	
	/**
	 * 文件下载
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/upload",method=RequestMethod.GET)
	public R downFileById(@RequestParam(value="id") Long id,HttpServletResponse response) {
		if(null !=id) {
			NopublicFile fileEntity = fileService.selectById(id);
			if(null != fileEntity) {
				String filePathStr=fileEntity.getFilePath()+fileEntity.getFileNike()+"."+fileEntity.getSuffix();
				File filePath=new File(filePathStr);
				if(!filePath.exists()) {
					return R.error();
				}
				try {
					FileUtils.copyFile(filePath, response.getOutputStream());
				} catch (IOException e) {
					
				}
				return R.ok();
			}
		}
		return R.parameterIsNul();
	}
	
	
	/**
	 * 文件删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/upload/{id}",method=RequestMethod.DELETE)
	public R deleteFileById(@PathVariable(value="id") String ids) {
		if(null !=ids) {
			List<NopublicFile> fileEntity = fileService.selectBatchIds(SplitUtils.splitIds(ids, ","));
			if(null != fileEntity) {
				fileService.deleteBatchIds(SplitUtils.splitIds(ids, ","));
				for (NopublicFile nopublicFile : fileEntity) {
					String filePathStr=nopublicFile.getFilePath()+"\\"+nopublicFile.getFileNike()+"."+nopublicFile.getSuffix();
					File filePath=new File(filePathStr);
					try {
						filePath.delete();
					} catch (Exception e) {
						return R.error();
					}
					return R.ok();
				}
			}
		}
		return R.parameterIsNul();
	}
	
	/**
	 * 文件上传
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/upload",method=RequestMethod.POST)
	public R uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request,HttpServletResponse response) {
		if( 0 < file.getSize()) {
			String type=request.getParameter("type");
			String basePath=filepath;
			String originalFilename = file.getOriginalFilename();
			String[] temp = originalFilename.split("\\.");
			String suffix=temp[temp.length-1];
			String oldName=temp[0];
			String newName=System.currentTimeMillis()+"";
			NopublicFile fileEntity=new NopublicFile();
			fileEntity.setFileNike(newName);
			fileEntity.setFileName(oldName);
			fileEntity.setFilePath(filepath);
			fileEntity.setSuffix(suffix);
			fileEntity.setFileType(type);//企业0   民宗局1
			fileEntity.setCreateTime(DateUtils.getNowTime());
			String pathName =basePath+newName+"."+suffix;
			File filePath=new File(basePath);
			filePath.setWritable(true, false);
			if(!filePath.exists()) {
				filePath.mkdirs();
			}
			try {
				file.transferTo(new File(pathName));
				fileService.insert(fileEntity);
			} catch (Exception e) {
				return R.error();
			}
			try {
				Map<String, Object> result= new HashMap<String, Object>();
				result.put("file", fileEntity);
				return R.ok(result);
			} catch (Exception e) {
			}
		}
		return R.error("文件大小为空");
	}

}
