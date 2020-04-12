package com.orhon.smartcampus.modules.base.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.base.entity.Subjects;
import com.orhon.smartcampus.framework.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 学科 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface SubjectsMapper extends BaseMapper<Subjects> {

    void subjectToGrade(List<Object> list);
    void subjectToPeriod(List<Object> list);
}
