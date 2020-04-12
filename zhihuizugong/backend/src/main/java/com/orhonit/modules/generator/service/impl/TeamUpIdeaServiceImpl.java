package com.orhonit.modules.generator.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.TeamUpIdeaDao;
import com.orhonit.modules.generator.entity.TeamUpIdeaEntity;
import com.orhonit.modules.generator.service.TeamUpIdeaService;


/**
 * 我为组工出点子
 * @author YaoSC
 *
 */
@Service("TeamUpIdeaService")
public  class TeamUpIdeaServiceImpl extends ServiceImpl<TeamUpIdeaDao, TeamUpIdeaEntity> implements TeamUpIdeaService {

	
	@Autowired
	TeamUpIdeaDao teamUpIdeaDao;
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		if(!params.containsKey("ideaTitle")) {
			Page<TeamUpIdeaEntity> page = this.selectPage(
	                new Query<TeamUpIdeaEntity>(params).getPage(),
	                new EntityWrapper<TeamUpIdeaEntity>().orderBy("idea_create_time DESC")
	        );
			  page.setTotal(this.selectCount(new EntityWrapper<TeamUpIdeaEntity>()));
	          return new PageUtils(page);
		}else {
			String ideaTitle = params.get("ideaTitle").toString();
			//String createUserId = params.get("createUserId").toString();
			Page<TeamUpIdeaEntity> page = this.selectPage(
	                new Query<TeamUpIdeaEntity>(params).getPage(),
	                new EntityWrapper<TeamUpIdeaEntity>()
	                .like(StringUtils.isNotBlank(ideaTitle), "idea_title", ideaTitle)
	                .orderBy("idea_create_time DESC")
	        );
			 page.setTotal(this.selectCount(new EntityWrapper<TeamUpIdeaEntity>()));
	         return new PageUtils(page);
		}
		
	}

	@Override
	public void deleteIdeaById(Integer ideaId) {
		teamUpIdeaDao.deleteIdeaById(ideaId);
		
	}

}
