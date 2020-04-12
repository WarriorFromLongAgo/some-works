package com.orhon.smartcampus.modules.moral.mapper;

import com.orhon.smartcampus.utils.PageDto;
import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.moral.entity.Item;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 德育量化项目表 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface ItemMapper extends BaseMapper<Item> {
    List<HashMap<String, Object>> getItem(@Param("maps") HashMap<String, Object> maps);
    List<HashMap<String, Object>> getItempage(@Param("maps") HashMap<String, Object> maps, @Param("dto") PageDto dto);

}
