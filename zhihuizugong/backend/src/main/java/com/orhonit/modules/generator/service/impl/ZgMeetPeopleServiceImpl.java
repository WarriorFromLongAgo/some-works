package com.orhonit.modules.generator.service.impl;

import com.orhonit.modules.generator.dao.ZgMeetInformDao;
import com.orhonit.modules.generator.entity.ZgMeetInformEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.ZgMeetPeopleDao;
import com.orhonit.modules.generator.entity.ZgMeetPeopleEntity;
import com.orhonit.modules.generator.service.ZgMeetPeopleService;

import java.util.*;


@Service("zgMeetPeopleService")
public class ZgMeetPeopleServiceImpl extends ServiceImpl<ZgMeetPeopleDao, ZgMeetPeopleEntity> implements ZgMeetPeopleService {

	@Autowired
	private ZgMeetPeopleDao zgMeetPeopleDao;

	@Autowired
	private ZgMeetInformDao zgMeetInformDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String meetId = params.get("meetId").toString();
    	if (params.get("type") != null) {
    		Integer type = Integer.parseInt((String)params.get("type"));
            Page<ZgMeetPeopleEntity> page = this.selectPage(
                    new Query<ZgMeetPeopleEntity>(params).getPage(),
                    new EntityWrapper<ZgMeetPeopleEntity>().where("meet_id = "+"'"+meetId+"'").and("type = "+type)
            );
            page.setTotal(this.selectCount(new EntityWrapper<ZgMeetPeopleEntity>().where("meet_id = "+"'"+meetId+"'").and("type = "+type)));
            return new PageUtils(page);
		}else {
	        Page<ZgMeetPeopleEntity> page = this.selectPage(
	                new Query<ZgMeetPeopleEntity>(params).getPage(),
	                new EntityWrapper<ZgMeetPeopleEntity>().where("meet_id = "+"'"+meetId+"'")
	        );
	        page.setTotal(this.selectCount(new EntityWrapper<ZgMeetPeopleEntity>().where("meet_id = "+"'"+meetId+"'")));
	        return new PageUtils(page);
		}

    }

	@Override
	public void save(ZgMeetPeopleEntity zgMeetPeople) {
		zgMeetPeople.setCreateTime(new Date());
		zgMeetPeople.setId(UUID.randomUUID().toString().replace("-", ""));
		zgMeetPeopleDao.save(zgMeetPeople);
	}

	@Override
	public List<ZgMeetPeopleEntity> findPeo(String meetId, Long userId) {
		
		return zgMeetPeopleDao.findPeo(meetId,userId);
	}

	@Override
	public List<Map<String,Object>> findJoinMeetList(Long userId) {
		List<Map<String, Object>> joinMeetList = zgMeetPeopleDao.findJoinMeetList(userId);
		for (Map<String, Object> stringObjectMap : joinMeetList) {
			String meetId = stringObjectMap.get("meetId").toString();
			ZgMeetInformEntity zgMeetInformEntity = zgMeetInformDao.selectById(meetId);
			if (zgMeetInformEntity != null){
				stringObjectMap.put("meetTitle",zgMeetInformEntity.getMeetTitle());
			}
		}
		return joinMeetList;
	}

	@Override
	public void updateReadType(String meetId,Integer userId) {
		
		zgMeetPeopleDao.updateReadType(meetId,userId);
	}


}