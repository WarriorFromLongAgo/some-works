package com.orhonit.modules.generator.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.app.dao.AppHelpMeCommentReplyDao;
import com.orhonit.modules.generator.app.entity.AppHelpMeCommentReplyEntity;
import com.orhonit.modules.generator.app.service.AppHelpMeCommentReplyService;

@Service("AppHelpMeCommentReplyService")
public class AppHelpMeCommentReplyServiceImpl extends ServiceImpl
                 <AppHelpMeCommentReplyDao,AppHelpMeCommentReplyEntity>implements AppHelpMeCommentReplyService{
	
	@Autowired
	AppHelpMeCommentReplyDao Dao;
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String id=String.valueOf(params.get("replyId"));
		int replyId = Integer.valueOf(id);
		int currPage = 1;
    	int limit = 10;
    	 if(params.get("page") != null){
             currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
        Page<AppHelpMeCommentReplyEntity> page = new Page<>(currPage, limit);
        int beginLimit = (currPage-1)*limit;
        List<AppHelpMeCommentReplyEntity>list = Dao.selectReplyList(replyId, beginLimit, limit);
        page.setRecords(list);
        long l = list.size();
        page.setTotal(l);
        return new PageUtils(page);
	}

}
