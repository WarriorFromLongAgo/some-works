package com.orhon.smartcampus.modules.watchlist.mapper;

import com.orhon.smartcampus.utils.PageDto;
import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.watchlist.entity.Administrativeduty;
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
public interface AdministrativedutyMapper extends BaseMapper<Administrativeduty> {
    List<HashMap<String, Object>> getDuty(@Param("maps") HashMap<String, Object> maps);
    List<HashMap<String, Object>> getDutypage(@Param("maps") HashMap<String, Object> maps, @Param("dto") PageDto dto);

    List getDutyTerm(Integer duty_id);
}
