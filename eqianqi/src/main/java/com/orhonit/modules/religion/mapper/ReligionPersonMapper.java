package com.orhonit.modules.religion.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.religion.entity.ReligionPerson;

/**
 * 教职人员
 * 
 * @author cyf
 * @email cyf0477@126.com
 * @version 2018-11-12 15:50:29
 */
@Mapper
public interface ReligionPersonMapper extends BaseMapper<ReligionPerson> {
	
	
	/**
	 * 查询教职人员支持身份证
	 * @return
	 */
	List<ReligionPerson> selectReligionPersonAll(Map<String, Object> params);
	
	
}
