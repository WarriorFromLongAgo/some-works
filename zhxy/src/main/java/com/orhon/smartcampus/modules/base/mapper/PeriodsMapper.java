package com.orhon.smartcampus.modules.base.mapper;

import com.orhon.smartcampus.modules.base.entity.Grades;
import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.base.entity.Periods;
import com.orhon.smartcampus.framework.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 学段信息 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface PeriodsMapper extends BaseMapper<Periods> {

    List<Periods> getPeriodList();
}
