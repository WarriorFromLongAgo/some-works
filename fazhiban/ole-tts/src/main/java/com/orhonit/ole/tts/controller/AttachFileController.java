package com.orhonit.ole.tts.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.FileUtil;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.common.utils.SysUtil;
import com.orhonit.ole.sys.model.SysDictEntity;
import com.orhonit.ole.sys.repository.SysDictRepository;
import com.orhonit.ole.tts.config.UploadConfig;
import com.orhonit.ole.tts.dto.AttachFileDTO;
import com.orhonit.ole.tts.entity.AttachFileEntity;
import com.orhonit.ole.tts.repository.AttachFileRepository;
import com.orhonit.ole.tts.service.file.AttachFileService;

/**
 * 附件管理
 * 
 * @author liuzhih
 *
 */
@RestController
@RequestMapping("/attachFile")
@SuppressWarnings("serial")
public class AttachFileController {

	@Autowired
	private AttachFileService attachFileService;

	@Autowired
	AttachFileRepository attachFileRepository;
	
	@Autowired
	SysDictRepository sysDictRepository;
	
	
	@Autowired
	private UploadConfig uploadConfig;

	// 可转换的文件类型列表
	HashMap<String, List<String>> fileTypeMap = new HashMap<String, List<String>>() {
		{
			put("doc", new ArrayList<String>() {
				{
					add("doc");
					add("docx");
					add("txt");
					add("xls");
					add("xlsx");
				}
			});
			put("img", new ArrayList<String>() {
				{
					add("jpg");
					add("jpeg");
					add("png");
					add("gif");
					add("bmp");
				}
			});
			put("video", new ArrayList<String>() {
				{
					add("mp4");
				}
			});
		}
	};
	
	/**
	 * 获取附件列表
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/list")
	public TableResponse<AttachFileDTO> listDocTemplate(TableRequest request,HttpServletRequest srequest) {
		Map<String, SysDictEntity> dictMap = new HashMap<String, SysDictEntity>();
		sysDictRepository.findByTypeValueOrderBySortAsc(CommonParameters.DictType.CASE_STATUS).forEach(dict->{
			dictMap.put(dict.getEnumValue(), dict);
		});
		
		sysDictRepository.findByTypeValueOrderBySortAsc(CommonParameters.DictType.DAILY_CHECK_STATUS).forEach(dict->{
			dictMap.put(dict.getEnumValue(), dict);
		});
		
		sysDictRepository.findByTypeValueOrderBySortAsc(CommonParameters.DictType.CHECK_STATUS).forEach(dict->{
			dictMap.put(dict.getEnumValue(), dict);
		});
		
		Map<String,Object> params=request.getParams();
		if(!StringUtils.isEmpty(srequest.getParameter("caseId"))){
			params.put("caseId", srequest.getParameter("caseId"));
		}
		if(!StringUtils.isEmpty(srequest.getParameter("caseStatus"))){
			params.put("caseStatus", srequest.getParameter("caseStatus"));
		}
		if(!StringUtils.isEmpty(srequest.getParameter("fileName"))){
			params.put("fileName", srequest.getParameter("fileName"));
		}
		Page<AttachFileEntity> page = this.attachFileService.getFileList(params, request.getStart(),
				request.getLength());
		page.forEach(item->{
			if(dictMap.get(item.getCaseStatus()) != null){
				item.setCaseStatus(dictMap.get(item.getCaseStatus()).getEnumDesc());
			}
		});
		return TableRequestHandler.<AttachFileDTO> builder().countHandler(new CountHandler() {
			@Override
			public int count(TableRequest request) {
				return Long.valueOf(page.getTotalElements()).intValue();
			}
		}).listHandler(new ListHandler<AttachFileDTO>() {
			@Override
			public List<AttachFileDTO> list(TableRequest request) {
				return page.getContent().stream().map(e -> {
					AttachFileDTO attachFileDTO = new AttachFileDTO();
					BeanUtils.copyProperties(e, attachFileDTO);
					return attachFileDTO;
				}).collect(Collectors.toList());
			}
		}).build().handle(request);
	}

	/**
	 * 删除附件
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/del")
	public Result<Object> delFile(HttpServletRequest request) {
		String id = request.getParameter("id");
		AttachFileEntity attachFileEntity = attachFileRepository.findOne(id);
		FileUtil.deleteFile(attachFileEntity.getFilePath() + attachFileEntity.getFileNewname() + "." + attachFileEntity.getFileType());
		if (fileTypeMap.get("doc").contains(attachFileEntity.getFileType().toLowerCase())) {
			FileUtil.deleteFile(attachFileEntity.getPdfUrl() + attachFileEntity.getFileNewname() + ".pdf");
		}
		attachFileRepository.delete(id);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, null);
	}

	
	/**
	 * 预览附件
	 * 
	 * @param request
	 */
	@GetMapping(value = "/show")
	public Result<Object> showFile(HttpServletRequest request) {
		String id = request.getParameter("id");
		String filePathName = null;
		AttachFileEntity attachFileEntity = attachFileRepository.findOne(id);
		//根据文件类型取值	
		if (fileTypeMap.get("doc").contains(attachFileEntity.getFileType().toLowerCase())) {
			filePathName = attachFileEntity.getPdfUrl() + attachFileEntity.getFileNewname() + ".pdf";
		}else if(fileTypeMap.get("img").contains(attachFileEntity.getFileType().toLowerCase())){
			filePathName = attachFileEntity.getFilePath() + attachFileEntity.getFileNewname() + "." + attachFileEntity.getFileType();
		}else if(fileTypeMap.get("video").contains(attachFileEntity.getFileType().toLowerCase())){
			filePathName = attachFileEntity.getFilePath() + attachFileEntity.getFileNewname() + "." + attachFileEntity.getFileType();
		}
		if(SysUtil.sysIsWin()){
			filePathName = uploadConfig.getServerUrl()+ "file/"+filePathName.substring(uploadConfig.getWinPath().length()).replaceAll("\\\\+", "/");
		}else{
			filePathName = uploadConfig.getServerUrl()+ "file/"+filePathName.substring(uploadConfig.getOtherPath().length()).replaceAll("\\\\+", "/");
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, filePathName);
	}
	
