package com.orhonit.modules.generator.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.app.dao.AppIdeaCommentDao;
import com.orhonit.modules.generator.app.dao.AppIdeaThumbsUpDao;
import com.orhonit.modules.generator.app.dao.AppTeamUpIdeaDao;
import com.orhonit.modules.generator.app.entity.AppHelpMeDianZanEntity;
import com.orhonit.modules.generator.app.entity.AppIdeaCommentEntity;
import com.orhonit.modules.generator.app.entity.AppIdeaThumbsUpEntity;
import com.orhonit.modules.generator.app.entity.AppTeamUpIdeaEntity;
import com.orhonit.modules.generator.app.service.AppTeamUpIdeaService;

/**
 * 为组工出点子
 * @author YaoSC
 *
 */
@Service("AppTeamUpIdeaService")
public class AppTeamUpIdeaServiceImpl extends ServiceImpl<AppTeamUpIdeaDao,AppTeamUpIdeaEntity>implements AppTeamUpIdeaService{
	
	@Autowired
	AppTeamUpIdeaDao appTeamUpIdeaDao;
	@Autowired
	AppIdeaThumbsUpDao appIdeaThumbsUpDao;
	@Autowired
	AppIdeaCommentDao appIdeaCommentDao;
    
	/**
	  * 我的点子
	 */
	@Override
	public PageUtils myIdea(Map<String, Object> params, Integer createUserId) {
		//Integer newModel = Integer.parseInt(params.get("newModel").toString());
    	int currPage = 1;
    	int limit = 10;
    	 if(params.get("page") != null){
             currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
        Page<AppTeamUpIdeaEntity> page = new Page<>(currPage, limit);
        int beginLimit = (currPage-1)*limit;
        List<AppTeamUpIdeaEntity> newsComEntityList = appTeamUpIdeaDao.myIdea(createUserId,beginLimit,limit);
        for(AppTeamUpIdeaEntity entity:newsComEntityList) {
        	int countIdeaZan=appIdeaThumbsUpDao.selectCount(new EntityWrapper<AppIdeaThumbsUpEntity>()
        			.eq("idea_id", entity.getIdeaId())
        			.eq("idea_type", CommonParameters.ideaType.IDEA_TYPE_ONE));//查出点子的所有点在数量
        	entity.setIdeaZan(countIdeaZan);
        	int zan=appIdeaThumbsUpDao.selectCount(new EntityWrapper<AppIdeaThumbsUpEntity>()
        			.eq("idea_id", entity.getIdeaId())
        			.eq("user_id", createUserId)
        			.eq("idea_type", CommonParameters.ideaType.IDEA_TYPE_ONE));//登录用户是否赞过
        	String isZan = String.valueOf(zan);
        	entity.setIsZan(isZan);
        	int commentNumber = appIdeaCommentDao.countComment(entity.getIdeaId());//点子的评论数量
        	entity.setIdeaClick(commentNumber);
        }
        page.setRecords(newsComEntityList);
        page.setTotal(this.selectCount(new EntityWrapper<AppTeamUpIdeaEntity>()));
        return new PageUtils(page);
	}
    
	
	/**
	 * 分页查询全部点子列表
	 */
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String id=String.valueOf(params.get("userId"));
		int userId = Integer.valueOf(id);
		int currPage = 1;
    	int limit = 10;
    	 if(params.get("page") != null){
             currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
        Page<AppTeamUpIdeaEntity> page = new Page<>(currPage, limit);
        int beginLimit = (currPage-1)*limit;
        List<AppTeamUpIdeaEntity> list = appTeamUpIdeaDao.selectAllList(beginLimit,limit);
        for(AppTeamUpIdeaEntity entity: list) {
        	int countIdeaZan=appIdeaThumbsUpDao.selectCount(new EntityWrapper<AppIdeaThumbsUpEntity>()
        			.eq("idea_id", entity.getIdeaId())
        			.eq("idea_type", CommonParameters.ideaType.IDEA_TYPE_ONE));//查出点子的所有点在数量
        	entity.setIdeaZan(countIdeaZan);
        	int zan=appIdeaThumbsUpDao.selectCount(new EntityWrapper<AppIdeaThumbsUpEntity>()
        			.eq("idea_id", entity.getIdeaId())
        			.eq("user_id", userId)
        			.eq("idea_type", CommonParameters.ideaType.IDEA_TYPE_ONE));//登录用户是否赞过
        	String isZan = String.valueOf(zan);
        	entity.setIsZan(isZan);
        	int commentNumber = appIdeaCommentDao.countComment(entity.getIdeaId());//点子的评论数量
        	entity.setIdeaClick(commentNumber);
        }
        page.setRecords(list);
        page.setTotal(this.selectCount(new EntityWrapper<AppTeamUpIdeaEntity>()));
        return new PageUtils(page);
	}

    
	/**
	 * 取消点赞
	 */
	@Override
	public void cancel(Integer ideaId) {
		appTeamUpIdeaDao.cancel(ideaId);
		
	}

    
	

}
