package com.orhon.smartcampus.modules.material.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.material.entity.Campus;
import com.orhon.smartcampus.framework.mapper.BaseMapper;

/**
 * <p>
 * 校区管理 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface CampusMapper extends BaseMapper<Campus> {

}
