package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.CoreReportDao;
import com.orhonit.modules.generator.dao.CoreReportFileDao;
import com.orhonit.modules.generator.entity.CoreReportEntity;
import com.orhonit.modules.generator.service.CoreReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("coreReportService")
public class CoreReportServiceImpl extends ServiceImpl<CoreReportDao, CoreReportEntity> implements CoreReportService {

    @Autowired
    CoreReportDao coreReportDao;

    @Autowired
    CoreReportFileDao coreReportFileDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Integer reportType = Integer.parseInt((String) params.get("reportType"));
        Page<CoreReportEntity> page = this.selectPage(
                new Query<CoreReportEntity>(params).getPage(),
                new EntityWrapper<CoreReportEntity>().and("report_type="+reportType).orderBy("creat_time DESC")
        );
        page.setTotal(this.selectCount(new EntityWrapper<CoreReportEntity>().where("report_type="+reportType)));
        return new PageUtils(page);
    }

    @Override
    public void insertReport(CoreReportEntity coreReport) {
        coreReportDao.insertReport(coreReport);
    }

    @Override
    public void deleteByReportId(String reportId) {
        coreReportDao.deleteByReportId(reportId);
        coreReportFileDao.removeById(reportId);
    }
}