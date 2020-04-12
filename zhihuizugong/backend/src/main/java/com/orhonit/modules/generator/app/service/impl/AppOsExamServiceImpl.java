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
import com.orhonit.modules.generator.app.dao.AppOsExamDao;

import com.orhonit.modules.generator.app.entity.AppOsExamEntity;

import com.orhonit.modules.generator.app.service.AppOsExamService;
import com.orhonit.modules.generator.app.vo.AppOsExamVO;



/**
 * APP端试卷
 * @author YaoSC
 *
 */
@Service("AppOsExamService")
public class AppOsExamServiceImpl extends ServiceImpl<AppOsExamDao,AppOsExamEntity>implements AppOsExamService{
	
	@Autowired
	AppOsExamDao dao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		 Page<AppOsExamEntity> page = this.selectPage(
	                new Query<AppOsExamEntity>(params).getPage(),
	                new EntityWrapper<AppOsExamEntity>().eq("is_del", CommonParameters.isDel.IS_DEL_NO)
	        );
	        page.setTotal(this.selectCount(new EntityWrapper<AppOsExamEntity>().eq("is_del", CommonParameters.isDel.IS_DEL_NO)));
	        return new PageUtils(page);
		
	}

	@Override
	public List<AppOsExamVO> getExamNow() {
		
		return dao.getExamNow();
	}
    
	
	
}
