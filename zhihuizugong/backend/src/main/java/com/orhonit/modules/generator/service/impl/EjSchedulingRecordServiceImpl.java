package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.EjSchedulingRecordDao;
import com.orhonit.modules.generator.entity.EjSchedulingRecordEntity;
import com.orhonit.modules.generator.service.EjSchedulingRecordService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("ejSchedulingRecordService")
public class EjSchedulingRecordServiceImpl extends ServiceImpl<EjSchedulingRecordDao, EjSchedulingRecordEntity> implements EjSchedulingRecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Integer peopleId = Integer.parseInt((String)params.get("peopleId"));
        Page<EjSchedulingRecordEntity> page = this.selectPage(
                new Query<EjSchedulingRecordEntity>(params).getPage(),
                new EntityWrapper<EjSchedulingRecordEntity>()
        );

        return new PageUtils(page);
    }

}