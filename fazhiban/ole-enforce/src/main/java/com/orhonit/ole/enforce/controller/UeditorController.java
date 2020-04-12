package com.orhonit.ole.enforce.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.ueditor.ActionEnter;
import com.orhonit.ole.enforce.entity.TestContent;
import com.orhonit.ole.enforce.repository.TestContentRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/ueditor")
@Slf4j
public class UeditorController {
	
	@Autowired
	private TestContentRepository testContentRepository;
	
	@RequestMapping("/action")
	public void actionEnter(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding( "utf-8" );
			response.setHeader("Content-Type" , "text/html");
			String rootPath = request.getServletContext().getRealPath( "/" );
			// response.getWriter().
			response.getWriter().write( new ActionEnter( request, rootPath ).exec() );
			
		} catch(Exception e ) {
			throw new IllegalArgumentException("ueditor error, error msg : " + e.getMessage());
		}
	}
	
	@PostMapping("/save")
	@ResponseBody
	public void saveTestContent(@RequestBody TestContent testContent) {
		System.out.println(testContent.getContent());
		TestContent testContentModel = new TestContent();
		testContentModel.setContent(testContent.getContent());
		this.testContentRepository.save(testContentModel);
		
	}
	
	@GetMapping("/getContent/{id}")
	@ResponseBody
	public String getContent(@PathVariable String id) {
		TestContent testContent = this.testContentRepository.findOne(Integer.valueOf(id));
		return testContent.getContent();
	}

}
