package com.orhon.smartcampus.modules.student.mapper;

import com.orhon.smartcampus.framework.mapper.BaseMapper;
import com.orhon.smartcampus.modules.student.entity.EclassRecords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bao
 */
@Mapper
public interface EclassRecordsMapper extends BaseMapper<EclassRecords> {

    EclassRecords findStudentId(@Param("id") Integer id);

    void eclassRecordsSave(@Param("maps") HashMap<String, Object> maps , @Param("createTime") String createTime , @Param("id") Integer id);

    void eclassRecordsSave2(@Param("maps") HashMap<String, Object> maps , @Param("originalId") Integer originalId , @Param("createTime") String createTime  , @Param("id") Integer id);

    void eclassRecordsUpdate(@Param("id") Integer id);

}
