package com.orhonit.ole.enforce.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 根据业务需求返回接口数据
 * @author ebusu
 *
 */
@RestController
public class EnforceErrorController implements ErrorController{
	
	private final static String ERROR_PATH = "/error";
	

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

	/**
     * Supports the HTML Error View
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "404", produces = "text/html")
    public String error404(HttpServletRequest request) {
    	System.out.println("404");
        return "您当前访问的页面不存在.";
    }
    
    /**
     * Supports the HTML Error View
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "500", produces = "text/html")
    public String error500(HttpServletRequest request) {
    	System.out.println("500");
        return "500";
    }
    
    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    public Object error(HttpServletRequest request ) {
    	
    	System.out.println("sdf");
        return "404";
    }

}
