package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.CoreOpinionFileDao;
import com.orhonit.modules.generator.entity.CoreOpinionFileEntity;
import com.orhonit.modules.generator.service.CoreOpinionFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("coreOpinionFileService")
public class CoreOpinionFileServiceImpl extends ServiceImpl<CoreOpinionFileDao, CoreOpinionFileEntity> implements CoreOpinionFileService {

	@Autowired
	CoreOpinionFileDao coreOpinionFileDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	Integer opinionId = Integer.parseInt((String) params.get("opinionId"));
    	Page<CoreOpinionFileEntity> page = this.selectPage(
                new Query<CoreOpinionFileEntity>(params).getPage(),
                new EntityWrapper<CoreOpinionFileEntity>().and("opinion_id="+opinionId)
        );	
    	page.setTotal(this.selectCount(new EntityWrapper<CoreOpinionFileEntity>().where("opinion_id="+opinionId)));
        return new PageUtils(page);
    }

	@Override
	public List<CoreOpinionFileEntity> getById(String opinionId) {
		return coreOpinionFileDao.getById(opinionId);
	}

}