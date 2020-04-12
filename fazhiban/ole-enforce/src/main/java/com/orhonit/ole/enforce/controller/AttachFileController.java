package com.orhonit.ole.enforce.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.FileUtil;
import com.orhonit.ole.common.utils.JaveUtil;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.common.utils.SysUtil;
import com.orhonit.ole.enforce.config.UploadConfig;
import com.orhonit.ole.enforce.dto.AttachFileDTO;
import com.orhonit.ole.enforce.entity.AttachFileEntity;
import com.orhonit.ole.enforce.repository.AttachFileRepository;
import com.orhonit.ole.enforce.service.file.AttachFileService;
import com.orhonit.ole.enforce.utils.FileToPdf;
import com.orhonit.ole.sys.model.SysDictEntity;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.repository.SysDictRepository;
import com.orhonit.ole.sys.service.SysDictService;
import com.orhonit.ole.sys.utils.UserUtil; 


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
	SysDictService sysDictService;

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
					add("mp3");
				}
			});
			put("pdf", new ArrayList<String>() {
				{
					add("pdf");
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
	 * 上传附件
	 * 
	 * @param file
	 * @param request
	 */

	@PostMapping(value = "/upload")
	public void uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		// 创建fileEntity
		AttachFileEntity attachFileEntity = new AttachFileEntity();
		attachFileEntity.setId(UUID.randomUUID().toString());
		// 获取登录人
		User user = UserUtil.getCurrentUser();
		attachFileEntity.setCreateName(user.getNickname());
		attachFileEntity.setCreateBy(user.getUsername());

		// 案件编号
		String caseNum = request.getParameter("caseNum");
		String caseId = request.getParameter("caseId");
		String caseStatus = request.getParameter("caseStatus");
		attachFileEntity.setCaseNum(caseNum);
		attachFileEntity.setCaseId(caseId);
		attachFileEntity.setCaseStatus(caseStatus);
		// 文件类型
		String[] temp = file.getOriginalFilename().split("\\.");
		String fileType = temp[temp.length - 1];
		attachFileEntity.setFileType(fileType);
		// 原文件名
		String fileName = file.getOriginalFilename().substring(0,
				file.getOriginalFilename().length() - fileType.length() - 1);
		attachFileEntity.setFileName(fileName);
		attachFileEntity.setFileTitle(fileName);
		// 新文件名
		String fileLocalName = System.currentTimeMillis() + "";
		attachFileEntity.setFileNewname(fileLocalName);
		// 生成日期
		Date date = new Date();
		SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMdd");
		String dateStr = dformat.format(date);
		attachFileEntity.setCreateDate(date);
		String filePath,pdfPath;
		if(SysUtil.sysIsWin()){
			// 文件保存路径
			filePath = uploadConfig.getWinPath() + "upload\\\\" + dateStr + "\\" + caseNum + "\\";
			// pdf文件保存路径
			pdfPath = uploadConfig.getWinPath() + "pdf\\" + dateStr + "\\" + caseNum + "\\";
		}else{
			// 文件保存路径
			filePath = uploadConfig.getOtherPath() + "upload/" + dateStr + "/" + caseNum + "/";
			// pdf文件保存路径
			pdfPath = uploadConfig.getOtherPath() + "pdf/" + dateStr + "/" + caseNum + "/";
		}
		attachFileEntity.setFilePath(filePath);
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

			// 如果上传的文件类型为：doc/docx/txt/xls/xslx则转换为PDF
			if (fileTypeMap.get("doc").contains(fileType.toLowerCase())) {
				// 创建目录
				File pdfdir = new File(pdfPath);
				pdfdir.mkdirs();

				attachFileEntity.setPdfUrl(pdfPath);

				switch (fileType.toLowerCase()) {
				case "doc":
				case "docx":
					FileToPdf.DocToPdf(filePath + fileLocalName + "." + fileType, pdfPath + fileLocalName + ".pdf");
					break;
				case "txt":
					FileToPdf.TxtToPdf(filePath + fileLocalName + "." + fileType, pdfPath + fileLocalName + ".pdf");
					break;
				case "xls":
				case "xlsx":
					FileToPdf.XlsxToPdf(filePath + fileLocalName + "." + fileType, pdfPath + fileLocalName + ".pdf");
					break;
				case "mp4":
					JaveUtil.saveSmallImg(filePath + fileLocalName + "." + fileType);
					break;
				default:
					break;
				}
			}else if (fileTypeMap.get("video").contains(fileType.toLowerCase())) {
				// 如果上传的文件类型为：MP4生成缩略图
				// 创建目录
				switch (fileType.toLowerCase()) {
				case "mp4":
					JaveUtil.saveSmallImg(filePath + fileLocalName + "." + fileType);
					break;
				default:
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 保存上传信息到数据库
		attachFileRepository.save(attachFileEntity);
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
		}else if(fileTypeMap.get("pdf").contains(attachFileEntity.getFileType().toLowerCase())){
			filePathName = attachFileEntity.getFilePath() + attachFileEntity.getFileNewname() + "." + attachFileEntity.getFileType();
		}
		if(SysUtil.sysIsWin()){
			filePathName = uploadConfig.getServerUrl()+ "file/"+filePathName.substring(uploadConfig.getWinPath().length()).replaceAll("\\\\+", "/");
		}else{
			filePathName = uploadConfig.getServerUrl()+ "file/"+filePathName.substring(uploadConfig.getOtherPath().length()).replaceAll("\\\\+", "/");
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, filePathName);
	}

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
			Sort sort = new Sort(Sort.Direction.DESC,"createDate");
			List<AttachFileEntity> entitys = attachFileRepository.findAllByCaseId(caseId,sort);
			Map<String,String> dict = null;
			Map<String,String> CFdict = sysDictService.getDictMapByTypeValue(CommonParameters.DictType.CASE_STATUS);
			Map<String,String> RCdict = sysDictService.getDictMapByTypeValue(CommonParameters.DictType.DAILY_CHECK_STATUS);
			Map<String,String> ZXJCdict = sysDictService.getDictMapByTypeValue(CommonParameters.DictType.CHECK_STATUS);
			for (AttachFileEntity attachFileEntity : entitys) {
				if("CF".equals(attachFileEntity.getCaseNum().split("-")[0])) {
					dict = CFdict;
				}else if("RC".equals(attachFileEntity.getCaseNum().split("-")[0])) {
					dict = RCdict;
				}else if("ZXJC".equals(attachFileEntity.getCaseNum().split("-")[0])) {
					dict = ZXJCdict;
				}
				//根据文件类型取值	
				String filePathName = null;
				if (fileTypeMap.get("doc").contains(attachFileEntity.getFileType().toLowerCase())) {
					filePathName = attachFileEntity.getPdfUrl() + attachFileEntity.getFileNewname() + ".pdf";
				}else if(fileTypeMap.get("img").contains(attachFileEntity.getFileType().toLowerCase())){
					filePathName = attachFileEntity.getFilePath() + attachFileEntity.getFileNewname() + "." + attachFileEntity.getFileType();
				}else if(fileTypeMap.get("video").contains(attachFileEntity.getFileType().toLowerCase())){
					filePathName = attachFileEntity.getFilePath() + attachFileEntity.getFileNewname() + "." + attachFileEntity.getFileType();
				}else if(fileTypeMap.get("pdf").contains(attachFileEntity.getFileType().toLowerCase())){
					filePathName = attachFileEntity.getFilePath() + attachFileEntity.getFileNewname() + "." + attachFileEntity.getFileType();
				}
				if(SysUtil.sysIsWin()){
					filePathName = uploadConfig.getServerUrl()+ "file/"+filePathName.substring(uploadConfig.getWinPath().length()).replaceAll("\\\\+", "/");
				}else{
					filePathName = uploadConfig.getServerUrl()+ "file/"+filePathName.substring(uploadConfig.getOtherPath().length()).replaceAll("\\\\+", "/");
				}
				attachFileEntity.setUrl(filePathName);
				attachFileEntity.setCaseStatus(dict.get(attachFileEntity.getCaseStatus()));
			}
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, entitys);
		}
		return ResultUtil.toResponseWithData(ResultCode.ERROR, null);
	}
}
