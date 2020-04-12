package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.R;
import com.orhonit.modules.generator.entity.OverseePeopleEntity;

import java.util.List;
import java.util.Map;

/**
 * 领导督办人员表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-06 15:02:04
 */
public interface OverseePeopleService extends IService<OverseePeopleEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void deletePeople(Integer peopleId, String overseeId);

	R insertAllPeople(Long userId,String overseeId);

	List<OverseePeopleEntity> allList(String overseeId);
}

