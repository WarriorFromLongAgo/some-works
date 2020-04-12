package com.orhonit.ole.enforce.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.constants.CommonParameters;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.common.utils.StrUtil;
import com.orhonit.ole.enforce.dto.DeptDTO;
import com.orhonit.ole.enforce.entity.SmsEntity;
import com.orhonit.ole.enforce.service.dept.DeptService;
import com.orhonit.ole.enforce.service.person.PersonService;
import com.orhonit.ole.enforce.service.sms.SmsService;
import com.orhonit.ole.enforce.utils.SmsSend;
import com.orhonit.ole.sys.config.ThreadLocalVariables;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.model.Role;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.RoleService;
import com.orhonit.ole.sys.service.UserService;
import com.orhonit.ole.sys.utils.AppTokenUtil;

/**
 * 用户相关控制器
 * 1. 登录
 * 2. 登出
 * 3. 修改密码
 * @author ebusu
 *
 */
@RestController("apiUserController")
@RequestMapping("/api/user")
public class ApiUserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private DeptService deptService;

	private static final String EXPIRE_TIME = "1";

	/**
	 * app 登录接口
	 * @param certNum 执法证件号
	 * @param password 密码(未加密)
	 * @return 执法人员基本信息
	 */
	@GetMapping("/login")
	public Result<Object> appLogin(
			@RequestParam(value = "certNum" , required = false) String certNum, 
			@RequestParam(value = "password", required = false) String password) {
		
		/**
		 * 1. 查看certNum是否存在
		 * 2. 是否关联已关联用户
		 * 3. 密码是否正确
		 * 4. 返回执法人员信息
		 */
		if ( StringUtils.isEmpty(certNum) ) {
			// 证件号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "执法证件号为空.");
		}
		if ( StringUtils.isEmpty(password) ) {
			// 密码为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "密码为空.");
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("certNum", certNum);
		List<PersonDTO> result = this.personService.getPersonListByParam(paramMap);
		if (result == null || result.isEmpty()) {
			// 执法证件号不存在
			return ResultUtil.toResponse(ResultCode.APP_ERROR_CERT_NUM);
		}
		
		if ( result.size() > 1 ) {
			// 数据库中根据执法证件号查出多条记录
			return ResultUtil.toResponse(ResultCode.APP_PERSON_CERT_NUM_NOT_UNIQUE);
		}
		
		PersonDTO personDTO = result.get(0);
		
		if ( personDTO.getIsEffect().intValue() == CommonParameters.Effect.NOT_EFFECT ) {
			// 无效的执法人员
			return ResultUtil.toResponse(ResultCode.APP_PERSON_CERT_NUM_NOT_UNIQUE);
		}
		
		String personId = personDTO.getId();
		User user = this.userService.getUserByPersonId(personId);
		if ( user == null ) {
			// 没有和用户表关联
			return ResultUtil.toResponse(ResultCode.APP_USER_PERSON_NOT_BINDING);
		}
		
		if (!user.getPassword().equals(userService.passwordEncoder(new String(password), user.getSalt()))) {
			// 密码错误
			return ResultUtil.toResponse(ResultCode.APP_ERROR_PASSWORD);
		}
		
		List<Role> roles = this.roleService.listByUserId(user.getId());
		
		personDTO.setRoles(roles);
		
		// never expire
		String token = AppTokenUtil.createToken(personId, EXPIRE_TIME);
		
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("token", token);
		
		resultMap.put("personDTO", personDTO);
		
		// 返回执法人员信息
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, resultMap);
	}
	
	/**
	 * app 登出接口 如果设置了缓存需要清除缓存中的token
	 * @param certNum 执法证件号
	 * @param password 密码(未加密)
	 * @return 执法人员基本信息
	 */
	@GetMapping("/logout")
	public Result<Object> appLogout(
			@RequestParam(value = "certNum" , required = false) String certNum, 
			@RequestParam(value = "password", required = false) String password) {
		
		/**
		 * 1. 查看certNum是否存在
		 * 2. 是否关联已关联用户
		 * 3. 密码是否正确
		 * 4. 返回执法人员信息
		 */
		if ( StringUtils.isEmpty(certNum) ) {
			// 证件号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "执法证件号为空.");
		}
		if ( StringUtils.isEmpty(password) ) {
			// 密码为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "密码为空.");
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("certNum", certNum);
		List<PersonDTO> result = this.personService.getPersonListByParam(paramMap);
		if (result == null || result.isEmpty()) {
			// 执法证件号不存在
			return ResultUtil.toResponse(ResultCode.APP_ERROR_CERT_NUM);
		}
		
		if ( result.size() > 1 ) {
			// 数据库中根据执法证件号查出多条记录
			return ResultUtil.toResponse(ResultCode.APP_PERSON_CERT_NUM_NOT_UNIQUE);
		}
		
		PersonDTO personDTO = result.get(0);
		
		if ( personDTO.getIsEffect().intValue() == CommonParameters.Effect.NOT_EFFECT ) {
			// 无效的执法人员
			return ResultUtil.toResponse(ResultCode.APP_PERSON_CERT_NUM_NOT_UNIQUE);
		}
		
		String personId = personDTO.getId();
		User user = this.userService.getUserByPersonId(personId);
		if ( user == null ) {
			// 没有和用户表关联
			return ResultUtil.toResponse(ResultCode.APP_USER_PERSON_NOT_BINDING);
		}
		
		if (!user.getPassword().equals(userService.passwordEncoder(new String(password), user.getSalt()))) {
			// 密码错误
			return ResultUtil.toResponse(ResultCode.APP_ERROR_PASSWORD);
		}
		// 返回执法人员信息
		return ResultUtil.toResponse(ResultCode.SUCCESS);
	}
	
	
	/**
	 * 修改密码
	 * */
	@PostMapping("/updatePassword")
	public Result<Object> changePassword(
			@RequestParam(value = "username" , required = false) String username,
			@RequestParam(value = "oldPassword" , required = false) String oldPassword,
			@RequestParam(value = "newPassword" , required = false) String newPassword) {
		
		String message = userService.appUpdatePassword(username, oldPassword, newPassword);	
			
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, message);
	}
	
	
	/**
	 * 根据证件号查询登录人角色
	 * */
	@GetMapping("/selRoleByPersonNum")
	public Result<Object> selRoleByPersonNum(
			@RequestParam(value = "username" , required = false) String username) {
		List<Object> roleIds = new ArrayList<Object>();
		roleIds = userService.selRoleByPersonNum(username);
			
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, roleIds);
	}
	
	
	/**
	 * 发送短信验证码
	 * */
	@PostMapping("/sendSmsCode")
	public Result<Object> getSmsCode(
			@RequestParam(value = "certNum" , required = false) String certNum) {
		if ( StringUtils.isEmpty(certNum) ) {
			// 证件号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "执法证件号为空.");
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("certNum", certNum);
		List<PersonDTO> result = this.personService.getPersonListByParam(paramMap);
		if (result == null || result.isEmpty()) {
			// 执法证件号不存在
			return ResultUtil.toResponse(ResultCode.APP_ERROR_CERT_NUM);
		}
		if ( result.size() > 1 ) {
			// 数据库中根据执法证件号查出多条记录
			return ResultUtil.toResponse(ResultCode.APP_PERSON_CERT_NUM_NOT_UNIQUE);
		}
		
		PersonDTO personDTO = result.get(0);
		
		if ( personDTO.getIsEffect().intValue() == CommonParameters.Effect.NOT_EFFECT ) {
			// 无效的执法人员
			return ResultUtil.toResponse(ResultCode.APP_PERSON_CERT_NUM_NOT_UNIQUE);
		}
		
		String personId = personDTO.getId();
		User user = this.userService.getUserByPersonId(personId);
		if ( user == null ) {
			// 没有和用户表关联
			return ResultUtil.toResponse(ResultCode.APP_USER_PERSON_NOT_BINDING);
		}
		if(StrUtil.isEmpty(personDTO.getTel())){
			// 电话号码为空
			return ResultUtil.toResponse(ResultCode.APP_PERSON_TEL_NULL);
		}
		if(!SmsSend.isChinaPhoneLegal(personDTO.getTel())){
			// 电话号码格式错误
		   return ResultUtil.toResponse(ResultCode.APP_PERSON_TEL_ERROR);
		}
		Map<Object,Object> maps = new  HashMap<>();
		maps = personService.getSmsCode(personDTO,user);
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, maps);
	}	
	
	/**
	 * app 通过验证码修改密码接口
	 * @param certNum 执法证件号
	 * @param password 密码(未加密)
	 * @param code  验证码
	 * @return 成功或失败
	 */
	@PostMapping("/uppsbycode")
	public Result<Object> appLoginByCode(
			@RequestParam(value = "certNum" , required = false) String certNum, 
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "code", required = false) String code) {
		
		if ( StringUtils.isEmpty(certNum) ) {
			// 证件号为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "执法证件号为空.");
		}
		if ( StringUtils.isEmpty(password) ) {
			// 密码为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "密码为空.");
		}
		if ( StringUtils.isEmpty(code) ) {
			// 验证码为空
			return ResultUtil.toResponseWithMsg(ResultCode.APP_PARAM_ERROR.getCode(), "验证码为空.");
		}
		
		SmsEntity smsEntity=smsService.getSmsByCertNum(certNum);
		
		if(smsEntity==null||StrUtil.isEmpty(smsEntity.getCode())){
			// 验证码错误
			return ResultUtil.toResponse(ResultCode.APP_CODE_ERROR);
		}
		//验证验证码
		if (!smsEntity.getCode().equals(code)){
			// 密码错误
			return ResultUtil.toResponse(ResultCode.APP_CODE_ERROR);
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("certNum", certNum);
		List<PersonDTO> result = this.personService.getPersonListByParam(paramMap);
		if (result == null || result.isEmpty()) {
			// 执法证件号不存在
			return ResultUtil.toResponse(ResultCode.APP_ERROR_CERT_NUM);
		}
		
		if ( result.size() > 1 ) {
			// 数据库中根据执法证件号查出多条记录
			return ResultUtil.toResponse(ResultCode.APP_PERSON_CERT_NUM_NOT_UNIQUE);
		}
		
		PersonDTO personDTO = result.get(0);
		
		if ( personDTO.getIsEffect().intValue() == CommonParameters.Effect.NOT_EFFECT ) {
			// 无效的执法人员
			return ResultUtil.toResponse(ResultCode.APP_PERSON_CERT_NUM_NOT_UNIQUE);
		}
		
		String personId = personDTO.getId();
		User user = this.userService.getUserByPersonId(personId);
		if ( user == null ) {
			// 没有和用户表关联
			return ResultUtil.toResponse(ResultCode.APP_USER_PERSON_NOT_BINDING);
		}
		
		//修改密码
		String message = userService.appUpdatePasswordByCode(user.getUsername(),password);	
		
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, message);
	}
	
	@PostMapping("/lawTypeByPersonId")
	public Result<Object> lawTypeByPersonId(
			@RequestParam(value = "certNum" , required = false) String certNum) {
		PersonDTO personDTO = ThreadLocalVariables.getPersonDTO();
		User user = this.userService.getUserByPersonId(personDTO.getId());
		DeptDTO dept = this.deptService.findDeptInfoById(user.getDept_id());
		String lawType = dept.getLaw_type();
		return ResultUtil.toResponseWithData(ResultCode.SUCCESS, lawType);
	}
	

}
