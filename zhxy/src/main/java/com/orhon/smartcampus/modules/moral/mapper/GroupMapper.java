package com.orhon.smartcampus.modules.moral.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.moral.entity.Group;
import com.orhon.smartcampus.framework.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 班级分组表 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface GroupMapper extends BaseMapper<Group> {
    void inserts(@Param("group_id") Integer group_id, @Param("class_id") Integer class_id);
    void deletes(@Param("group_id") Integer group_id);
}
