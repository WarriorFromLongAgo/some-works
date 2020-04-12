package com.orhonit.ole.sys.config;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.orhonit.ole.common.utils.JsonUtil;
import com.orhonit.ole.sys.model.SysDictEntity;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Component
public class DictCacheManager {
	
	@Autowired
	private EhCacheManager cacheManager;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	public static final String DICT_CACHE_NAME = "dict_cache:";
	
	/**
	 * 缓存过期时间,使用token过期时间
	 */
	@Value("${token.expire.seconds}")
	private Integer expireSeconds;

	/**
	 * 缓存字典表数据
	 * @param sysDictEntities
	 */
	public void saveDictList(List<SysDictEntity> sysDictEntities, String key) {
		Cache cache = cacheManager.getCacheManager().getCache(DICT_CACHE_NAME);
		
		Element element = new Element(key, sysDictEntities);
		element.setTimeToLive(expireSeconds);
		cache.put(element);
	}

	public List<SysDictEntity> getSysDictList(String key) {
		Cache cache = cacheManager.getCacheManager().getCache(DICT_CACHE_NAME);
		Element element = cache.get(key);
		if (element != null) {
			List<SysDictEntity> sysDictEntities = (List<SysDictEntity>) element.getValue();
			return sysDictEntities;
		}

		return null;
	}

	public Integer getSysDictCount(String key) {
		Cache cache = cacheManager.getCacheManager().getCache(DICT_CACHE_NAME);
		Element element = cache.get(key);
		if (element != null) {
			Integer sysDictEntitiesCount = (Integer) element.getValue();
			return sysDictEntitiesCount;
		}

		return null;
	}

	public void saveDictCount(Integer count, String key) {
		Cache cache = cacheManager.getCacheManager().getCache(DICT_CACHE_NAME);
		
		Element element = new Element(key, count);
		element.setTimeToLive(expireSeconds);
		cache.put(element);
		
	}

	public List<SysDictEntity> getDictTypeByValue(String key) {
		/*Cache cache = cacheManager.getCacheManager().getCache(DICT_CACHE_NAME);
		Element element = cache.get(key);
		if (element != null) {
			List<SysDictEntity> sysDictEntities = (List<SysDictEntity>) element.getValue();
			return sysDictEntities;
		}

		return null;*/
		
		String json = redisTemplate.opsForValue().get(DICT_CACHE_NAME + key);
		if (!StringUtils.isEmpty(json)) {
			
			List<SysDictEntity> sysDictEntities = JSONObject.parseArray(json, SysDictEntity.class);

			return sysDictEntities;
		}
		return null;
	}

	public void saveDictTypeValueData(String key, List<SysDictEntity> sysDictEntities) {
		/*Cache cache = cacheManager.getCacheManager().getCache(DICT_CACHE_NAME);
		
		Element element = new Element(key, sysDictEntities);
		element.setTimeToLive(expireSeconds);
		cache.put(element);*/
		
		redisTemplate.opsForValue().set(DICT_CACHE_NAME + key, JsonUtil.toJson(JSONObject.toJSON(sysDictEntities)), expireSeconds,
				TimeUnit.SECONDS);
		
	}

	public void deleteDictByTypeValue(String key) {
		/*Cache cache = cacheManager.getCacheManager().getCache(DICT_CACHE_NAME);
		cache.remove(typeValue);*/
		key = DICT_CACHE_NAME + key;
		if (redisTemplate.hasKey(key)) {
			redisTemplate.delete(key);
		}
	}
}
