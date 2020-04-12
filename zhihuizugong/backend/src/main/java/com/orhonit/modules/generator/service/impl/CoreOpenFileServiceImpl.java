package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.CoreOpenFileDao;
import com.orhonit.modules.generator.entity.CoreOpenFileEntity;
import com.orhonit.modules.generator.service.CoreOpenFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("coreOpenFileService")
public class CoreOpenFileServiceImpl extends ServiceImpl<CoreOpenFileDao, CoreOpenFileEntity> implements CoreOpenFileService {

    @Autowired
    CoreOpenFileDao coreOpenFileDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CoreOpenFileEntity> page = this.selectPage(
                new Query<CoreOpenFileEntity>(params).getPage(),
                new EntityWrapper<CoreOpenFileEntity>()
        );
        page.setTotal(this.selectCount(new EntityWrapper<CoreOpenFileEntity>()));
        return new PageUtils(page);
    }

    @Override
    public List<CoreOpenFileEntity> getById(String openId) {
        return coreOpenFileDao.getById(openId);
    }

    @Override
    public void deleteByOpenId(String openId) {
        coreOpenFileDao.deleteByOpenId(openId);
    }
}