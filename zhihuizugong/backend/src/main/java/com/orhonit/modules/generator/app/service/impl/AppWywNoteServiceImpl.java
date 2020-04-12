package com.orhonit.modules.generator.app.service.impl;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.app.dao.AppWywNoteDao;
import com.orhonit.modules.generator.app.entity.AppWywNoteEntity;
import com.orhonit.modules.generator.app.service.AppWywNoteService;
import com.orhonit.modules.generator.app.vo.AppWywNoteVO;

@Service("AppWywNoteService")
public class AppWywNoteServiceImpl extends ServiceImpl<AppWywNoteDao,AppWywNoteEntity>implements AppWywNoteService{
    
	
	@Autowired
	AppWywNoteDao appWywNoteDao;
	@Autowired
	AppWywNoteService appWywNoteService;
	static final String IS_LIST="0";//所有列表
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		if(String.valueOf(params.get("islist")).equals(AppWywNoteServiceImpl.IS_LIST)) {
			int currPage = 1;
	    	int limit = 10;      //每条页面数量
	    	if(params.get("page") != null){
	            currPage = Integer.parseInt((String)params.get("page"));
	        }
	        if(params.get("limit") != null){
	            limit = Integer.parseInt((String)params.get("limit"));
	        }
	        Page<AppWywNoteEntity> page = new Page<>(currPage, limit);
	        int beginLimit = (currPage-1)*limit;
	        List<AppWywNoteEntity> list=appWywNoteDao.selectListUserTruName(beginLimit, limit);//所有笔记发布者
	        page.setTotal(list.size());
	        page.setRecords(list);
		    return new PageUtils(page);
		}else{
			String createUserid=String.valueOf(params.get("createUserid"));
			int userId = Integer.valueOf(createUserid);
			int currPage = 1;
	    	int limit = 10;      //每条页面数量
	    	if(params.get("page") != null){
	            currPage = Integer.parseInt((String)params.get("page"));
	        }
	        if(params.get("limit") != null){
	            limit = Integer.parseInt((String)params.get("limit"));
	        }
	        Page<AppWywNoteEntity> page = new Page<>(currPage, limit);
	        int beginLimit = (currPage-1)*limit;
	        List<AppWywNoteEntity> list=appWywNoteDao.mySelectList(userId, beginLimit, limit);//我的所有笔记发布者
	        page.setTotal(list.size());
	        page.setRecords(list);
		    return new PageUtils(page);
		}
	}

    
	/**
	 * 捂一捂  排行榜
	 */
	@Override
	public PageUtils phPage(Map<String, Object> params) {
		int currPage = 1;
    	int limit = 10;      //每条页面数量
    	Integer index = 1;   //排名
    	if(params.get("page") != null){
            currPage = Integer.parseInt((String)params.get("page"));
        }
        if(params.get("limit") != null){
            limit = Integer.parseInt((String)params.get("limit"));
        }
        Page<AppWywNoteVO> page = new Page<>(currPage, limit);
        int beginLimit = (currPage-1)*limit;
        double allNumberOfReleases = appWywNoteService.selectCount(new EntityWrapper<AppWywNoteEntity>());//总数
        List<AppWywNoteVO> list=appWywNoteDao.userPh(beginLimit, limit);//发布用户的条数
        DecimalFormat decimal = new DecimalFormat("0.0%");
		 decimal.setRoundingMode(RoundingMode.HALF_UP);
        for(AppWywNoteVO i : list) {
        	double sl=i.getNumberOfReleases();
        	double sld=sl/allNumberOfReleases;
        	String str=decimal.format(sld);
        	i.setProportion(str);
        	//i.setIndex(index+(limit*(int)page.getPages()-1));
        	i.setIndex(index+beginLimit);
        	index++;
        }
        page.setTotal(list.size());
        page.setRecords(list);
		return new PageUtils(page);
	}


	@Override
	public AppWywNoteEntity getOneWyw(Integer noteId) {
		
		return appWywNoteDao.getOneWyw(noteId);
	}
}
