package com.orhon.smartcampus.modules.moral.mapper;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.moral.entity.MonthWeek;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface MonthWeekMapper extends BaseMapper<MonthWeek> {
    String start_time(@Param("week_id") Integer week_id);
    String end_time(@Param("week_id") Integer week_id);

}
