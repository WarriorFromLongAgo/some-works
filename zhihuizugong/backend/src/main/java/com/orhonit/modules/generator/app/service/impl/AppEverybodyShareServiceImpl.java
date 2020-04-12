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
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.app.constant.CommonParameters;
import com.orhonit.modules.generator.app.dao.AppEverybodyShareDao;
import com.orhonit.modules.generator.app.entity.AppEverybodyShareEntity;
import com.orhonit.modules.generator.app.entity.AppOsExamEntity;
import com.orhonit.modules.generator.app.entity.AppWywNoteEntity;
import com.orhonit.modules.generator.app.service.AppEverybodyShareService;
import com.orhonit.modules.generator.app.utils.ReflectUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 大家来分享
 * @author YaoSC
 *
 */

//@Slf4j
@Service("AppEverybodyShareService")
public class AppEverybodyShareServiceImpl extends ServiceImpl<AppEverybodyShareDao,AppEverybodyShareEntity> 
                                                implements AppEverybodyShareService{
	@Autowired
	AppEverybodyShareDao  AppEverybodyShareDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		if(!params.containsKey("shareTitle")) {
			int currPage = 1;
	    	int limit = 10;
	    	 if(params.get("page") != null){
	             currPage = Integer.parseInt((String)params.get("page"));
	         }
	         if(params.get("limit") != null){
	             limit = Integer.parseInt((String)params.get("limit"));
	         }
	        Page<AppEverybodyShareEntity> page = new Page<>(currPage, limit);
	        int beginLimit = (currPage-1)*limit;
	        params.put("limit", limit);
	        params.put("beginLimit", beginLimit);
	        params.remove("page",page);
	        page.setRecords(AppEverybodyShareDao.listEvery(params));
	        page.setTotal(this.selectCount(new EntityWrapper<AppEverybodyShareEntity>()));
	        return new PageUtils(page);
		}else {
			String shareTitle = params.get("shareTitle").toString();
			int currPage = 1;
	    	int limit = 10;
	    	 if(params.get("page") != null){
	             currPage = Integer.parseInt((String)params.get("page"));
	         }
	         if(params.get("limit") != null){
	             limit = Integer.parseInt((String)params.get("limit"));
	         }
	        Page<AppEverybodyShareEntity> page = new Page<>(currPage, limit);
	        int beginLimit = (currPage-1)*limit;
	        params.put("limit", limit);
	        params.put("shareTitle", shareTitle);
	        params.put("beginLimit", beginLimit);
	        params.remove("page",page);
	        page.setRecords(AppEverybodyShareDao.listEvery(params));
	        page.setTotal(this.selectCount(new EntityWrapper<AppEverybodyShareEntity>().where("share_title", shareTitle)));
	        return new PageUtils(page);
			  }
		}
		

	@Override
	public void deleteEveryBodyById(Integer shareId) {
		AppEverybodyShareDao.deleteEveryBodyById(shareId);
	}

	@Override
	public void insertEveryBody(AppEverybodyShareEntity everybodyShareEntity) {
		everybodyShareEntity.setShareCreateTime(new Date());
		AppEverybodyShareDao.insertEveryBodyShare(everybodyShareEntity);
	}

	@Override
	public AppEverybodyShareEntity selectOne(Integer shareId) {
		AppEverybodyShareEntity enitty = AppEverybodyShareDao.selectOne(shareId);
		return enitty;
	}


	@Override
	public PageUtils MylistPage(Map<String, Object> params) {
		String shareUserId = params.get("shareUserId").toString();
		int currPage = 1;
    	int limit = 10;
    	 if(params.get("page") != null){
             currPage = Integer.parseInt((String)params.get("page"));
         }
         if(params.get("limit") != null){
             limit = Integer.parseInt((String)params.get("limit"));
         }
        Page<AppEverybodyShareEntity> page = new Page<>(currPage, limit);
        int beginLimit = (currPage-1)*limit;
        params.put("limit", limit);
        params.put("shareUserId", shareUserId);
        params.put("beginLimit", beginLimit);
        params.remove("page",page);
       List<AppEverybodyShareEntity>list= AppEverybodyShareDao.Mylist(params);
        page.setRecords(list);
        page.setTotal(list.size());
        return new PageUtils(page);
	}

}
