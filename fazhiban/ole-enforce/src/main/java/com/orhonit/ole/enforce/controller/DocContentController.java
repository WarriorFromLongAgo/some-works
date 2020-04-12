package com.orhonit.ole.enforce.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.JsonUtil;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dto.DocContentDTO;
import com.orhonit.ole.enforce.entity.DocContentEntity;
import com.orhonit.ole.enforce.entity.DocTemplateEntity;
import com.orhonit.ole.enforce.exception.EnforceException;
import com.orhonit.ole.enforce.repository.DocContentRepository;
import com.orhonit.ole.enforce.service.DocTemplateService;
import com.orhonit.ole.enforce.service.casedoc.DocContentService;
import com.orhonit.ole.enforce.utils.FileToPdf;

/**
 * 模板内容
 * @author ebusu
 *
 */
@RestController
@RequestMapping("/docContent")
public class DocContentController {

	@Autowired
	private DocTemplateService docTemplateService;

	@Autowired
	private DocContentService docContentService ;
	
	@Autowired 
	private DocContentRepository docContentRepository;

	/**
	 * 根据模板ID获取输入框以及输入框内容
	 * 
	 * @param templateId
	 */
	@GetMapping("/content")
	public Result<Object> getTemplateContentById(@RequestParam String templateId, @RequestParam String caseId, @RequestParam Boolean newOne) {
		DocTemplateEntity docTemplateEntity = docTemplateService.findOne(templateId);

		// 获取模板内容
		Document document = Jsoup.parse(StringEscapeUtils.unescapeHtml4(docTemplateEntity.getContent()));
		
		List<DocContentEntity> docContentEntitys = this.docContentRepository.findByTemplateIdAndCaseId(templateId, caseId);
		List<Map<String, String>> maps = new ArrayList<>();
		if(docContentEntitys.size()==0 || newOne) {
			// 最终要返回给前台的html代码
			StringBuilder stringBuilder = new StringBuilder();	
			// 文书上需要填写内容的模块放到上面
			Map<String, String> map = new HashMap<>();
			map.put("datetime", "");
			map.put("templateName", docTemplateEntity.getName());
			this.docContentService.createHtml(stringBuilder, document, caseId, map, templateId,new HashMap<>());
			// 返回到前台
			map.put("htmlContent", stringBuilder.toString());
			maps.add(map);
		}else {
			for (DocContentEntity docContentEntity : docContentEntitys) {
				// 最终要返回给前台的html代码
				StringBuilder stringBuilder = new StringBuilder();	
				Map<String, String> jsonValueMap = null;
				if (docContentEntity == null) {
					jsonValueMap = new HashMap<>();
				} else {
					jsonValueMap = JsonUtil.fromJson(docContentEntity.getValue(), HashMap.class);
				}
				// 文书上需要填写内容的模块放到上面
				Map<String, String> map = new HashMap<>();
				map.put("datetime", "");
				map.put("contentId", docContentEntity.getId());
				map.put("templateName", docTemplateEntity.getName());
				this.docContentService.createHtml(stringBuilder, document, caseId, map, templateId,jsonValueMap);
				// 返回到前台
				map.put("htmlContent", stringBuilder.toString());
				maps.add(map);
			}
		}
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, maps);
	}
	
	/**
	 * 将前台提交的json内容保存到数据库中
	 * @return
	 */
	@PostMapping("/save/docContent")
	public Result<Object> saveDocContent(@Valid DocContentDTO docContentDTO) {
		System.out.println(docContentDTO);
		DocContentEntity docContentEntity = new DocContentEntity();
		BeanUtils.copyProperties(docContentDTO, docContentEntity);
		this.docContentService.save(docContentEntity);
		return ResultUtil.toResponse(ResultCode.SUCCESS);
	}
	
	/**
	 * 获取文书内容,将模板和数据(Json)绑定并返回html到前台
	 * @return
	 */
	@GetMapping("/detail")
	public Result<Object> getFinalContent(@RequestParam String templateId, @RequestParam String caseId, @RequestParam String contentId) {
		
		DocTemplateEntity docTemplateEntity = docTemplateService.findOne(templateId);

		// 获取模板内容
		Document document = Jsoup.parse(StringEscapeUtils.unescapeHtml4(docTemplateEntity.getContent()));
		
		// 获取文书内容
		String htmlContent = this.docContentService.getHtmlContent(templateId, caseId,contentId, document);
		
		Map<String, String> map = new HashMap<>();
		// 返回到前台
		map.put("htmlContent", htmlContent);
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, map);
	}
	
//	@PostMapping("/createDoc")
//	public Result<Object> createDoc( @RequestBody DocContentEntity docContentEntity) {
//		
//		Map<String, Object> params = new HashMap<>();
//		
//		List<DocTemplateEntity> list = this.docTemplateService.getDocTemplateList(params, 0, 99999).getContent();
//		
//		for ( DocTemplateEntity docTemplateEntity : list ) {
//			
//			String templateId = docTemplateEntity.getId();
//			
//			System.out.println("################################################");
//			System.out.println("templateId:" + templateId);
//			System.out.println("caseId:" + docContentEntity.getCaseId());
//			System.out.println("################################################");
//			
//			DocContentEntity resDocContentEntity = this.docContentService.findByCaseIdAndTemplateId(docContentEntity.getCaseId(),templateId);
//			
//			if ( resDocContentEntity == null ) {
//				throw new EnforceException(ResultCode.INTERNAL_SERVER_ERROR.getCode(), docContentEntity.getCaseId() + ", " + templateId + " is null");
//			}
//
//			// 获取模板内容
//			Document document = Jsoup.parse(StringEscapeUtils.unescapeHtml4(docTemplateEntity.getContent()));
//
//			// 文书上需要填写内容的模块放到上面
//			Map<String, String> map = new HashMap<>();
//			map.put("datetime", "");
//			map.put("templateName", docTemplateEntity.getName());
//
//			String htmlContent = this.docContentService.getHtmlContent(templateId, docContentEntity.getCaseId(), document);
//			
//			htmlContent = htmlContent.replaceAll("<br>", "");
//			
//			document = Jsoup.parse(StringEscapeUtils.unescapeHtml4(htmlContent));
//			
//			document.head().html("<style>@page{margin:0;}table,table tr th, table tr td { border:1px solid #000000;font-family:Simsun; }"
//					+ "table { width:  100%; text-align: center; border-collapse: collapse;}</style>");
//
//			FileToPdf.htmlToDoc(document.toString(), "E:\\fzbrq\\case\\" + docTemplateEntity.getCode() + ".doc");
//		}
//
//		
//		
//		return ResultUtil.toResponse(ResultCode.SUCCESS);
//	}
}
