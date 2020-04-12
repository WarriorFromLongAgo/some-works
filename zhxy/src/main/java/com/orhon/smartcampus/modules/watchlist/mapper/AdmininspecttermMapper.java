package com.orhon.smartcampus.modules.watchlist.mapper;

import com.orhon.smartcampus.utils.PageDto;
import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.watchlist.entity.Admininspectterm;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 行政值班检查项 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface AdmininspecttermMapper extends BaseMapper<Admininspectterm> {
    List<HashMap<String, Object>> getTerm(@Param("maps") HashMap<String, Object> maps);
    List<HashMap<String, Object>> getTermpage(@Param("maps") HashMap<String, Object> maps, @Param("dto") PageDto dto);

    List getItems(int id);
}
