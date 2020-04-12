package com.orhon.smartcampus.modules.watchlist.mapper;

import com.orhon.smartcampus.utils.PageDto;
import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.watchlist.entity.Data_log;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 数据添加 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface Data_logMapper extends BaseMapper<Data_log> {
    List<HashMap<String, Object>> getDataLog(@Param("maps") HashMap<String, Object> maps);
    List<HashMap<String, Object>> getDataLogpage(@Param("maps") HashMap<String, Object> maps, @Param("dto") PageDto dto);

    Object getById(Integer id);
}
