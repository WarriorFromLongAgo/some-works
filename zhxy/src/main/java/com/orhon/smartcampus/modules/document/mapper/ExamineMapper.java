package com.orhon.smartcampus.modules.document.mapper;

import com.orhon.smartcampus.utils.PageDto;
import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.document.entity.Examine;
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
public interface ExamineMapper extends BaseMapper<Examine> {
    List<HashMap<String, Object>> getDocument(@Param("maps") HashMap<String, Object> maps);
    List<HashMap<String, Object>> getDocumentpage(@Param("maps") HashMap<String, Object> maps, @Param("dto") PageDto dto);

    void inserts(Integer document_id, Integer examine_user, Integer parent_id);

    Boolean updates(Integer id, Integer status);
}
