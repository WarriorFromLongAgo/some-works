package com.orhon.smartcampus.modules.watchlist.mapper;

import com.orhon.smartcampus.utils.PageDto;
import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.watchlist.entity.Dutyleader;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 值班领导、环节干部 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface DutyleaderMapper extends BaseMapper<Dutyleader> {
    List<HashMap<String, Object>> getLeader(@Param("maps") HashMap<String, Object> maps);
    List<HashMap<String, Object>> getLeaderpage(@Param("maps") HashMap<String, Object> maps, @Param("dto") PageDto dto);

}
