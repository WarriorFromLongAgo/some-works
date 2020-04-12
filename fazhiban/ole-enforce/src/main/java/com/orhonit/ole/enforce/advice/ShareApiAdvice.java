package com.orhonit.ole.enforce.advice;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.utils.IPUtils;
import com.orhonit.ole.enforce.config.ShareUrlConfig;
import com.orhonit.ole.enforce.entity.AppLogEntity;
import com.orhonit.ole.enforce.exception.EnforceException;
import com.orhonit.ole.enforce.repository.AppLogRepository;
import com.orhonit.ole.sys.config.ThreadLocalVariables;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.utils.ShareAppTokenUtil;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ShareApiAdvice {

	@Autowired
	private AppLogRepository appLogRepository;

	@Autowired
	private ShareUrlConfig shareUrlConfig;

	@Pointcut("execution(public * com.orhonit.ole.enforce.controller.shareapi.*.*(..))")
	public void authentication() {

	}

	@Around("authentication()")
	public Object doAuth(ProceedingJoinPoint joinPoint) {
		
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		HttpServletRequest request = attributes.getRequest();
		
		boolean ipInProp = false;

		if (shareUrlConfig.getUrls() != null) {
			for (Map<String, String> item : shareUrlConfig.getUrls()) {
				log.info("url is {}", item.get("url"));
				//if ( IPUtils.getIpAddr(request).equals(item.get("url"))) {
					ipInProp = true;
				//}
			}
			
		} else {
			throw new EnforceException(500, "IP鉴权信息不能为空.");
		}
		
		if ( !ipInProp) {
			throw new EnforceException(500, "当前IP地址无法访问接口.");
		}

		AppLogEntity appLogEntity = new AppLogEntity();

		String url = request.getRequestURL().toString();

		appLogEntity.setHttpMethod(request.getMethod());
		appLogEntity.setIsSuccess(CommonParameters.Effect.EFFECT);
		appLogEntity.setToken(request.getHeader("Authorization"));
		appLogEntity.setStartTime(String.valueOf(System.currentTimeMillis()));
		appLogEntity.setCreateDate(new Date());
		appLogEntity.setUrl(url);
		appLogEntity.setUserAgent(request.getHeader("user-agent"));
		if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {

			Object[] apiArgs = new Object[joinPoint.getArgs().length];
			int index = 0;
			for (Object arg : joinPoint.getArgs()) {
				if (arg instanceof String) {
					apiArgs[index] = arg;
				}
				index++;
			}

			appLogEntity.setParams(JSON.toJSONString(apiArgs));
		}

		if (url.indexOf("/shareapi/user/login") == -1) {
			String authorizationToken = request.getHeader("Authorization");
			if (StringUtils.isEmpty(authorizationToken)) {
				throw new EnforceException(ResultCode.APP_TOKEN_ERROR);
			}

			try {
				String userId = ShareAppTokenUtil.getUserIdByToken(authorizationToken);
				PersonDTO personDTO = new PersonDTO();
				personDTO.setId(String.valueOf(userId));
				ThreadLocalVariables.setPersonDTO(personDTO);

			} catch (Exception e) {
				appLogEntity.setIsSuccess(CommonParameters.Effect.NOT_EFFECT);
				e.printStackTrace();
				throw new EnforceException(ResultCode.APP_TOKEN_FORMAT_ERROR);
			}

			PersonDTO tlPersonDTO = ThreadLocalVariables.getPersonDTO();

			appLogEntity.setUserId(tlPersonDTO.getId());
		}

		try {
			Object object = joinPoint.proceed();
			appLogEntity.setResult(JSON.toJSONString(object));
			return object;
		} catch (Throwable e) {
			appLogEntity.setResult(e.getMessage());
			appLogEntity.setIsSuccess(CommonParameters.Effect.NOT_EFFECT);
			e.printStackTrace();
			throw new EnforceException(ResultCode.APP_TOKEN_ERROR.getCode(),
					e.getMessage() == null ? "null" : e.getMessage());
		} finally {
			appLogEntity.setEndTime(String.valueOf(System.currentTimeMillis()));
			Long execTime = Long.valueOf(appLogEntity.getEndTime()).longValue()
					- Long.valueOf(appLogEntity.getStartTime()).longValue();
			appLogEntity.setExecTime(String.valueOf(execTime));
			this.appLogRepository.save(appLogEntity);
			ThreadLocalVariables.removePersonDTO();
		}
	}
}
