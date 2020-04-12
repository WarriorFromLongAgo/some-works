package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.CoreOrganizationDao;
import com.orhonit.modules.generator.entity.CoreOrganizationEntity;
import com.orhonit.modules.generator.service.CoreOrganizationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("coreOrganizationService")
public class CoreOrganizationServiceImpl extends ServiceImpl<CoreOrganizationDao, CoreOrganizationEntity> implements CoreOrganizationService {

    @Autowired
    CoreOrganizationDao coreOrganizationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String type =  (String) params.get("type");
        Page<CoreOrganizationEntity> page = this.selectPage(
                new Query<CoreOrganizationEntity>(params).getPage(),
                new EntityWrapper<CoreOrganizationEntity>().and(StringUtils.isNotBlank(type) , "type="+type).orderBy("create_time DESC")
        );
        page.setTotal(this.selectCount(new EntityWrapper<CoreOrganizationEntity>().and(StringUtils.isNotBlank(type) , "type="+type)));
        return new PageUtils(page);
    }

    @Override
    public void insertOrganization(CoreOrganizationEntity coreOrganization) {
        coreOrganizationDao.insertOrganization(coreOrganization);
    }
}