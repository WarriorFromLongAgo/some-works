package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.EjSchedulingEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * 调度主表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 21:04:44
 */
@Mapper
public interface EjSchedulingDao extends BaseMapper<EjSchedulingEntity> {

    void save(EjSchedulingEntity ejScheduling);

    @Update("update ej_scheduling set stats = 3 where id = #{schedulingId}")
    void updateStatus(String schedulingId);
    @Update("update ej_scheduling set stats = 2 where id = #{id}")
    void updateStats(String id);
}
