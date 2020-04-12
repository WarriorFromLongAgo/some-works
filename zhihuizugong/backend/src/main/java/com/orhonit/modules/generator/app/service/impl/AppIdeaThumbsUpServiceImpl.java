package com.orhonit.modules.generator.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.modules.generator.app.dao.AppIdeaThumbsUpDao;
import com.orhonit.modules.generator.app.dao.AppTeamUpIdeaDao;
import com.orhonit.modules.generator.app.entity.AppIdeaThumbsUpEntity;
import com.orhonit.modules.generator.app.service.AppIdeaThumbsUpService;

/**
  * 为组工出点子  点赞
 * @author YaoSC
 *
 */
@Service("AppIdeaThumbsUpService")
public class AppIdeaThumbsUpServiceImpl extends ServiceImpl<AppIdeaThumbsUpDao,AppIdeaThumbsUpEntity>implements AppIdeaThumbsUpService{
    
	@Autowired
	AppIdeaThumbsUpDao appIdeaThumbsUpDao;
	@Autowired
	AppTeamUpIdeaDao  appTeamUpIdeaDao;
	
	/**
	  * 点赞+1
	 */
	@Override
	public void updateNewZan(Integer ideaId) {
		appTeamUpIdeaDao.updateNewZan(ideaId);
		
	}
    
	/**
	 * 点赞-1
	 */
	@Override
	public void deleteZan(Integer thumbsupId) {
		// TODO Auto-generated method stub
		appIdeaThumbsUpDao.deleteZan(thumbsupId);
		
	}
    
	/**
	 * 点赞
	 */
	@Override
	public String selectDianZan(AppIdeaThumbsUpEntity entity) {
		 String msg="成功点赞!";
		 String error="已点赞过!";
		 int selectCount=appIdeaThumbsUpDao.selectCount(new EntityWrapper<AppIdeaThumbsUpEntity>()
				 .eq("user_id", entity.getUserId())
				 .eq("idea_id", entity.getIdeaId())
				 .eq("idea_type", entity.getIdeaType()));
		 if(selectCount<=0) {
			 appIdeaThumbsUpDao.insert(entity);
				return msg;
			}else {
				return error;
			}
	}

	@Override
	public String quxiaodianzan(AppIdeaThumbsUpEntity entity) {
		String msg="已取消点赞!";
		String error="未点赞,取消不了点赞!";
		 int selectCount=appIdeaThumbsUpDao.selectCount(new EntityWrapper<AppIdeaThumbsUpEntity>()
				 .eq("user_id", entity.getUserId())
				 .eq("idea_id", entity.getIdeaId())
				 .eq("idea_type", entity.getIdeaType()));
		 if(selectCount>0) {
			 appIdeaThumbsUpDao.delete(
					 new EntityWrapper<AppIdeaThumbsUpEntity>()
					 .eq("user_id", entity.getUserId())
					 .eq("idea_id", entity.getIdeaId())
					 .eq("idea_type", entity.getIdeaType()));
			 return msg;
		 }else {
			 return error;
		 }
	}

}
