package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.CoreWorkCrewDao;
import com.orhonit.modules.generator.entity.CoreWorkCrewEntity;
import com.orhonit.modules.generator.service.CoreWorkCrewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("coreWorkCrewService")
public class CoreWorkCrewServiceImpl extends ServiceImpl<CoreWorkCrewDao, CoreWorkCrewEntity> implements CoreWorkCrewService {

    @Autowired
    CoreWorkCrewDao coreWorkCrewDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Integer serveId = Integer.parseInt((String) params.get("serveId"));
        Page<CoreWorkCrewEntity> page = this.selectPage(
                new Query<CoreWorkCrewEntity>(params).getPage(),
                new EntityWrapper<CoreWorkCrewEntity>().and("serve_id="+serveId)
        );
        page.setTotal(this.selectCount(new EntityWrapper<CoreWorkCrewEntity>().where("serve_id="+serveId)));
        return new PageUtils(page);
    }

    @Override
    public List<CoreWorkCrewEntity> getByServeId(String serveId) {
        return coreWorkCrewDao.getByServeId(serveId);
    }

    @Override
    public void deleteByServeId(String serveId) {
        coreWorkCrewDao.deleteByServeId(serveId);
    }
}