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
import com.orhonit.modules.generator.dao.LearnalessonBookstoreDao;
import com.orhonit.modules.generator.entity.LearnalessonBookstoreEntity;
import com.orhonit.modules.generator.service.LearnalessonBookstoreService;

@Service("LearnalessonBookstoreService")
public class LearnalessonBookstoreImpl extends ServiceImpl<LearnalessonBookstoreDao, LearnalessonBookstoreEntity> 
                             implements LearnalessonBookstoreService{
	
	@Autowired
	LearnalessonBookstoreDao Dao;
	
	
	//书苑 根据编号查询
	@Override
	public LearnalessonBookstoreEntity selectStore(Integer bookeStoreId) {
		
		return Dao.selectStore(bookeStoreId);
	}
    
	
	//书苑  根据编号删除
	@Override
	public void deleteStore(Integer bookeStoreId) {
		Dao.deleteStore(bookeStoreId);
	}

    //书苑 根据编号更新
	@Override
	public void updateStore(LearnalessonBookstoreEntity entity) {
		Dao.updateStore(entity);
		
	}


	@Override
	public PageUtils queryPage(Map<String, Object> params) {
    		Page<LearnalessonBookstoreEntity> page = this.selectPage(
            		new Query<LearnalessonBookstoreEntity>(params).getPage(),
                    new EntityWrapper<LearnalessonBookstoreEntity>()
                    .like(StringUtils.isNotBlank((String)params.get("bookstoreName")), "bookstore_name", (String)params.get("bookstoreName"))
	                .eq((String)params.get("bookstoreType") !=null,"bookstore_type", (String)params.get("bookstoreType"))
            );
            page.setTotal(this.selectCount(new EntityWrapper<LearnalessonBookstoreEntity>()));
            return new PageUtils(page);
    	
	}

}
