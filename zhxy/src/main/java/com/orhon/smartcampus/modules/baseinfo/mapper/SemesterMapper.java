package com.orhon.smartcampus.modules.baseinfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.baseinfo.entity.Semester;
import com.orhon.smartcampus.framework.mapper.BaseMapper;

import java.util.Date;
import java.util.HashMap;

/**
 * <p>
 * 学期表 Mapper 接口
 * </p>
 *
 * @author bao
 */
@Mapper
public interface SemesterMapper extends BaseMapper<Semester> {


    HashMap getSemesterViaStartAndEnd(Date startAt , Date endAt);

}
