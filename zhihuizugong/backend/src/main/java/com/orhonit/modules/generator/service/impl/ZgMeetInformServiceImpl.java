package com.orhonit.modules.generator.service.impl;

import com.orhonit.modules.generator.dao.ZgMeetPeopleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.app.entity.AppHelpMeCommentReplyEntity;
import com.orhonit.modules.generator.dao.ZgMeetInformDao;
import com.orhonit.modules.generator.entity.WorkPlanEntity;
import com.orhonit.modules.generator.entity.ZgMeetInformEntity;
import com.orhonit.modules.generator.entity.ZgMeetPeopleEntity;
import com.orhonit.modules.generator.service.ZgMeetInformService;
import com.orhonit.modules.sys.entity.SysUserEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service("zgMeetInformService")
public class ZgMeetInformServiceImpl extends ServiceImpl<ZgMeetInformDao, ZgMeetInformEntity> implements ZgMeetInformService {

	@Autowired
	private ZgMeetInformDao zgMeetInformDao;

	@Autowired
	private ZgMeetPeopleDao zgMeetPeopleDao;
	
    @Override
    public PageUtils findAll(Map<String, Object> params) {
		int currPage = 1;
    	int limit = 10;

    	 if(params.get("page") != null){
    		 currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
         int page = (currPage-1)*limit;
         List<ZgMeetInformEntity> listSize= zgMeetInformDao.findAllCount(params);
         params.put("page", page);
         params.put("limit", limit);  
         Page<ZgMeetInformEntity> infPage = new Page<>(currPage,limit);
         List<ZgMeetInformEntity> list= zgMeetInformDao.findAll(params);
		 infPage.setTotal(listSize.size());
		 infPage.setRecords(list);
        return new PageUtils(infPage);
    }

	@Override
	public void save(ZgMeetInformEntity zgMeetInform) {
		zgMeetInform.setCreateTime(new Date());
		zgMeetInformDao.save(zgMeetInform);
	}

	@Override
	public List<Map<String, Object>> findAllUser(Long userId) {
		return zgMeetInformDao.findAllUser(userId);
	}

	@Override
	public List<Map<String, Object>> findMeetList() {
		// TODO Auto-generated method stub
		return zgMeetInformDao.findMeetList();
	}

	@Override
	public void del(String id) {
		zgMeetInformDao.deleteById(id);
		zgMeetInformDao.delPeo(id);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map unreadList(Map<String,Object>params) {
         int userId=Integer.valueOf((String) params.get("userId"));
         List<ZgMeetPeopleEntity> unreadSize= zgMeetInformDao.readTotal(userId);//未读总数
         List<ZgMeetInformEntity>meetList=new ArrayList<ZgMeetInformEntity>();//未读列表
         Map<String,Object> map = new HashMap<String,Object>();
         for(ZgMeetPeopleEntity inform:unreadSize) {
        	 ZgMeetInformEntity	 entity= zgMeetInformDao.meetList(inform.getMeetId());
        	 meetList.add(entity);
         }
         map.put("meetList", meetList);
         map.put("readtotal", unreadSize.size());
		//return new PageUtils(page);
		return map;
	}

	

}