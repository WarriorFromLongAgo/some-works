package com.orhon.smartcampus.modules.moral.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.moral.entity.DataStudent;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.hibernate.validator.constraints.EAN;

import java.awt.*;
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
public interface DataStudentMapper extends BaseMapper<DataStudent> {

    List getStudent(Integer moral_column_id);

    void inserts(Integer moral_column_id, Integer student);

    void deletes(Integer id);
}
