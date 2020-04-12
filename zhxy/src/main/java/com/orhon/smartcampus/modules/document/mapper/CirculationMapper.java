package com.orhon.smartcampus.modules.document.mapper;

import com.google.gson.JsonObject;
import com.orhon.smartcampus.utils.PageDto;
import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.document.entity.Circulation;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface CirculationMapper extends BaseMapper<Circulation> {
    void inserts(@Param("document_id") Integer document_id, @Param("content") JsonObject content);
    List<HashMap<String, Object>> getDocument(@Param("maps") HashMap<String, Object> maps);
    List<HashMap<String, Object>> getDocumentpage(@Param("maps") HashMap<String, Object> maps, @Param("dto") PageDto dto);

    void insertExamineUser(Integer document_id, Integer examine_user);
}
