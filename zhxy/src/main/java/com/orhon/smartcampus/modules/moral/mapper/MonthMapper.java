package com.orhon.smartcampus.modules.moral.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.moral.entity.Month;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface MonthMapper extends BaseMapper<Month> {
    void inserts(@Param("month_id") Integer month_id, @Param("week_id") Integer week_id);
    void deletes(@Param("month_id") Integer month_id);
}
