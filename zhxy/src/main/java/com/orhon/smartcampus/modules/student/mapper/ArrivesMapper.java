package com.orhon.smartcampus.modules.student.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.student.entity.Arrives;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 届 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface ArrivesMapper extends BaseMapper<Arrives> {

    List<HashMap<String, Object>> PageListArrives(@Param("map") HashMap<String, Object> map);
}
