package com.orhonit.ole.report.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

import com.orhonit.ole.common.utils.JsonUtil;
import com.orhonit.ole.common.utils.SpringContext;
import com.orhonit.ole.report.advice.ExceptionHandlerAdvice.ResponseInfo;
import com.orhonit.ole.report.token.TokenManager;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.SysLogService;
import com.orhonit.ole.sys.utils.UserUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 退出<br>
 * web退出和restful方式退出<br>
 * 后者会删除缓存的token
 * 
 * @author caodw
 *
 *
 */
@Slf4j(topic = "adminLogger")
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		String loginToken = RestfulFilter.getToken(request);
		User user = UserUtil.getCurrentUser();
		if (StringUtils.isBlank(loginToken)) {// 非Restful方式
			boolean flag = super.preHandle(request, response);
			log.debug("{}退出成功", user.getUsername());
			SpringContext.getBean(SysLogService.class).save(user.getId(), "退出", true, null);

			return flag;
		} else {
			TokenManager tokenManager = SpringContext.getBean(TokenManager.class);
			boolean flag = tokenManager.deleteToken(loginToken);
			if (flag) {
				RestfulFilter.writeResponse(WebUtils.toHttp(response), HttpStatus.OK.value(), SUCCESS_INFO);
				log.debug("{}退出成功", user.getUsername());
			} else {
				RestfulFilter.writeResponse(WebUtils.toHttp(response), HttpStatus.BAD_REQUEST.value(), ERR_INFO);
			}

			SpringContext.getBean(SysLogService.class).save(user.getId(), "token方式退出", flag, null);

			return false;
		}
	}

	private static String SUCCESS_INFO = JsonUtil
			.toJson(ResponseInfo.builder().code(HttpStatus.OK.value() + "").message("退出成功").build());
	private static String ERR_INFO = JsonUtil
			.toJson(ResponseInfo.builder().code(HttpStatus.BAD_REQUEST.value() + "").message("退出失败,token不存在").build());

}
