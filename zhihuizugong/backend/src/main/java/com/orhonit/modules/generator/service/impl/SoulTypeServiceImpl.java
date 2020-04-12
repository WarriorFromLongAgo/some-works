package com.orhonit.modules.generator.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.SoulTypeDao;
import com.orhonit.modules.generator.entity.SoulTypeEntity;
import com.orhonit.modules.generator.service.SoulTypeService;

@Service("SoulTypeService")
public class SoulTypeServiceImpl extends ServiceImpl<SoulTypeDao, SoulTypeEntity> implements SoulTypeService{
	
	@Autowired
	SoulTypeDao Dao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		if(!params.get("typeIdentifier").equals("")) {
			 Page<SoulTypeEntity> page = this.selectPage(
		                new Query<SoulTypeEntity>(params).getPage(),
		                new EntityWrapper<SoulTypeEntity>().like(StringUtils.isNotBlank((String)params.get("typeIdentifier")), "type_identifier",(String) params.get("typeIdentifier"))
		        );
			 page.setTotal(this.selectCount(new EntityWrapper<SoulTypeEntity>().like(StringUtils.isNotBlank((String)params.get("typeIdentifier")), "type_identifier",(String) params.get("typeIdentifier"))));
		     return new PageUtils(page);
		}else {
			 Page<SoulTypeEntity> page = this.selectPage(
		                new Query<SoulTypeEntity>(params).getPage(),
		                new EntityWrapper<SoulTypeEntity>()
		        );
		        page.setTotal(this.selectCount(new EntityWrapper<SoulTypeEntity>()));
		        return new PageUtils(page);
		}
		
	}

	@Override
	public void deleteType(Integer typeId) {
		
		Dao.deleteType(typeId);
	}

	@Override
	public void updateType(SoulTypeEntity entity) {
		Dao.updatetype(entity);
		
	}
    
	
	//查出所有分类
	@Override
	public List<SoulTypeEntity> soulTypeList() {
		
		return Dao.soulTypeList();
	}

}
