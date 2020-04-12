package com.orhonit.modules.generator.app.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.app.dao.AppHelpMeCommentDao;
import com.orhonit.modules.generator.app.dao.AppHelpMeCommentReplyDao;
import com.orhonit.modules.generator.app.dao.AppHelpMeDianZanDao;
import com.orhonit.modules.generator.app.entity.AppHelpMeCommentEntity;
import com.orhonit.modules.generator.app.entity.AppHelpMeCommentReplyEntity;
import com.orhonit.modules.generator.app.entity.AppHelpMeDianZanEntity;
import com.orhonit.modules.generator.app.service.AppHelpMeCommentService;
import com.orhonit.modules.generator.app.vo.AppHelpMeCommentVO;

/**
 * 一级评论
 */
@Service("AppHelpMeCommentService")
public class AppHelpMeCommentServiceImpl extends ServiceImpl<AppHelpMeCommentDao,AppHelpMeCommentVO>implements AppHelpMeCommentService{
	
	@Autowired
	AppHelpMeCommentDao Dao;
	@Autowired
	AppHelpMeCommentReplyDao appHelpMeCommentReplyDao;
	@Autowired
	AppHelpMeDianZanDao appHelpMeDianZanDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String t = String.valueOf(params.get("comHelpMeId"));
		Integer comHelpMeId = Integer.valueOf(t);
		String userId = String.valueOf(params.get("userId"));
		int helpUserId = Integer.valueOf(userId);
		int currPage = 1;
    	int limit = 10;
    	 if(params.get("page") != null){
             currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
        Page<AppHelpMeCommentVO> page = new Page<>(currPage, limit);
        int beginLimit = (currPage-1)*limit;
        List<AppHelpMeCommentVO> list = Dao.selectCommentList(comHelpMeId,beginLimit,limit);
        for(AppHelpMeCommentVO entity:list) {
        	 int commentReplyCount=appHelpMeCommentReplyDao.selectCount(
             		new EntityWrapper<AppHelpMeCommentReplyEntity>().eq("reply_id", entity.getComId()));
        	 entity.setReplyTotal(commentReplyCount);//回复数量
        	 int isThubUp = appHelpMeDianZanDao.selectCount(//此用户是否点赞  1：已点赞  0；未点赞
        			 new EntityWrapper<AppHelpMeDianZanEntity>()
        			 .eq("help_id", entity.getComId())
        			 .eq("help_type", 2)
        			 .eq(StringUtils.isBlank(userId),"help_user_id_zan", helpUserId));
        	 int countThubUp = appHelpMeDianZanDao.selectCount(//点赞数量
        			 new EntityWrapper<AppHelpMeDianZanEntity>()
        			 .eq("help_id", entity.getComId())
        			 .eq("help_type", 2));
        	 String thubUp = String.valueOf(isThubUp);
        	 entity.setIsThumbsUp(thubUp);
        	 entity.setCountThumbsUp(countThubUp);
        }
        page.setRecords(list);
        page.setTotal(list.size());
        return new PageUtils(page);
	}

	@Override
	public void insertCommetn(AppHelpMeCommentEntity entity) {
		Dao.insertCommetn(entity);
	}

	
}
