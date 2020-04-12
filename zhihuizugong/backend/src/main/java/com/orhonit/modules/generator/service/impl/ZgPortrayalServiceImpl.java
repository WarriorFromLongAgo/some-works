package com.orhonit.modules.generator.service.impl;

import com.orhonit.modules.generator.entity.ZgPortrayalValueEntity;
import com.orhonit.modules.generator.entity.ZgRemarkEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.ZgPortrayalDao;
import com.orhonit.modules.generator.entity.ZgPortrayalEntity;
import com.orhonit.modules.generator.service.WywNoteService;
import com.orhonit.modules.generator.service.ZgPortrayalService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service("zgPortrayalService")
public class ZgPortrayalServiceImpl extends ServiceImpl<ZgPortrayalDao, ZgPortrayalEntity> implements ZgPortrayalService {

	@Autowired
	private ZgPortrayalDao zgPortrayalDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    		if (params.get("type") != null){
    			Integer type = Integer.parseInt(params.get("type").toString());
				Page<ZgPortrayalEntity> page = this.selectPage(
						new Query<ZgPortrayalEntity>(params).getPage(),
						new EntityWrapper<ZgPortrayalEntity>().where("type = "+type)
				);
				page.setTotal(this.selectCount(new EntityWrapper<ZgPortrayalEntity>().where("type = "+type)));
				return new PageUtils(page);
			}else {
				Page<ZgPortrayalEntity> page = this.selectPage(
						new Query<ZgPortrayalEntity>(params).getPage(),
						new EntityWrapper<ZgPortrayalEntity>()
				);
				page.setTotal(this.selectCount(new EntityWrapper<ZgPortrayalEntity>()));
				return new PageUtils(page);
			}
    }

	@Override
	public void save(ZgPortrayalEntity zgPortrayal) {
    	List<ZgPortrayalEntity> list = zgPortrayalDao.findList(zgPortrayal.getType());
    	if (list != null && list.size() > 0){
			zgPortrayal.setId(list.get(0).getId());
			this.updatePortrayal(zgPortrayal);
		}else {
			zgPortrayal.setId(UUID.randomUUID().toString().replace("-",""));
			zgPortrayal.setUpdateTime(new Date());
			zgPortrayalDao.save(zgPortrayal);
		}
	}

	@Override
	public void updatePortrayal(ZgPortrayalEntity zgPortrayal) {
		Integer type = zgPortrayal.getType();
		String id = zgPortrayal.getId();
		zgPortrayal.setUpdateTime(new Date());
		zgPortrayalDao.update(zgPortrayal,new EntityWrapper<ZgPortrayalEntity>().where("type = "+type).and("id = "+"'"+id+"'"));
	}
}