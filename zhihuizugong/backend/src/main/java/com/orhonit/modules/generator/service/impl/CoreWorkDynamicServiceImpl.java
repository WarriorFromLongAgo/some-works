package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.CoreWorkDynamicDao;
import com.orhonit.modules.generator.dao.CoreWorkDynamicFileDao;
import com.orhonit.modules.generator.entity.CoreWorkDynamicEntity;
import com.orhonit.modules.generator.service.CoreWorkDynamicService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("coreWorkDynamicService")
public class CoreWorkDynamicServiceImpl extends ServiceImpl<CoreWorkDynamicDao, CoreWorkDynamicEntity> implements CoreWorkDynamicService {

	@Autowired
	CoreWorkDynamicDao coreWorkDynamicDao;

	@Autowired
	CoreWorkDynamicFileDao coreWorkDynamicFileDao;
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Integer workId = Integer.parseInt((String) params.get("workId"));
		String serveId = (String) params.get("serveId");
    	Page<CoreWorkDynamicEntity> page = this.selectPage(
                new Query<CoreWorkDynamicEntity>(params).getPage(),
                new EntityWrapper<CoreWorkDynamicEntity>().and("work_id="+workId).and(StringUtils.isNotBlank(serveId) , "serve_id="+"'"+serveId+"'").orderBy("create_time DESC")
        );	
    	page.setTotal(this.selectCount(new EntityWrapper<CoreWorkDynamicEntity>().where("work_id="+workId).and(StringUtils.isNotBlank(serveId) , "serve_id="+"'"+serveId+"'")));
        return new PageUtils(page);
	}

	@Override
	public void insertDynamic(CoreWorkDynamicEntity coreWorkDynamic) {
		coreWorkDynamicDao.insertDynamic(coreWorkDynamic);
	}

	@Override
	public void deleteByDynamicId(String dynamicId) {
		coreWorkDynamicDao.deleteByDynamicId(dynamicId);
	}
}