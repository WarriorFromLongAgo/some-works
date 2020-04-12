package com.orhon.smartcampus.modules.core.graphql.gql.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.dao.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.orhon.smartcampus.modules.base.entity.Regions;
import com.orhon.smartcampus.modules.base.mapper.DictionaryMapper;
import com.orhon.smartcampus.modules.base.mapper.RegionsMapper;


@Service
public class GQLRunService implements ApplicationRunner {

	@Autowired
	private DictionaryMapper mapper;

	@Autowired
	private RegionsMapper regionsMapper;

	@Autowired
	private RedisTemplate<String, HashMap<String, Object>> redisTemplate;

	private static HashMap<String, Object> DictionaryMap  = new HashMap<String, Object>();

	private static HashMap<String, Object> RegionMap  = new HashMap<String, Object>();

	private static HashMap<String, Object> SchoolMap  = new HashMap<String, Object>();

	private static HashMap<String, Object> UserMap  = new HashMap<String, Object>();

	private static HashMap<String, Object> TeacherMap  = new HashMap<String, Object>();

	private static HashMap<String, Object> SchoolSettingMap  = new HashMap<String, Object>();

	private static HashMap<String, Object> GradeMap  = new HashMap<String, Object>();

	private static HashMap<String, Object> PeriodMap  = new HashMap<String, Object>();

	private static HashMap<String, Object> SubjectMap  = new HashMap<String, Object>();

	private static HashMap<String, Object> DutiesMap  = new HashMap<String, Object>();

	private static HashMap<String, Object> DepartmentUsersMap  = new HashMap<String, Object>();

	private static HashMap<String, Object> departmentMap  = new HashMap<String, Object>();

	@Autowired
	private RedisConnectionFactory factory;

	@Override
	public void run(ApplicationArguments args) throws Exception {		
		//		int hashCode = redisTemplate.opsForHash().hashCode();
		//		System.out.println(hashCode);
		//List<Object> list2 = redisTemplate.opsForHash().values("user_users{16842}");
		//System.out.println(list2);
			Object map = redisTemplate.opsForHash().entries("user_users{185}");
				//System.out.println("user_id======="+map);
		ValueOperations<String, HashMap<String, Object>> opsForValue = redisTemplate.opsForValue();
		boolean DictionaryKey = redisTemplate.hasKey("DictionaryMap");
		if(DictionaryKey) {
			DictionaryMap = opsForValue.get("DictionaryMap");
		}else{
			List<HashMap<String,Object>> dictionaryMap = mapper.getDicOptionsByCode(null);
			for (HashMap<String, Object> hashMap : dictionaryMap) {
				if(hashMap.get("dictionary_value") !=null ) {
					DictionaryMap.put((String) hashMap.get("dictionary_value"),hashMap);
				}
			}
			opsForValue.set("DictionaryMap", DictionaryMap, 60 * 60, TimeUnit.SECONDS);
		}


		boolean RegionKey = redisTemplate.hasKey("RegionMap");
		if(RegionKey) {
			RegionMap = opsForValue.get("RegionMap");
		}else {
			List<Regions> regionsList = regionsMapper.getRegionsList();
			for (Regions regions : regionsList) {
				if(regions.getId()!=null && regions.getRegion_name()!=null) {
					RegionMap.put(regions.getId().toString(),regions);
				}
			}
			opsForValue.set("RegionMap", RegionMap, 60 * 60, TimeUnit.SECONDS);
		}
	}

	public static HashMap<String, Object> DictionaryMap() {
		return DictionaryMap;
	}

	public static HashMap<String, Object> RegionMap() {
		return RegionMap;
	}
	
	public static HashMap<String, Object> SchoolMap() {
		return SchoolMap;
	}
	public static HashMap<String, Object> UserMap() {
		return UserMap;
	}
	public static HashMap<String, Object> TeacherMap() {
		return TeacherMap;
	}
	public static HashMap<String, Object> SchoolSettingMap() {
		return SchoolSettingMap;
	}
	public static HashMap<String, Object> GradeMap() {
		return GradeMap;
	}
	public static HashMap<String, Object> PeriodMap() {
		return PeriodMap;
	}
	public static HashMap<String, Object> SubjectMap() {
		return SubjectMap;
	}
	public static HashMap<String, Object> DutiesMap() {
		return DutiesMap;
	}
	public static HashMap<String, Object> DepartmentUsersMap() {
		return DepartmentUsersMap;
	}
	public static HashMap<String, Object> departmentMap() {
		return departmentMap;
	}

	/** 根据key取出对应的数据
	 * @param key
	 * @return
	 */
	public String get(final String key) {
		return redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					byte[] b = connection.get(key.getBytes());
					if(b==null){
						return null;
					}
					return new String(b, "utf-8");

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return "";
			}
		});
	}


}
