package com.orhonit.ole.enforce.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.utils.SpringContext;
import com.orhonit.ole.enforce.filter.RestfulFilter;
import com.orhonit.ole.enforce.token.TokenManager;
import com.orhonit.ole.enforce.token.UserIdToken;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.SysLogService;
import com.orhonit.ole.sys.utils.TokenUtil;
import com.orhonit.ole.sys.utils.UserUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 单点登录
 * @author ebusu
 *
 */
@RequestMapping("/sso")
@RestController
@Slf4j
public class EnforceSsoController {
	
	@GetMapping("/login")
	public void ssoLogin(@RequestParam String token, HttpServletResponse response) throws Exception{
		log.info("token is {}", token);
		/*TokenManager tokenManager = SpringContext.getBean(TokenManager.class);
		boolean flag = tokenManager.deleteToken(token);
		log.info("delete token  result=",flag);	*/
		log.info("token is {}", token);
		String userId = TokenUtil.getUserIdByToekn(token);
		log.info("userId is {}",userId);
		UserIdToken userIdToken = new UserIdToken();
		userIdToken.setUserId(Long.valueOf(userId));
		SecurityUtils.getSubject().login(userIdToken);
		response.sendRedirect("/enforce");
	}

}
