package com.orhon.smartcampus.modules.systemctl.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.systemctl.entity.Schoolsettings;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * <p>
 * 学校设置 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface SchoolsettingsMapper extends BaseMapper<Schoolsettings> {

    HashMap getBySchoolID(@Param("school_id") Integer schoolid);
}
