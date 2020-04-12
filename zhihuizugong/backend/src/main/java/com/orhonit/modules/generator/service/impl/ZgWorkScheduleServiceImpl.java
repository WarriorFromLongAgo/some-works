package com.orhonit.modules.generator.service.impl;

import com.orhonit.modules.generator.dao.ZgDefaultScoreDao;
import com.orhonit.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.dao.ZgWorkScheduleDao;
import com.orhonit.modules.generator.entity.WorkPlanEntity;
import com.orhonit.modules.generator.entity.ZgWorkScheduleEntity;
import com.orhonit.modules.generator.service.ZgWorkScheduleService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;



@Service("zgWorkScheduleService")
public class ZgWorkScheduleServiceImpl extends ServiceImpl<ZgWorkScheduleDao, ZgWorkScheduleEntity> implements ZgWorkScheduleService {

	@Autowired
	private ZgWorkScheduleDao zgWorkScheduleDao;

	@Autowired
    private ZgDefaultScoreDao zgDefaultScoreDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
		int currPage = 1;
    	int limit = 10;

    	 if(params.get("page") != null){
    		 currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
         int page = (currPage-1)*limit;
         params.put("page", page);
         params.put("limit", limit);
         String planId = params.get("planId").toString();
         Integer status = null;
         if (params.get("status") != null) {
        	 status = Integer.parseInt((String)params.get("status"));
             Page<ZgWorkScheduleEntity> infPage = new Page<>(currPage,limit);
             List<ZgWorkScheduleEntity> list = zgWorkScheduleDao.queryPage(params);
             infPage.setTotal(this.selectCount(new EntityWrapper<ZgWorkScheduleEntity>().where("plan_id = "+"'"+planId+"'").and("status="+status)));
             infPage.setRecords(list);
            return new PageUtils(infPage);
		 }else {
	        Page<ZgWorkScheduleEntity> infPage = new Page<>(currPage,limit);
            List<ZgWorkScheduleEntity> list = zgWorkScheduleDao.queryPage(params);
            infPage.setTotal(this.selectCount(new EntityWrapper<ZgWorkScheduleEntity>().where("plan_id = "+"'"+planId+"'")));
            infPage.setRecords(list);
           return new PageUtils(infPage);
		}

    }

	@Override
	public void save(ZgWorkScheduleEntity zgWorkSchedule) {
        if (zgWorkSchedule.getStatus() == 2){
            SysUserEntity userInfo = zgDefaultScoreDao.findUserInfo(zgWorkSchedule.getUserId());
            zgWorkSchedule.setLeaderName(userInfo.getUserTrueName());
        }
		zgWorkSchedule.setId(UUID.randomUUID().toString().replace("-", ""));
		zgWorkSchedule.setCreateTime(new Date());
		zgWorkScheduleDao.save(zgWorkSchedule);
	}

}