package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.CoreOrganizationFileDao;
import com.orhonit.modules.generator.entity.CoreOrganizationFileEntity;
import com.orhonit.modules.generator.service.CoreOrganizationFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("coreOrganizationFileService")
public class CoreOrganizationFileServiceImpl extends ServiceImpl<CoreOrganizationFileDao, CoreOrganizationFileEntity> implements CoreOrganizationFileService {

    @Autowired
    CoreOrganizationFileDao coreOrganizationFileDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String organizationId = (String) params.get("organizationId");
        Page<CoreOrganizationFileEntity> page = this.selectPage(
                new Query<CoreOrganizationFileEntity>(params).getPage(),
                new EntityWrapper<CoreOrganizationFileEntity>().and("organization_id="+organizationId)
        );
        page.setTotal(this.selectCount(new EntityWrapper<CoreOrganizationFileEntity>().and("organization_id="+organizationId)));
        return new PageUtils(page);
    }

    @Override
    public List<CoreOrganizationFileEntity> getById(String organizationId) {
        return coreOrganizationFileDao.getById(organizationId);
    }
}