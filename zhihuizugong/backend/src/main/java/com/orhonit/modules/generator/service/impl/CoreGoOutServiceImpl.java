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
import com.orhonit.modules.generator.dao.CoreGoOutDao;
import com.orhonit.modules.generator.entity.CoreGoOutEntity;
import com.orhonit.modules.generator.service.CoreGoOutService;



@Service("coreGoOutService")
public class CoreGoOutServiceImpl extends ServiceImpl<CoreGoOutDao, CoreGoOutEntity> implements CoreGoOutService {

	@Autowired
	CoreGoOutDao coreGoOutDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
		String userId =  (String) params.get("userId");
    	Page<CoreGoOutEntity> page = this.selectPage(
                new Query<CoreGoOutEntity>(params).getPage(),
                new EntityWrapper<CoreGoOutEntity>().and(StringUtils.isNotBlank(userId) , "user_id=" + userId).orderBy("go_out_time DESC")
        );	
    	page.setTotal(this.selectCount(new EntityWrapper<CoreGoOutEntity>().and(StringUtils.isNotBlank(userId) , "user_id=" + userId)));
        return new PageUtils(page);
    }

}