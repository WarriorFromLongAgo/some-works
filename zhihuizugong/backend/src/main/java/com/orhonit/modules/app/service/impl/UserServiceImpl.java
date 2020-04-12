package com.orhonit.modules.app.service.impl;


import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.exception.RRException;
import com.orhonit.common.validator.Assert;
import com.orhonit.modules.app.dao.UserDao;
import com.orhonit.modules.app.entity.AppUserEntity;
import com.orhonit.modules.app.form.LoginForm;
import com.orhonit.modules.app.service.UserService;

import java.util.List;
import java.util.concurrent.RecursiveTask;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, AppUserEntity> implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public AppUserEntity queryByMobile(String mobile) {
		AppUserEntity userEntity = new AppUserEntity();
		userEntity.setMobile(mobile);
		return baseMapper.selectOne(userEntity);

	}

	@Override
	public long login(LoginForm form) {
		if (form.getMobile() != null && StringUtils.isNotBlank(form.getMobile())){
			AppUserEntity user = null;
			//List<AppUserEntity> us = queryByMobile(form.getMobile());
			List<AppUserEntity> us = userDao.queryByMobile(form.getMobile());
			if (us != null && us.size() > 0){
				user = us.get(0);
			}
			Assert.isNull(user, "手机号或密码错误");

			//密码错误

			if(!user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())){
				throw new RRException("手机号或密码错误");
			}

			return user.getUserId();
		}else if (form.getUserNickname() != null && StringUtils.isNotBlank(form.getUserNickname())){
			AppUserEntity userEntity = new AppUserEntity();
			userEntity.setUserNickname(form.getUserNickname());
			AppUserEntity user = baseMapper.selectOne(userEntity);
			Assert.isNull(user, "身份证号或密码错误");

			//密码错误

			if(!user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())){
				throw new RRException("身份证号或密码错误");
			}

			return user.getUserId();
		}
		return -1;
	}

}