	/*
	@GetMapping(value = "/show")
	public void showFile(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String filePathName = null;
		AttachFileEntity attachFileEntity = attachFileRepository.findOne(id);
		//根据文件类型取值	
		if (fileTypeMap.get("doc").contains(attachFileEntity.getFileType().toLowerCase())) {
			filePathName = attachFileEntity.getPdfUrl() + attachFileEntity.getFileNewname() + ".pdf";
			response.setContentType("application/pdf");
		}else if(fileTypeMap.get("img").contains(attachFileEntity.getFileType().toLowerCase())){
			filePathName = attachFileEntity.getFilePath() + attachFileEntity.getFileNewname() + "." + attachFileEntity.getFileType();
			response.setContentType("image/jpeg");
		}else if(fileTypeMap.get("video").contains(attachFileEntity.getFileType().toLowerCase())){
			filePathName = attachFileEntity.getFilePath() + attachFileEntity.getFileNewname() + "." + attachFileEntity.getFileType();
			response.setContentType("video/mpeg4");
		}
		FileInputStream fileinputstream = null;
		try {
			File file = new File(filePathName);
			fileinputstream = new FileInputStream(file);
			long l = file.length();
			int k = 0;
			byte abyte0[] = new byte[65000];
			response.setContentLength((int) l);
			response.setHeader("Content-Disposition","inline; filename=" + attachFileEntity.getFileNewname());
			while ((long) k < l) {
				int j;
				j = fileinputstream.read(abyte0, 0, 65000);
				k += j;
				response.getOutputStream().write(abyte0, 0, j);
			}
			
		} catch (IOException e) {
			System.out.println("前端页面已断开连接");
		}finally {
			try {
				fileinputstream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/

	/**
	 * 下载附件
	 * 
	 * @param request
	 */
	@GetMapping(value = "/download")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		AttachFileEntity attachFileEntity = attachFileRepository.findOne(id);
		try {
			File file = new File(attachFileEntity.getFilePath() + attachFileEntity.getFileNewname() + "."
					+ attachFileEntity.getFileType());
			FileInputStream fileinputstream = new FileInputStream(file);
			long l = file.length();
			int k = 0;
			byte abyte0[] = new byte[65000];
			response.setContentLength((int) l);
			response.setHeader("Content-Disposition",
					"attachment; filename=" + new String(
							(attachFileEntity.getFileName() + "." + attachFileEntity.getFileType()).getBytes(),
							"iso-8859-1"));
			while ((long) k < l) {
				int j;
				j = fileinputstream.read(abyte0, 0, 65000);
				k += j;
				response.getOutputStream().write(abyte0, 0, j);
			}
			fileinputstream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取附件列表
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/caseFileList/{caseId}")
	public Result<List<AttachFileEntity>> caseFileList(@PathVariable String caseId) {
		if(caseId!=null && !"".equals(caseId)){
			List<AttachFileEntity> entitys = attachFileRepository.findAllByCaseId(caseId);
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, entitys);
		}
		return ResultUtil.toResponseWithData(ResultCode.ERROR, null);
	}
}
