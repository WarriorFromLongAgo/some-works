package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.CorePoliticalEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 生活时时讲
 * 
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-05 16:36:25
 */
@Mapper
public interface CorePoliticalDao extends BaseMapper<CorePoliticalEntity> {

    void batchDeletePolitical(Integer[] ids);

}
