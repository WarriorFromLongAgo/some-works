package com.orhonit.modules.generator.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.app.dao.AppHelpMeCommentDao;
import com.orhonit.modules.generator.app.dao.AppHelpMeDianZanDao;
import com.orhonit.modules.generator.app.dao.ApphelpMeDao;
import com.orhonit.modules.generator.app.entity.AppHelpMeDianZanEntity;
import com.orhonit.modules.generator.app.entity.ApphelpMeEntity;
import com.orhonit.modules.generator.app.service.ApphelpMeService;
import com.orhonit.modules.generator.app.vo.AppHelpMeVO;

/*
 * 发帖信息
 */
@Service("ApphelpMeService")
public class ApphelpMeServiceImpl extends ServiceImpl<ApphelpMeDao,AppHelpMeVO>implements ApphelpMeService{

	
	
	@Autowired
	ApphelpMeDao Dao;
	@Autowired
	AppHelpMeDianZanDao appHelpMeDianZanDao;
	@Autowired
	AppHelpMeCommentDao  appHelpMeCommentDao;
	
	@Override
	public void deleteId(Integer helpMeId) {
		Dao.deleteId(helpMeId);
		
	}

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String helpTitle = String.valueOf(params.get("helpTitle"));
		String userId=String.valueOf(params.get("userId"));
		int helpUserId = Integer.valueOf(userId);
		int helpMeId=0;
		if(params.containsKey("helpMeId")) {
			 String id = String.valueOf(params.get("helpMeId"));
			 helpMeId = Integer.valueOf(id);
		}
		int currPage = 1;
    	int limit = 10;
    	 if(params.get("page") != null){
             currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
        Page<AppHelpMeVO> page = new Page<>(currPage, limit);
        int beginLimit = (currPage-1)*limit;
        List<AppHelpMeVO>list= Dao.selectListAndUserName(helpMeId,helpTitle,beginLimit, limit);
        for(AppHelpMeVO entity: list) {
        	int zanCount=appHelpMeDianZanDao.selectCount(new EntityWrapper<AppHelpMeDianZanEntity>()    //每个帖子的点赞数量
        			.eq("help_id", entity.getHelpMeId())
        			.eq("help_type", 1));
        	entity.setHelpThumbsUp(zanCount);
        	int countComment=appHelpMeCommentDao.selectCountZan(entity.getHelpMeId());    //评论数量
        	entity.setCommentTotal(countComment);
        	int cou = appHelpMeDianZanDao.selectCount(new EntityWrapper<AppHelpMeDianZanEntity>()    //每个帖子的点赞数量
        			.eq("help_id", entity.getHelpMeId())
        			.eq("help_type", 1)
        			.eq("help_user_id_zan", helpUserId));
        	String helpZan = String.valueOf(cou);
        	entity.setHelpZan(helpZan);
        }
        page.setRecords(list);
        long l = list.size();
        page.setTotal(l);
        return new PageUtils(page);
	}

	@Override
	public void updateHelp(ApphelpMeEntity entity) {
		entity.setHelpUpdateTime(new Date());
		Dao.updateHelpMe(entity);
		
	}

	@Override
	public void insertHelpMe(ApphelpMeEntity entity) {
		Dao.insertHelpMe(entity);
		
	}

	
}
