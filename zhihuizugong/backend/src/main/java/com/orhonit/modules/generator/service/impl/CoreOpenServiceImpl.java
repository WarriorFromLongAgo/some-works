package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.CoreOpenDao;
import com.orhonit.modules.generator.entity.CoreOpenEntity;
import com.orhonit.modules.generator.service.CoreOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@Service("coreOpenService")
public class CoreOpenServiceImpl extends ServiceImpl<CoreOpenDao, CoreOpenEntity> implements CoreOpenService {

    @Autowired
    CoreOpenDao coreOpenDao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Integer openType = Integer.parseInt((String) params.get("openType"));
    	Page<CoreOpenEntity> page = this.selectPage(
                new Query<CoreOpenEntity>(params).getPage(),
                new EntityWrapper<CoreOpenEntity>().and("open_type="+openType)
        );	
    	page.setTotal(this.selectCount(new EntityWrapper<CoreOpenEntity>().where("open_type="+openType)));
        return new PageUtils(page);
    }

    @Override
    public void insertOpen(CoreOpenEntity coreOpen) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = date.format(new Date());
        coreOpen.setCreateTime(time);
        coreOpenDao.insertOpen(coreOpen);
    }
}