package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.CoreLeaveDao;
import com.orhonit.modules.generator.entity.CoreLeaveEntity;
import com.orhonit.modules.generator.entity.CoreOpinionEntity;
import com.orhonit.modules.generator.service.CoreLeaveService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("coreLeaveService")
public class CoreLeaveServiceImpl extends ServiceImpl<CoreLeaveDao, CoreLeaveEntity> implements CoreLeaveService {

	@Autowired
	CoreLeaveDao coreLeaveDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
		String userId =  (String) params.get("userId");
		Page<CoreLeaveEntity> page = this.selectPage(
				new Query<CoreLeaveEntity>(params).getPage(),
				new EntityWrapper<CoreLeaveEntity>().and(StringUtils.isNotBlank(userId) , "user_id=" + userId).orderBy("leave_time DESC")
		);
		page.setTotal(this.selectCount(new EntityWrapper<CoreLeaveEntity>().and(StringUtils.isNotBlank(userId) , "user_id=" + userId)));
		return new PageUtils(page);
    }

}