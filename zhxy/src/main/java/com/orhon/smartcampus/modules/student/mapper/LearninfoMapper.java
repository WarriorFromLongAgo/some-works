package com.orhon.smartcampus.modules.student.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhon.smartcampus.modules.student.entity.Learninfo;
import com.orhon.smartcampus.framework.mapper.BaseMapper;

/**
 * <p>
 * 学生学籍基本信息表 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface LearninfoMapper extends BaseMapper<Learninfo> {


}
