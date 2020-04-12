package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.FinancePeopleEntity;

import java.util.List;
import java.util.Map;

/**
 * 财务管理人员表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-24 15:12:02
 */
public interface FinancePeopleService extends IService<FinancePeopleEntity> {

    PageUtils queryPage(Map<String, Object> params);

	void deletePeople(Integer userId, String financeId);

	List<FinancePeopleEntity> queryList(Map<String, Object> params);

	List<FinancePeopleEntity> allList(String financeId);

	List<FinancePeopleEntity> lowerList(Integer lowerId);

	void insertAllPeople(Integer[] userId, String financeId);
}

