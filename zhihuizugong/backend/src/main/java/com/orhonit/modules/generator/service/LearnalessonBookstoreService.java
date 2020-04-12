package com.orhonit.modules.generator.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.LearnalessonBookstoreEntity;

public interface LearnalessonBookstoreService extends IService<LearnalessonBookstoreEntity>{
	
	PageUtils queryPage(Map<String, Object> params);
	
	
	//书苑 单条查询
	LearnalessonBookstoreEntity selectStore(Integer bookeStoreId);
	//书苑 根据编号删除数据
	void deleteStore(Integer bookeStoreId);
    //书苑 根据编号 更新	
	void updateStore(LearnalessonBookstoreEntity entity);

}
