package com.orhonit.ole.report.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.utils.StrUtil;
import com.orhonit.ole.report.token.UserIdToken;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.UserService;
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
public class ReportSsoController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public void ssoLogin(@RequestParam(value = "token" , required = true) String token,@RequestParam(value = "flag" , required = false) String flag,HttpServletResponse response) throws Exception{
		log.info("token is {}", token);
		log.info("flag is {}", flag);
		if(StrUtil.isNotEmpty(flag)&&"app".equals(flag)){
			String personId= TokenUtil.getPersonIdByToekn(token);
			log.info("personId is {}",personId);
			User user = this.userService.getUserByPersonId(personId);
			UserIdToken userIdToken = new UserIdToken();
			userIdToken.setUserId(user.getId());
			SecurityUtils.getSubject().login(userIdToken);
			response.sendRedirect("/report");
		}else{
			String userId = TokenUtil.getUserIdByToekn(token);
			log.info("userId is {}",userId);
			UserIdToken userIdToken = new UserIdToken();
			userIdToken.setUserId(Long.valueOf(userId));
			SecurityUtils.getSubject().login(userIdToken);
			response.sendRedirect("/report");
		}
	}

}
