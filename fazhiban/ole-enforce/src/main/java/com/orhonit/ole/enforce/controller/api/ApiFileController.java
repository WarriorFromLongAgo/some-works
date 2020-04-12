package com.orhonit.ole.enforce.controller.api;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orhonit.ole.common.annotation.LogAnnotation;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.FileUtil;
import com.orhonit.ole.common.utils.JaveUtil;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.common.utils.SysUtil;
import com.orhonit.ole.enforce.config.UploadConfig;
import com.orhonit.ole.enforce.entity.AttachFileEntity;
import com.orhonit.ole.enforce.entity.DocContentEntity;
import com.orhonit.ole.enforce.entity.DocTemplateEntity;
import com.orhonit.ole.enforce.exception.EnforceException;
import com.orhonit.ole.enforce.repository.AttachFileRepository;
import com.orhonit.ole.enforce.repository.DocContentRepository;
import com.orhonit.ole.enforce.service.DocTemplateService;
import com.orhonit.ole.enforce.service.casedoc.DocContentService;
import com.orhonit.ole.enforce.utils.FileToPdf;
import com.orhonit.ole.sys.config.ThreadLocalVariables;
import com.orhonit.ole.sys.dto.AppBannerDTO;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.AppBannerService;
import com.orhonit.ole.sys.service.UserService;

import io.swagger.annotations.ApiOperation;

/**
 * APP查看附件、文书控制器
 * 
 * @author ebusu
 *
 */
@RestController
@RequestMapping("/api/file")
public class ApiFileController {

	@Autowired
	private AttachFileRepository attachFileRepository;
	
	@Autowired
	private UploadConfig uploadConfig;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DocContentService docContentService;
	
	@Autowired
	private DocTemplateService docTemplateService;
	
	@Autowired 
	private DocContentRepository docContentRepository;

	@Autowired
	private AppBannerService appBannerService;
	
	@Value("${files.punishBill}")
	private String filesPath;
	
	@Value("${upload.serverUrl}")
	private String serverUrl;

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
	 * 上传附件
	 * 
	 * @param file
	 * @param request
	 */

	@PostMapping(value = "/upload")
	public Result<Object> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		// 创建fileEntity
		AttachFileEntity attachFileEntity = new AttachFileEntity();
		attachFileEntity.setId(UUID.randomUUID().toString());
		// 获取登录人
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());
		
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
			return ResultUtil.toResponseWithMsg(ResultCode.APP_SAVE_ERROR.getCode(), "文件上传失败！");
		}
		// 保存上传信息到数据库
		attachFileRepository.save(attachFileEntity);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS,"文件上传成功！");
	}

	@GetMapping(value = "/getDocDetailPDF")
	public Result<Object> uploadFile(
			@RequestParam(value = "caseId", required = false) String caseId,
			@RequestParam(value = "templateId" , required = false) String templateId,
			@RequestParam(value = "contentId" , required = false) String contentId) {
		try {
			DocTemplateEntity docTemplateEntity = this.docTemplateService.findOne(templateId);
			DocContentEntity resDocContentEntity = this.docContentRepository.findOne(contentId);
			
			if ( resDocContentEntity == null ) {
				throw new EnforceException(ResultCode.INTERNAL_SERVER_ERROR.getCode(), caseId + ", " + templateId + " is null");
			}

			// 获取模板内容
			Document document = Jsoup.parse(StringEscapeUtils.unescapeHtml4(docTemplateEntity.getContent()));

			// 文书上需要填写内容的模块放到上面
			Map<String, String> map = new HashMap<>();
			map.put("datetime", "");
			map.put("templateName", docTemplateEntity.getName());

			String htmlContent = this.docContentService.getHtmlContent(templateId, caseId, contentId,document);
			
			htmlContent = htmlContent.replaceAll("<br>", "");
			
			document = Jsoup.parse(StringEscapeUtils.unescapeHtml4(htmlContent));
			
			document.head().html("<style>@page{margin:0;width:659px;}table,table tr th, table tr td { border:1px solid #000000;font-family:Simsun; }"
					+ "table { width:  100%; text-align: center; border-collapse: collapse;}</style>");
			String downloadpdf = System.currentTimeMillis() + ".pdf";
			String docName,pdfName;
			if(SysUtil.sysIsWin()){
				new File(uploadConfig.getWinTemp()).mkdirs();
				docName = uploadConfig.getWinTemp() + System.currentTimeMillis() + ".doc";
				pdfName = uploadConfig.getWinTemp() + downloadpdf;
			}else{
				new File(uploadConfig.getOtherTemp()).mkdirs();
				docName = uploadConfig.getOtherTemp() + System.currentTimeMillis() + ".doc";
				pdfName = uploadConfig.getOtherTemp() + downloadpdf;
			}
			FileToPdf.htmlToDoc(document.toString(), docName);
			FileToPdf.DocToPdf(docName, pdfName);
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS,uploadConfig.getServerUrl()+"file/temp/"+downloadpdf);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.toResponseWithMsg(ResultCode.APP_TEMP_DOWNLOAD_ERROR.getCode(), "下载文书失败");
		}
	}
	
	/**
	 * APP轮播图获取
	 * @param roleId
	 * */
	@GetMapping(value = "/getBanner")
	public Result<Object> getBanner(@RequestParam(value = "roleId", required = false) Integer roleId) {
		
		if(StringUtils.isEmpty(roleId)){
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "roleId is null");
		}
		
		List<AppBannerDTO> list = this.appBannerService.findByRoleId(roleId);
		
		String filePath,fileName;
		
		for(int i=0;i<list.size();i++){
			fileName=list.get(i).getUrl();
			if(SysUtil.sysIsWin()){
				filePath = uploadConfig.getServerUrl()+ "file"+fileName;
			}else{
				filePath = uploadConfig.getServerUrl()+ "file"+fileName;
			}
			list.get(i).setUrl(filePath);
		}
		
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS,list);
	}
	
	/**
	 * 保存缴费凭证
	 * @param file
	 * @return
	 * @throws IOException
	 */

	@PostMapping("/punishImg")
	public Result<Object> punishImg(@RequestParam("file")MultipartFile file) throws IOException {
		Map<String,String> res = new HashMap<String,String>();
		String fileOrigName = file.getOriginalFilename();
		fileOrigName = fileOrigName.substring(fileOrigName.lastIndexOf("."));
		String md5 = FileUtil.fileMd5(file.getInputStream());
		String pathname = "/"+md5+System.currentTimeMillis()+fileOrigName;
		res.put("punishBill", pathname);
		String fullPath = filesPath + pathname;
		res.put("url", serverUrl+"file/"+filesPath.split("/")[filesPath.split("/").length-1]+pathname);
		FileUtil.saveFile(file, fullPath);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS,res);
	}
}
