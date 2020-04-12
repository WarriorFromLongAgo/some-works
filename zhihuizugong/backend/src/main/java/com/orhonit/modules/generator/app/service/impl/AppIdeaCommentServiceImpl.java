package com.orhonit.modules.generator.app.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.app.dao.AppIdeaCommentDao;
import com.orhonit.modules.generator.app.entity.AppIdeaCommentEntity;
import com.orhonit.modules.generator.app.service.AppIdeaCommentService;
import com.orhonit.modules.generator.app.utils.ReflectUtils;
import com.orhonit.modules.generator.app.vo.AppIdeaCommentVO;




/**
  * 点子评论
 * @author YaoSC
 *
 */
@Service("AppIdeaCommentService")
public class AppIdeaCommentServiceImpl  extends ServiceImpl<AppIdeaCommentDao,AppIdeaCommentVO>implements AppIdeaCommentService{
    @Autowired
    AppIdeaCommentDao apppIdeaCommentDao;
	
	/**
	  * 点子   评论列表
	 */
	@Override
	public PageUtils commentPage(Map<String, Object> params) {
		Integer ideaId=Integer.valueOf((String) params.get("ideaId"));
		int currPage = 1;
    	int limit = 10;
    	 if(params.get("page") != null){
             currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
        Page<AppIdeaCommentVO> page = new Page<>(currPage, limit);
        int beginLimit = (currPage-1)*limit;
        List<AppIdeaCommentVO> list = apppIdeaCommentDao.getIdeaCommentByPage(ideaId,beginLimit,limit);
        page.setRecords(list);
        page.setTotal(list.size());
        return new PageUtils(page);
	}

	
	
	/**
	 * 评论和其他回复
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AppIdeaCommentVO newComAndReply(Map<String, Object> params) {
		Integer ideaCommentId = Integer.parseInt((String) params.get("ideaCommentId"));//评论ID
    	int currPage = 1;
    	int limit = 10;
    	 if(params.get("page") != null){
             currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
        int beginLimit = (currPage-1)*limit;
        AppIdeaCommentVO appIdeaCommentVO = apppIdeaCommentDao.newComAndReply(ideaCommentId, beginLimit, limit);
        return appIdeaCommentVO;
	}



	@Override
	public void insertideaComment(AppIdeaCommentEntity entity) {
		apppIdeaCommentDao.insertIdeaComment(entity);
		
	}

}
