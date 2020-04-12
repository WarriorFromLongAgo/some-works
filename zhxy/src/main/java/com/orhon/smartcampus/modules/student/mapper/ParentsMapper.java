package com.orhon.smartcampus.modules.student.mapper;

import org.apache.ibatis.annotations.Mapper;


import com.orhon.smartcampus.modules.student.entity.Parents;
import com.orhon.smartcampus.framework.mapper.BaseMapper;

/**
 * <p>
 * 学生家长关系表 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface ParentsMapper extends BaseMapper<Parents> {

}
