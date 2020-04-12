package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.MeetPeopleEntity;

import java.util.List;
import java.util.Map;

/**
 * 参加人员表
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-12 15:00:48
 */
public interface MeetPeopleService extends IService<MeetPeopleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<MeetPeopleEntity> getById(String meetId);

    int deleteByMeetId(String meetId);


}

