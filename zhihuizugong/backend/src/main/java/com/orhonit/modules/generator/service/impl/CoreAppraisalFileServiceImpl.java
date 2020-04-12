package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.dao.CoreAppraisalFileDao;
import com.orhonit.modules.generator.entity.CoreAppraisalFileEntity;
import com.orhonit.modules.generator.service.CoreAppraisalFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("coreAppraisalFileService")
public class CoreAppraisalFileServiceImpl extends ServiceImpl<CoreAppraisalFileDao, CoreAppraisalFileEntity> implements CoreAppraisalFileService {

    @Autowired
    CoreAppraisalFileDao coreAppraisalFileDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return null;
    }

    @Override
    public List<CoreAppraisalFileEntity> getByAppraisalId(String appraisalId) {
        return coreAppraisalFileDao.getByAppraisalId(appraisalId);
    }
}