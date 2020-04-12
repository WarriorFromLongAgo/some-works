package com.orhonit.modules.generator.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.WorkPlanEntity;
import com.orhonit.modules.generator.entity.ZgPlanFileEntity;
@Mapper
public interface WorkPlanUploadDao extends BaseMapper<ZgPlanFileEntity> {

}
