package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.CoreOrganizationFileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 党组织附件表
 * 
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-06-20 14:14:55
 */
@Mapper
public interface CoreOrganizationFileDao extends BaseMapper<CoreOrganizationFileEntity> {

    @Select("select * from core_organization_file where organization_id = #{organizationId}")
    List<CoreOrganizationFileEntity> getById(String organizationId);
}
