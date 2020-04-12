package com.orhon.smartcampus.modules.teacher.mapper;

import com.orhon.smartcampus.modules.teacher.entity.TInformation;
import com.orhon.smartcampus.utils.PageDto;
import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.teacher.entity.OfficeArrange;
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
public interface OfficeArrangeMapper extends BaseMapper<OfficeArrange> {
    List<HashMap<String, Object>> getInfos();

    List<HashMap<String, Object>> getOfficeArrange(@Param("maps") HashMap<String, Object> maps);
    List<HashMap<String, Object>> getOfficeArrangepage(@Param("maps") HashMap<String, Object> maps, @Param("dto") PageDto dto);

}
