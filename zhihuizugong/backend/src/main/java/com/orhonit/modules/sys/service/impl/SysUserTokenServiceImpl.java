package com.orhonit.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.R;
import com.orhonit.modules.sys.dao.SysUserTokenDao;
import com.orhonit.modules.sys.entity.SysUserTokenEntity;
import com.orhonit.modules.sys.oauth2.TokenGenerator;
import com.orhonit.modules.sys.service.SysUserTokenService;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {
	//12小时后过期
	private final static int EXPIRE = 3600 * 12;


	@Override
	public R createToken(long userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();

		//当前时间
		Date now = new Date();
		//过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
		SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
		//判断是否生成过token
		List<SysUserTokenEntity> tokenEntityList = this.selectList(new EntityWrapper<SysUserTokenEntity>().and("user_id ="+userId).and("is_app = 0"));
		if(tokenEntityList.size() == 0){
			tokenEntity = new SysUserTokenEntity();
			tokenEntity.setUserId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);
			tokenEntity.setIsApp(0);
			//保存token
			this.insert(tokenEntity);
		}else{
			tokenEntityList.get(0).setToken(token);
			tokenEntityList.get(0).setUpdateTime(now);
			tokenEntityList.get(0).setExpireTime(expireTime);
			tokenEntityList.get(0).setIsApp(0);
			//更新token
			this.updateById(tokenEntityList.get(0));
		}

		R r = R.ok().put("token", token).put("expire", EXPIRE);

		return r;
	}

	@Override
	public void logout(long userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();

		//修改token
		SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
		tokenEntity.setUserId(userId);
		tokenEntity.setToken(token);
		this.updateById(tokenEntity);
	}

}
