package com.orhonit.ole.enforce.token;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.orhonit.ole.common.utils.JsonUtil;

/**
 * redis实现的Token
 * 
 * 
 * @author caodw
 *
 *
 */
@Service
@Primary
public class RedisTokenManager implements TokenManager {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	private static final String TOKEN_PREFIX = "tokens:";
	/**
	 * token过期秒数
	 */
	@Value("${token.expire.seconds}")
	private Integer expireSeconds;

	@Override
	public Token saveToken(UsernamePasswordToken usernamePasswordToken) {
		String key = UUID.randomUUID().toString();
		redisTemplate.opsForValue().set(TOKEN_PREFIX + key, JsonUtil.toJson(usernamePasswordToken), expireSeconds,
				TimeUnit.SECONDS);

		return Token.builder().token(key).createTime(new Date())
				.expireTime(DateUtils.addSeconds(new Date(), expireSeconds)).build();
	}

	@Override
	public UsernamePasswordToken getToken(String key) {
		String json = redisTemplate.opsForValue().get(TOKEN_PREFIX + key);
		if (!StringUtils.isEmpty(json)) {
			UsernamePasswordToken usernamePasswordToken = JsonUtil.fromJson(json, UsernamePasswordToken.class);

			return usernamePasswordToken;
		}
		return null;
	}

	@Override
	public boolean deleteToken(String key) {
		key = TOKEN_PREFIX + key;
		if (redisTemplate.hasKey(key)) {
			redisTemplate.delete(TOKEN_PREFIX + key);

			return true;
		}

		return false;
	}
}
