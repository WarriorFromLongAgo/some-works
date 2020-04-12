package com.orhonit.modules.religion.service;


import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.IService;
import com.orhonit.modules.religion.entity.ReligionPerson;

/**
 * 教职人员
 * 
 * @author cyf
 * @email cyf0477@126.com
 * @version 2018-11-12 15:50:29
 */
public interface ReligionPersonService extends IService<ReligionPerson> {
	
	/**
	 * 查询教职人员支持身份证
	 * @return
	 */
	public Map<String, Object> selectReligionPersonAll(Map<String, Object> params);
	
}
