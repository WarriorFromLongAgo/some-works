package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.LeadershipOverseeEntity;

import java.util.Map;

/**
 * 领导督办主表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-06 15:02:04
 */
public interface LeadershipOverseeService extends IService<LeadershipOverseeEntity> {
	 /**
     * 领导督办已发列表
     */
    PageUtils queryPage(Map<String, Object> params);
    /**
     * 领导督办已接受列表
     */
	PageUtils receptionList(Map<String, Object> params);
	/**
     * 保存
     */
	void save(LeadershipOverseeEntity leadershipOversee);
	/**
	 * 删除领导督办
	 * @param overseeId
	 */
	void deleteLeadershipOversee(String overseeId);
}

