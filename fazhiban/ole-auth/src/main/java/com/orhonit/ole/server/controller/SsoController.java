package com.orhonit.ole.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.SystemConstants;
import com.orhonit.ole.common.utils.StrUtil;
import com.orhonit.ole.server.config.SsoConfig;
import com.orhonit.ole.sys.dao.PermissionDao;
import com.orhonit.ole.sys.model.Permission;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.PermissionService;
import com.orhonit.ole.sys.utils.TokenUtil;
import com.orhonit.ole.sys.utils.UserUtil;

@RestController
@RequestMapping("/sso")
public class SsoController {
	
	@Autowired
	private SsoConfig ssoConfig;
	
	@Autowired
	private PermissionService permissionService;
	
	@GetMapping("/redirect/{module}")
	// @RequiresPermissions(value = { "module:enforce:access" })
	public String redirect(HttpServletResponse response, @PathVariable String module) throws Exception {
		
		String redirectUrl = ssoConfig.getClass().getMethod("get" + StrUtil.captureName(module)).invoke(ssoConfig).toString();
		
		User user = UserUtil.getCurrentUser();
		
		String token = TokenUtil.createToken(user.getId(), ssoConfig.getTokenExpire());
		
		redirectUrl = redirectUrl + "?token=" + token;
		
		System.out.println(redirectUrl);
		
		// response.sendRedirect(redirectUrl);
		
		return redirectUrl;
	}
	
	/**
	 * 获取允许访问的所有系统模块
	 */
	@GetMapping("/module")
	public List<Permission> getAllowedAccessSysByUser() {
		User user = UserUtil.getCurrentUser();
		
		return this.permissionService.getAllowedAccessSysByUser(user.getId(), SystemConstants.MENU_SYS.getCode());
	}
}
