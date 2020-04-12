package com.orhonit.ole.cim.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.farsunset.cim.sdk.server.session.DefaultSessionManager;
import com.orhonit.ole.cim.util.BeanUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/msgindex")
@Slf4j
public class MsgIndexController {

	@GetMapping("/index")
	public void index() {
		log.info("index page.");
	}
	
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {

		request.setAttribute("sessionList",
				((DefaultSessionManager) BeanUtils.getBean("CIMSessionManager")).queryAll());
		
		log.info("sessionList is {}" , ((DefaultSessionManager) BeanUtils.getBean("CIMSessionManager")).queryAll());
		ModelAndView modelAndView=new ModelAndView("session/manage");
		return modelAndView;
	}
	
	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request) {
		ModelAndView modelAndView=new ModelAndView("webclient/main");
		return modelAndView;
	}
	
	
}
