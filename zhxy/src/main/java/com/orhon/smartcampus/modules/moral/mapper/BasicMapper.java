package com.orhon.smartcampus.modules.moral.mapper;

import com.orhon.smartcampus.modules.teacher.entity.TInformation;
import com.orhon.smartcampus.utils.PageDto;
import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.moral.entity.Basic;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface BasicMapper extends BaseMapper<Basic> {
    List<HashMap<String, Object>> getBase(@Param("maps") HashMap<String, Object> maps);
    List<HashMap<String, Object>> getBasepage(@Param("maps") HashMap<String, Object> maps, @Param("dto") PageDto dto);

}
