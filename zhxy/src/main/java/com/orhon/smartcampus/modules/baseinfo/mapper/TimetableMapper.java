package com.orhon.smartcampus.modules.baseinfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.baseinfo.entity.Timetable;
import com.orhon.smartcampus.framework.mapper.BaseMapper;

/**
 * <p>
 * 课节信息表 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface TimetableMapper extends BaseMapper<Timetable> {

}
