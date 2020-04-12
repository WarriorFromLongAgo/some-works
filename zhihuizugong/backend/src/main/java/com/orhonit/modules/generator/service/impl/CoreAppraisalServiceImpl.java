package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.CoreAppraisalDao;
import com.orhonit.modules.generator.dao.CoreAppraisalFileDao;
import com.orhonit.modules.generator.entity.CoreAppraisalEntity;
import com.orhonit.modules.generator.service.CoreAppraisalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("coreAppraisalService")
public class CoreAppraisalServiceImpl extends ServiceImpl<CoreAppraisalDao, CoreAppraisalEntity> implements CoreAppraisalService {

    @Autowired
    CoreAppraisalDao coreAppraisalDao;

    @Autowired
    CoreAppraisalFileDao coreAppraisalFileDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Integer appraisalType = Integer.parseInt((String) params.get("appraisalType"));
        Page<CoreAppraisalEntity> page = this.selectPage(
                new Query<CoreAppraisalEntity>(params).getPage(),
                new EntityWrapper<CoreAppraisalEntity>().and("appraisal_type="+appraisalType).orderBy("creat_time DESC")
        );
        page.setTotal(this.selectCount(new EntityWrapper<CoreAppraisalEntity>().where("appraisal_type="+appraisalType)));
        return new PageUtils(page);
    }

    @Override
    public void save(CoreAppraisalEntity coreAppraisal) {
        coreAppraisalDao.save(coreAppraisal);
    }

    @Override
    public void deleteByAppraisalId(String appraisalId) {
        coreAppraisalDao.deleteByAppraisalId(appraisalId);
        coreAppraisalFileDao.deleteByAppraisalFileId(appraisalId);
    }
}