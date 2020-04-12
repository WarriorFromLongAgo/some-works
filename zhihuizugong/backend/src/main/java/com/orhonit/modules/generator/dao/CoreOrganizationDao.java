package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.CoreOrganizationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 党组织表
 * 
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-06-20 14:01:54
 */
@Mapper
public interface CoreOrganizationDao extends BaseMapper<CoreOrganizationEntity> {
    void insertOrganization(CoreOrganizationEntity coreOrganization);
}
