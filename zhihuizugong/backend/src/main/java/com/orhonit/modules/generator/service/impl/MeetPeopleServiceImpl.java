package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.MeetPeopleDao;
import com.orhonit.modules.generator.entity.MeetPeopleEntity;
import com.orhonit.modules.generator.service.MeetPeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("meetPeopleService")
public class MeetPeopleServiceImpl extends ServiceImpl<MeetPeopleDao, MeetPeopleEntity> implements MeetPeopleService {

    @Autowired
    MeetPeopleDao meetPeopleDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String meetId = (String) params.get("meetId");
        Page<MeetPeopleEntity> page = this.selectPage(
                new Query<MeetPeopleEntity>(params).getPage(),
                new EntityWrapper<MeetPeopleEntity>().where("meet_id="+meetId)
        );
        page.setTotal(this.selectCount(new EntityWrapper<MeetPeopleEntity>().where("meet_id="+meetId)));
        return new PageUtils(page);
    }

    @Override
    public List<MeetPeopleEntity> getById(String meetId) {
        return meetPeopleDao.getById(meetId);
    }

    @Override
    public int deleteByMeetId(String meetId) {
        return meetPeopleDao.deleteByMeetId(meetId);
    }
}