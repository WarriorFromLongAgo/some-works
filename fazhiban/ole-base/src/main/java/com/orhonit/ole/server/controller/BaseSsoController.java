package com.orhonit.ole.server.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.server.token.UserIdToken;
import com.orhonit.ole.sys.utils.TokenUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 单点登录
 * @author ebusu
 *
 */
@RequestMapping("/sso")
@RestController
@Slf4j
public class BaseSsoController {
	
	@GetMapping("/login")
	public void ssoLogin(@RequestParam String token, HttpServletResponse response) throws Exception{
		log.info("token is {}", token);
		String userId = TokenUtil.getUserIdByToekn(token);
		log.info("userId is {}",userId);
		UserIdToken userIdToken = new UserIdToken();
		userIdToken.setUserId(Long.valueOf(userId));
		SecurityUtils.getSubject().login(userIdToken);
		response.sendRedirect("/base");
	}

}
