package com.orhonit.ole.enforce;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.orhonit.ole.enforce.dto.CaseInfoDTO;
import com.orhonit.ole.enforce.entity.DocContentEntity;
import com.orhonit.ole.enforce.entity.DocTemplateEntity;
import com.orhonit.ole.enforce.repository.DocContentRepository;
import com.orhonit.ole.enforce.service.DocTemplateService;
import com.orhonit.ole.enforce.service.casedoc.DocContentService;
import com.orhonit.ole.enforce.utils.FileToPdf;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j(topic = "flowTest")
public class CaseFilecCateLogTest {

	private static final String CASE_ID = "cb21448e-28b4-41aa-9020-fa64f80f7e1f";

	private static final String TEMPLATE_ID = "db2c991e-d38e-42c8-9e04-1cbc3cc23a37";

	@Autowired
	private DocContentRepository docContentRepository;

	@Autowired
	private DocTemplateService docTemplateService;

	@Autowired
	private DocContentService docContentService;

	@Test
	public void generateDoc() {
		DocContentEntity docContentEntity =null; //this.docContentRepository.findByTemplateIdAndCaseId(TEMPLATE_ID, CASE_ID);
		log.info("docContentEntity : {}", docContentEntity);

		DocTemplateEntity docTemplateEntity = this.docTemplateService.findOne(TEMPLATE_ID);
		log.info("docTempalteEntity : {}", docTemplateEntity);

		// 获取模板内容
		Document document = Jsoup.parse(StringEscapeUtils.unescapeHtml4(docTemplateEntity.getContent()));

		// 文书上需要填写内容的模块放到上面
		Map<String, String> map = new HashMap<>();
		map.put("datetime", "");
		map.put("templateName", docTemplateEntity.getName());

		String htmlContent =null;// this.docContentService.getHtmlContent(TEMPLATE_ID, CASE_ID, document);
		
		htmlContent = htmlContent.replaceAll("<br>", "");
		
		document = Jsoup.parse(StringEscapeUtils.unescapeHtml4(htmlContent));
		
		document.head().html("<style>@page{margin:0;}table,table tr th, table tr td { border:1px solid #000000;font-family:Simsun; }"
				+ "table { width:  100%; text-align: center; border-collapse: collapse;}</style>");

		FileToPdf.htmlToDoc(document.toString(), "E:\\fzbrq\\case\\1.doc");

		// 获取模板内容
//		Document document2 = Jsoup.parse(StringEscapeUtils.unescapeHtml4(docTemplateEntity.getContent()));

		// 获取文书内容
//		String htmlContent = this.docContentService.getHtmlContent(TEMPLATE_ID, CASE_ID, document2);
//
//		htmlContent = "<html><head><style>@page{margin:0;}"
//				+ "table,table tr th, table tr td { border:1px solid #000000; }"
//				+ "table { width:  min-height: 25px; line-height: 25px; text-align: center; border-collapse: collapse;}"
//				+ "</style></head><body style=\"width: 756px;margin:0 auto;\">" + "<div>" + htmlContent + "</div>"
//				+ "</body></html>";
//
//		FileToPdf.htmlToDoc(htmlContent, "E:\\fzbrq\\case\\2.doc");
//
//		htmlContent = "<html><head><style>@page{margin:0;}"
//				+ "table,table tr th, table tr td { border:1px solid #000000; }"
//				+ "table { width:  min-height: 25px; line-height: 25px; text-align: center; border-collapse: collapse;}"
//				+ "</style></head><body style=\"width: 756px;margin:0 auto;\">" + "<div><table><tr class=\"firstRow\">"
//				+ "<td width=\"75\" valign=\"top\" style=\"word-break: break-all;\" rowspan=\"3\" colspan=\"1\"><span style=\"font-family: 宋体, SimSun;\">当事人基本情况</span></td>"
//				+ "<td width=\"70\" valign=\"top\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">姓名</span></td>"
//				+ "<td valign=\"top\" colspan=\"1\" rowspan=\"1\" width=\"96\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">"
//				+ "<partyinfo>" + "张三丰" + "</partyinfo></span></td>"
//				+ "<td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"word-break: break-all;\" width=\"71\"><span style=\"font-family: 宋体, SimSun;\">年龄<br></span></td>"
//				+ "<td rowspan=\"1\" valign=\"top\" align=\"null\" width=\"67\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">"
//				+ "<partyinfo>" + "45" + "</partyinfo></span></td>"
//				+ "<td rowspan=\"1\" valign=\"top\" align=\"null\" width=\"99\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">住址</span></td>"
//				+ "<td valign=\"top\" style=\"word-break: break-all;\" colspan=\"3\" rowspan=\"1\" width=\"138\"><span style=\"font-family: 宋体, SimSun;\">"
//				+ "<partyinfo>" + " 呼和浩特" + "</partyinfo></span></td>" + "</tr>" + "<tr>"
//				+ "<td width=\"108\" valign=\"top\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">单位名称</span></td>"
//				+ "<td valign=\"top\" colspan=\"3\" rowspan=\"1\" width=\"235\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">"
//				+ "<partyinfo>" + "**大厦" + "</partyinfo></span></td>"
//				+ "<td rowspan=\"1\" valign=\"top\" align=\"null\" style=\"word-break: break-all;\" colspan=\"3\" width=\"43\"><span style=\"font-family: 宋体, SimSun;\">法定代表人</span></td>"
//				+ "<td rowspan=\"1\" valign=\"top\" align=\"null\" width=\"157\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">"
//				+ "<partyinfo></partyinfo></span></td>" + "</tr>" + "<tr>"
//				+ "<td width=\"108\" valign=\"top\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">单位地址</span></td>"
//				+ "<td valign=\"top\" colspan=\"7\" rowspan=\"1\" width=\"511\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">"
//				+ "<partyinfo>" + "呼和浩特" + "</partyinfo></span></td>" + "</tr>" + "<tr>"
//				+ "<td valign=\"top\" colspan=\"1\" rowspan=\"2\" style=\"word-break: break-all;\" width=\"81\"><span style=\"font-family: 宋体, SimSun;\">违法事实<br></span></td>"
//				+ "<td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"word-break: break-all;\" width=\"70\"><span style=\"font-family: 宋体, SimSun;\">时间</span></td>"
//				+ "<td valign=\"top\" colspan=\"3\" rowspan=\"1\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">"
//				+ "<caseinfo>" + "2018-01-16 15:11:33" + "</caseinfo></span></td>"
//				+ "<td rowspan=\"1\" valign=\"top\" align=\"null\" width=\"75\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">地点</span></td>"
//				+ "<td valign=\"top\" colspan=\"3\" rowspan=\"1\" style=\"word-break: break-all;\" width=\"138\"><span style=\"font-family: 宋体, SimSun;\">"
//				+ "<caseinfo>" + "呼和浩特" + "</caseinfo></span></td>" + "</tr></table></div>\"" + "</body></html>";
//
//		FileToPdf.htmlToDoc(htmlContent, "E:\\fzbrq\\case\\3.doc");

	}

