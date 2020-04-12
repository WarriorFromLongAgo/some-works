package com.orhon.pa.modules.sys.web;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhon.pa.modules.sys.token.UserIdToken;
import com.orhon.pa.modules.sys.utils.TokenUtil;

import groovy.util.logging.Slf4j;

/**
 * 单点登录
 * @author ebusu
 *
 */
@RequestMapping("/sso")
@RestController
@Slf4j
public class PaSsoController {
	
	@RequestMapping("/login")
	public void ssoLogin(@RequestParam String token, HttpServletResponse response) throws Exception{
		String userId = TokenUtil.getUserIdByToekn(token);
		UserIdToken userIdToken = new UserIdToken();
		userIdToken.setUserId(Long.valueOf(userId));
		SecurityUtils.getSubject().login(userIdToken);
		response.sendRedirect("/pa/a?login");
	}

}
