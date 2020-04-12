package com.orhonit.ole.tts.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.tts.dto.DocContentDTO;
import com.orhonit.ole.tts.entity.DocContentEntity;
import com.orhonit.ole.tts.entity.DocTemplateEntity;
import com.orhonit.ole.tts.service.doc.DocContentService;
import com.orhonit.ole.tts.service.doc.DocTemplateService;

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

	/**
	 * 根据模板ID获取输入框以及输入框内容
	 * 
	 * @param templateId
	 */
	@GetMapping("/content")
	public Result<Object> getTemplateContentById(@RequestParam String templateId, @RequestParam String caseId) {
		DocTemplateEntity docTemplateEntity = docTemplateService.findOne(templateId);

		// 获取模板内容
		Document document = Jsoup.parse(StringEscapeUtils.unescapeHtml4(docTemplateEntity.getContent()));
		
		// 最终要返回给前台的html代码
		StringBuilder stringBuilder = new StringBuilder();
		
		// 文书上需要填写内容的模块放到上面
		Map<String, String> map = new HashMap<>();
		map.put("datetime", "");
		map.put("templateName", docTemplateEntity.getName());
		this.docContentService.createHtml(stringBuilder, document, caseId, map, templateId);
		// 返回到前台
		map.put("htmlContent", stringBuilder.toString());
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, map);
	}
	
	/**
	 * 将前台提交的json内容保存到数据库中
	 * @return
	 */
	@PostMapping("/save/docContent")
	public Result<Object> saveDocContent(@Valid DocContentDTO docContentDTO) {
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
	public Result<Object> getFinalContent(@RequestParam String templateId, 
			@RequestParam String caseId) {
		
		DocTemplateEntity docTemplateEntity = docTemplateService.findOne(templateId);

		// 获取模板内容
		Document document = Jsoup.parse(StringEscapeUtils.unescapeHtml4(docTemplateEntity.getContent()));
		
		// 获取文书内容
		String htmlContent = this.docContentService.getHtmlContent(templateId, caseId, document);
		
		Map<String, String> map = new HashMap<>();
		// 返回到前台
		System.out.println(htmlContent);
		map.put("htmlContent", htmlContent);
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, map);
	}
}
