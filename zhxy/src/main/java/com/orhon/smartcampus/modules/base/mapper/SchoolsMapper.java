package com.orhon.smartcampus.modules.base.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.base.entity.Schools;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 学校 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface SchoolsMapper extends BaseMapper<Schools> {

    public Map getSchoolByID(@Param("id") Long id);

}
