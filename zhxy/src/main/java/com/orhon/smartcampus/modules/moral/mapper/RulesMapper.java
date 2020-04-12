package com.orhon.smartcampus.modules.moral.mapper;

import com.orhon.smartcampus.utils.PageDto;
import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.moral.entity.Rules;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 德育量化项目细则表 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface RulesMapper extends BaseMapper<Rules> {
    List<HashMap<String, Object>> getRule(@Param("maps") HashMap<String, Object> maps);
    List<HashMap<String, Object>> getRulepage(@Param("maps") HashMap<String, Object> maps, @Param("dto") PageDto dto);

}
