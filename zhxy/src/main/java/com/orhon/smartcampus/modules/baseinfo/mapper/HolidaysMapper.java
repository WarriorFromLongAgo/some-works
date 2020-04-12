package com.orhon.smartcampus.modules.baseinfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.baseinfo.entity.Holidays;
import com.orhon.smartcampus.framework.mapper.BaseMapper;

/**
 * <p>
 * 节假日管理 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface HolidaysMapper extends BaseMapper<Holidays> {

}