	public static void main(String[] args) {
		String htmlContent = "<html><head><style>@page{margin:0;}"
				+ "table,table tr th, table tr td { border:1px solid #000000;font-family:Simsun; }"
				+ "table { width:  100%; text-align: center; border-collapse: collapse;}"
				+ "</style></head><body style=\"width: 100%;margin:0 auto;\">" + "<div><table><tr>"
				+ "<td width=\"75\" valign=\"top\" style=\"word-break: break-all;\" rowspan=\"3\" colspan=\"1\"><span style=\"font-family: 宋体, SimSun;\">当事人基本情况</span></td>"
				+ "<td width=\"70\" valign=\"top\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">姓名</span></td>"
				+ "<td valign=\"top\" colspan=\"1\" rowspan=\"1\" width=\"96\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">"
				+ "<partyinfo>" + "张三丰" + "</partyinfo></span></td>"
				+ "<td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"word-break: break-all;\" width=\"71\"><span style=\"font-family: 宋体, SimSun;\">年龄<br></span></td>"
				+ "<td rowspan=\"1\" valign=\"top\" align=\"null\" width=\"67\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">"
				+ "<partyinfo>" + "45" + "</partyinfo></span></td>"
				+ "<td rowspan=\"1\" valign=\"top\" align=\"null\" width=\"99\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">住址</span></td>"
				+ "<td valign=\"top\" style=\"word-break: break-all;\" colspan=\"3\" rowspan=\"1\" width=\"138\"><span style=\"font-family: 宋体, SimSun;\">"
				+ "<partyinfo>" + " 呼和浩特" + "</partyinfo></span></td>" + "</tr>" + "<tr>"
				+ "<td width=\"108\" valign=\"top\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">单位名称</span></td>"
				+ "<td valign=\"top\" colspan=\"3\" rowspan=\"1\" width=\"235\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">"
				+ "<partyinfo>" + "**大厦" + "</partyinfo></span></td>"
				+ "<td rowspan=\"1\" valign=\"top\" align=\"null\" style=\"word-break: break-all;\" colspan=\"3\" width=\"43\"><span style=\"font-family: 宋体, SimSun;\">法定代表人</span></td>"
				+ "<td rowspan=\"1\" valign=\"top\" align=\"null\" width=\"157\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">"
				+ "<partyinfo>sadfasdf</partyinfo></span></td>" + "</tr>" + "<tr>"
				+ "<td width=\"108\" valign=\"top\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">单位地址</span></td>"
				+ "<td valign=\"top\" colspan=\"7\" rowspan=\"1\" width=\"511\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">"
				+ "<partyinfo>" + "呼和浩特" + "</partyinfo></span></td>" + "</tr>" + "<tr>"
				+ "<td valign=\"top\" colspan=\"1\" rowspan=\"2\" style=\"word-break: break-all;\" width=\"81\"><span style=\"font-family: 宋体, SimSun;\">违法事实<br></span></td>"
				+ "<td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"word-break: break-all;\" width=\"70\"><span style=\"font-family: 宋体, SimSun;\">时间</span></td>"
				+ "<td valign=\"top\" colspan=\"3\" rowspan=\"1\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">"
				+ "<caseinfo>" + "2018-01-16 15:11:33" + "</caseinfo></span></td>"
				+ "<td rowspan=\"1\" valign=\"top\" align=\"null\" width=\"75\" style=\"word-break: break-all;\"><span style=\"font-family: 宋体, SimSun;\">地点</span></td>"
				+ "<td valign=\"top\" colspan=\"3\" rowspan=\"1\" style=\"word-break: break-all;\" width=\"138\"><span style=\"font-family: 宋体, SimSun;\">"
				+ "<caseinfo>" + "呼和浩特" + "</caseinfo></span></td>" + "</tr></table></div>" + "</body></html>";
		
		htmlContent = htmlContent.replaceAll("<br>", "");
		
		Document document = Jsoup.parse(StringEscapeUtils.unescapeHtml4(htmlContent));
		
		Elements elements = document.getElementsByTag("table");
		
		for ( Element element : elements ) {
			Elements tds = element.getElementsByTag("td");
			for ( Element td : tds) {
				td.removeAttr("style");
				td.removeAttr("valign");
				td.removeAttr("width");
				td.removeAttr("align");
				if ( td.hasAttr("paryInfo")) {
					
				}
				if ( !"".equals(td.getElementsByTag("partyInfo").html())){
					System.out.println(td.getElementsByTag("partyinfo").html());
					td.html(td.getElementsByTag("partyInfo").html());
					
				}
				else if ( !"".equals(td.getElementsByTag("caseinfo").html())){
					System.out.println(td.getElementsByTag("caseinfo").html());
					td.html(td.getElementsByTag("caseinfo").html());
				} else {
					td.html(td.getElementsByTag("span").html());
				}
				
			}
			
		}
		
		log.info("result is {}", document.toString());
		
		FileToPdf.htmlToDoc(document.toString(), "E:\\fzbrq\\case\\5.doc");
		
		
		
		Document res = Jsoup.parse(StringEscapeUtils.unescapeHtml4(htmlContent));
		
		Elements partyElements = res.getElementsByTag("partyInfo");
		
		for ( Element element : partyElements ) {
			element.parent().html( element.html());
		}
		
		Elements caseElements = res.getElementsByTag("caseinfo");
		
		for ( Element element : caseElements ) {
			element.parent().html( element.html());
		}
		
		log.info("result is {}", res);
		
		FileToPdf.htmlToDoc(res.toString(), "E:\\fzbrq\\case\\6.doc");
		
		
		htmlContent = "<html><head><style>@page{margin:0;}"
				+ "table,table tr th, table tr td { border:1px solid #000000;font-family:Simsun; }"
				+ "table { width:  100%; text-align: center; border-collapse: collapse;}"
				+ "</style></head><body style=\"width: 100%;margin:0 auto;\">" + "<div><table><tr>"
				+ "<td rowspan=\"3\" colspan=\"1\" width=\"75\">当事人基本情况</td>"
				+ "<td >姓名</td>"
				+ "<td colspan=\"1\" rowspan=\"1\">"
				+ "张三丰" + "</td>"
				+ "<td colspan=\"1\" rowspan=\"1\" >年龄</td>"
				+ "<td rowspan=\"1\" >"
				+ "45" + "</td>"
				+ "<td rowspan=\"1\" >住址</td>"
				+ "<td colspan=\"3\" rowspan=\"1\" >"
				+ " 呼和浩特" + "</td>" + "</tr>" + "<tr>"
				+ "<td >单位名称</td>"
				+ "<td colspan=\"3\" rowspan=\"1\">"
				+ "**大厦" + "</td>"
				+ "<td rowspan=\"1\" colspan=\"3\">法定代表人</td>"
				+ "<td rowspan=\"1\" >"
				+ "</td>" + "</tr>" + "<tr>"
				+ "<td >单位地址</td>"
				+ "<td colspan=\"7\" rowspan=\"1\" >"
				+ "呼和浩特" + "</td>" + "</tr>" + "<tr>"
				+ "<td colspan=\"1\" rowspan=\"2\">违法事实</td>"
				+ "<td colspan=\"1\" rowspan=\"1\" >时间</td>"
				+ "<td colspan=\"3\" rowspan=\"1\" >"
				+ "2018-01-16 15:11:33" + "</td>"
				+ "<td rowspan=\"1\" >地点</td>"
				+ "<td colspan=\"3\" rowspan=\"1\">"
				+ "呼和浩特" + "</td>" + "</tr></table></div>" + "</body></html>";
		
		htmlContent = "<html><head><style>@page{margin:0;}"
				+ "table,table tr th, table tr td { border:1px solid #000000;font-family:Simsun; }"
				+ "table { width:  100%; text-align: center; border-collapse: collapse;}"
				+ "</style></head><body style=\"width: 100%;margin:0 auto;\">" + "<div><table><tr>"
				+ "<td rowspan=\"3\" colspan=\"1\" >当事人基本情况</td>"
				+ "<td >姓名</td>"
				+ "<td colspan=\"1\" rowspan=\"1\">"
				+ "张三丰" + "</td>"
				+ "<td colspan=\"1\" rowspan=\"1\" >年龄</td>"
				+ "<td rowspan=\"1\" >"
				+ "45" + "</td>"
				+ "<td rowspan=\"1\" >住址</td>"
				+ "<td colspan=\"3\" rowspan=\"1\" >"
				+ " 呼和浩特" + "</td>" + "</tr>" + "<tr>"
				+ "<td >单位名称</td>"
				+ "<td colspan=\"3\" rowspan=\"1\">"
				+ "**大厦" + "</td>"
				+ "<td rowspan=\"1\" colspan=\"3\">法定代表人</td>"
				+ "<td rowspan=\"1\" >"
				+ "</td>" + "</tr>" + "<tr>"
				+ "<td >单位地址</td>"
				+ "<td colspan=\"7\" rowspan=\"1\" >"
				+ "呼和浩特" + "</td>" + "</tr>" + "<tr>"
				+ "<td colspan=\"1\" rowspan=\"2\">违法事实</td>"
				+ "<td colspan=\"1\" rowspan=\"1\" >时间</td>"
				+ "<td colspan=\"3\" rowspan=\"1\" >"
				+ "2018-01-16 15:11:33" + "</td>"
				+ "<td rowspan=\"1\" >地点</td>"
				+ "<td colspan=\"3\" rowspan=\"1\">"
				+ "呼和浩特" + "</td>" + "</tr></table></div>" + "</body></html>";
		
		
		Document keyide = Jsoup.parse(StringEscapeUtils.unescapeHtml4(htmlContent));
		
		log.info("keyide :{}", keyide);

		FileToPdf.htmlToDoc(keyide.toString(), "E:\\fzbrq\\case\\10.doc");
		
	}

}
