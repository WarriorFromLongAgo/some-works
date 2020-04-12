package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.CoreWorkCrewDao;
import com.orhonit.modules.generator.dao.CoreWorkDao;
import com.orhonit.modules.generator.dao.CoreWorkDynamicDao;
import com.orhonit.modules.generator.entity.CoreWorkEntity;
import com.orhonit.modules.generator.service.CoreWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("coreWorkService")
public class CoreWorkServiceImpl extends ServiceImpl<CoreWorkDao, CoreWorkEntity> implements CoreWorkService {

	@Autowired
	CoreWorkDao coreWorkDao;

	@Autowired
	CoreWorkCrewDao coreWorkCrewDao;

	@Autowired
	CoreWorkDynamicDao coreWorkDynamicDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	Integer workId = Integer.parseInt((String) params.get("workId"));
    	Page<CoreWorkEntity> page = this.selectPage(
                new Query<CoreWorkEntity>(params).getPage(),
                new EntityWrapper<CoreWorkEntity>().and("work_id="+workId).orderBy("creat_time DESC")
        );	
    	page.setTotal(this.selectCount(new EntityWrapper<CoreWorkEntity>().where("work_id="+workId)));
        return new PageUtils(page);
    }

	@Override
	public boolean save(CoreWorkEntity entity) {

		return coreWorkDao.save(entity);
	}

	@Override
	public CoreWorkEntity selectByServeId(String serveId) {
		return coreWorkDao.selectByServeId(serveId);
	}

	@Override
	public void deleteByServeId(String serveId) {
		coreWorkDao.deleteByServeId(serveId);
		coreWorkCrewDao.deleteByServeId(serveId);
		coreWorkDynamicDao.deleteByServeId(serveId);
	}
}