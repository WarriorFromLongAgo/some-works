package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.EjSchedulingFileDao;
import com.orhonit.modules.generator.entity.EjSchedulingFileEntity;
import com.orhonit.modules.generator.service.EjSchedulingFileService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("ejSchedulingFileService")
public class EjSchedulingFileServiceImpl extends ServiceImpl<EjSchedulingFileDao, EjSchedulingFileEntity> implements EjSchedulingFileService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EjSchedulingFileEntity> page = this.selectPage(
                new Query<EjSchedulingFileEntity>(params).getPage(),
                new EntityWrapper<EjSchedulingFileEntity>()
        );

        return new PageUtils(page);
    }

}