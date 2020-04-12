package com.orhonit.ole.enforce.controller.shareapi;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.constants.UserConstants;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.enforce.dao.PersonDao;
import com.orhonit.ole.enforce.entity.SysUserEntity;
import com.orhonit.ole.enforce.repository.SysUserRepository;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.model.User.Status;
import com.orhonit.ole.sys.utils.ShareAppTokenUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户相关控制器
 * 登录
 * @author ebusu
 *
 */
@RestController("shareApiUserController")
@RequestMapping("/shareapi/user")
@Slf4j
public class ShareApiUserController {
	
	@Autowired
	private SysUserRepository sysUserRepository;
	
	@Autowired
	private PersonDao personDao;

	private static final String EXPIRE_TIME = "1";

	/**
	 * app 登录接口
	 * @param certNum 执法证件号
	 * @param password 密码(未加密)
	 * @return 执法人员基本信息
	 */
	@GetMapping("/login")
	public Result<Object> appLogin(
			@RequestParam(value = "loginName" , required = false) String loginName, 
			@RequestParam(value = "password", required = false) String password) {
		
		if ( StringUtils.isEmpty(loginName) ) {
			// 证件号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "用户名为空.");
		}
		if ( StringUtils.isEmpty(password) ) {
			// 密码为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "密码为空.");
		}
		
		SysUserEntity sysUserEntity = sysUserRepository.findByUsername(loginName);
		if (sysUserEntity == null) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_ERROR_PASSWORD.getCode(), "用户名或密码错误.");
		}
		
		Object object = new SimpleHash("MD5", password, sysUserEntity.getSalt(), UserConstants.HASH_ITERATIONS);
		String mpassword = object.toString();

		if (!sysUserEntity.getPassword().equals(mpassword)) {
			log.info("密码错误.");
			return ResultUtil.toResponseWithMsg(ResultCode.APP_ERROR_PASSWORD.getCode(), "用户名或密码错误.");
		}

		if (sysUserEntity.getStatus() != Status.VALID) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PERSON_NOT_EFFECT.getCode(), "无效的状态..");
		}
		
		if ( StringUtils.isEmpty(sysUserEntity.getPersonId()) ) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_ERROR_CERT_NUM.getCode(), "账号无执法证件号.");
		}
		
		if ( StringUtils.isEmpty(sysUserEntity.getDeptId()) ) {
			return ResultUtil.toResponseWithMsg(ResultCode.APP_ERROR_CERT_NUM.getCode(), "账号无执法主体.");
		}
		
		PersonDTO personDTO = this.personDao.getPersonInfoByPersonId(sysUserEntity.getPersonId());
		
		if (personDTO == null) {
			// 执法证件号不存在
			return ResultUtil.toResponse(ResultCode.APP_ERROR_CERT_NUM);
		}
		
		if ( personDTO.getIsEffect().intValue() == CommonParameters.Effect.NOT_EFFECT ) {
			// 无效的执法人员
			return ResultUtil.toResponse(ResultCode.APP_PERSON_CERT_NUM_NOT_UNIQUE);
		}
		
		if ( personDTO.getDelFlag().intValue() == CommonParameters.Effect.EFFECT ) {
			// 无效的执法人员
			return ResultUtil.toResponse(ResultCode.APP_PERSON_CERT_NUM_NOT_UNIQUE);
		}
		
		if ( !personDTO.getDeptId().equals(sysUserEntity.getDeptId())) {
			log.info("用户表和执法人员表的组织代码不一样.");
			// 无效的执法人员
			return ResultUtil.toResponse(ResultCode.APP_PERSON_CERT_NUM_NOT_UNIQUE);
		}
		
		// never expire
		String token = ShareAppTokenUtil.createToken(sysUserEntity.getPersonId().toString(), EXPIRE_TIME);
		
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("token", token);
		
		// 返回执法人员信息
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);
	}
	
	

}
