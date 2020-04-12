package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.CoreWorkDynamicFileDao;
import com.orhonit.modules.generator.entity.CoreWorkDynamicFileEntity;
import com.orhonit.modules.generator.service.CoreWorkDynamicFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("CoreWorkDynamicFileService")
public class CoreWorkDynamicFileServiceImpl extends ServiceImpl<CoreWorkDynamicFileDao, CoreWorkDynamicFileEntity> implements CoreWorkDynamicFileService {

    @Autowired
    CoreWorkDynamicFileDao coreWorkDynamicFileDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String dynamicId = (String) params.get("dynamicId");
        Page<CoreWorkDynamicFileEntity> page = this.selectPage(
                new Query<CoreWorkDynamicFileEntity>(params).getPage(),
                new EntityWrapper<CoreWorkDynamicFileEntity>().and("dynamic_id="+dynamicId)
        );
        page.setTotal(this.selectCount(new EntityWrapper<CoreWorkDynamicFileEntity>().where("dynamic_id="+dynamicId)));
        return new PageUtils(page);
    }

    @Override
    public void removeById(String dynamicId) {
        coreWorkDynamicFileDao.removeById(dynamicId);
    }
}