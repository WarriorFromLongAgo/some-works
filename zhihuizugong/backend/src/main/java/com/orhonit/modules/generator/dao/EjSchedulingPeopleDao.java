package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.EjSchedulingPeopleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 调度参会人
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 21:04:44
 */
@Mapper
public interface EjSchedulingPeopleDao extends BaseMapper<EjSchedulingPeopleEntity> {

    @Update("update ej_scheduling_people set title")
    void updateTaskClaim(EjSchedulingPeopleEntity ejSchedulingPeople);

    @Select("select user_id from ej_scheduling_people where scheduling_id = #{schedulingId}")
    List<Long> selectJoinPeople(String schedulingId);
}
