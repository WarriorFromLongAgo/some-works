package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.CoreOpinionReplyEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author xiaobai
 * @email sunlightcs@gmail.com
 * @date 2019-06-20 16:10:56
 */
@Mapper
public interface CoreOpinionReplyDao extends BaseMapper<CoreOpinionReplyEntity> {
    @Delete("DELETE FROM `core_opinion_reply` WHERE opinion_id = #{opinionId}")
    void removeById(String opinionId);
}
