package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.MeetingEntity;
import com.orhonit.modules.generator.vo.MeetPeopleVo;

import java.util.List;
import java.util.Map;

/**
 * 支部活动表
 *
 * @author zjw
 * @email chobitsyjwz@163.com
 * @date 2019-02-14 14:56:22
 */
public interface MeetingService extends IService<MeetingEntity> {

    PageUtils queryPage(Map<String, Object> params,long userId);

	MeetingEntity getById(String meetId);

	void setMeetUsers(String id, Integer userDept);

	void insertMeet(MeetingEntity meeting);

	List<MeetPeopleVo> getIsSigninAndStation(String meetId);

	int getmeetcounts(Long userId);
	
	void updateByMeeting(MeetingEntity meeting);
	
	void deleteBymeetId(String meetId);
}

