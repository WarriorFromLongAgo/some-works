package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.ZgMeetFlowEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 会议流程
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-17 10:32:02
 */
@Mapper
public interface ZgMeetFlowDao extends BaseMapper<ZgMeetFlowEntity> {

    void save(ZgMeetFlowEntity zgMeetFlow);

    List<ZgMeetFlowEntity> findFlow(String meetId);
}
