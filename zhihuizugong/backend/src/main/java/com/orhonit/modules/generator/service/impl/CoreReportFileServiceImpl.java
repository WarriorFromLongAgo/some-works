package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.CoreReportFileDao;
import com.orhonit.modules.generator.entity.CoreReportFileEntity;
import com.orhonit.modules.generator.service.CoreReportFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("coreReportFileService")
public class CoreReportFileServiceImpl extends ServiceImpl<CoreReportFileDao, CoreReportFileEntity> implements CoreReportFileService {

    @Autowired
    CoreReportFileDao coreReportFileDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CoreReportFileEntity> page = this.selectPage(
                new Query<CoreReportFileEntity>(params).getPage(),
                new EntityWrapper<CoreReportFileEntity>()
        );
        page.setTotal(this.selectCount(new EntityWrapper<CoreReportFileEntity>()));
        return new PageUtils(page);
    }

    @Override
    public List<CoreReportFileEntity> getById(String reportId) {
        return coreReportFileDao.getById(reportId);
    }

    @Override
    public void removeById(String reportId) {
        coreReportFileDao.removeById(reportId);
    }
}