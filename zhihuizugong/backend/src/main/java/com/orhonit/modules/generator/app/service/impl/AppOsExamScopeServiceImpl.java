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
import com.orhonit.modules.generator.app.dao.AppOsExamScopeDao;
import com.orhonit.modules.generator.app.entity.AppOsExamScopeEntity;
import com.orhonit.modules.generator.app.service.AppOsExamScopeService;
import com.orhonit.modules.generator.app.vo.AppPersonRrankVO;


/**
  * 答题成绩表
 * @author YaoSC
 *
 */
@Service("AppOsExamScopeService")
public class AppOsExamScopeServiceImpl extends ServiceImpl<AppOsExamScopeDao,AppOsExamScopeEntity>implements AppOsExamScopeService{
	
	@Autowired
	AppOsExamScopeDao Dao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		 Page<AppOsExamScopeEntity> page = this.selectPage(
	                new Query<AppOsExamScopeEntity>(params).getPage(),
	                new EntityWrapper<AppOsExamScopeEntity>()
	        );
	        page.setTotal(this.selectCount(new EntityWrapper<AppOsExamScopeEntity>()));
	        return new PageUtils(page);
	}
	
	
	@Override
	public PageUtils selectPersonRank(Map<String, Object> params) {
    	int currPage = 1;
    	int limit = 10;      //每条页面数量
    	Integer index = 1;   //排名
    	if(params.get("page") != null){
            currPage = Integer.parseInt((String)params.get("page"));
        }
        if(params.get("limit") != null){
            limit = Integer.parseInt((String)params.get("limit"));
        }
        Page<AppPersonRrankVO> page = new Page<>(currPage, limit);
        int beginLimit = (currPage-1)*limit;
        List<AppPersonRrankVO> list = Dao.selectPersonRank(beginLimit,limit);
        for(AppPersonRrankVO i : list) {
        	//i.setIndex(index+(limit*(int)page.getPages()-1));
        	i.setIndex(index+beginLimit);
        	index++;
        }
        page.setRecords(list);
        page.setTotal(list.size());
        //page.setTotal(this.selectCount(new EntityWrapper<AppPersonRrankVO>().where("new_id="+newId)));
        return new PageUtils(page);
	}

}
