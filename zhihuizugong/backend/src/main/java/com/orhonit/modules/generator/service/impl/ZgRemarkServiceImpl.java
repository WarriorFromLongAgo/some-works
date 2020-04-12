package com.orhonit.modules.generator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.ZgRemarkDao;
import com.orhonit.modules.generator.entity.ZgRemarkEntity;
import com.orhonit.modules.generator.service.ZgRemarkService;

import java.util.Date;
import java.util.Map;
import java.util.UUID;



@Service("zgRemarkService")
public class ZgRemarkServiceImpl extends ServiceImpl<ZgRemarkDao, ZgRemarkEntity> implements ZgRemarkService {

	@Autowired
	private ZgRemarkDao zgRemarkDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String planId = params.get("planId").toString();
        Page<ZgRemarkEntity> page = this.selectPage(
                new Query<ZgRemarkEntity>(params).getPage(),
                new EntityWrapper<ZgRemarkEntity>().where("plan_id = "+"'"+planId+"'")
        );
        page.setTotal(this.selectCount(new EntityWrapper<ZgRemarkEntity>().where("plan_id = "+"'"+planId+"'")));
        return new PageUtils(page);
    }

	@Override
	public void save(ZgRemarkEntity zgRemark) {
		zgRemark.setId(UUID.randomUUID().toString().replace("-", ""));
		zgRemark.setCreateTime(new Date());
		zgRemarkDao.save(zgRemark);
	}

}