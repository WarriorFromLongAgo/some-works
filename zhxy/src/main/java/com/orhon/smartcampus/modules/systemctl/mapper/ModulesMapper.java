package com.orhon.smartcampus.modules.systemctl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.orhon.smartcampus.modules.systemctl.entity.Modules;
import com.orhon.smartcampus.framework.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface ModulesMapper extends BaseMapper<Modules> {

	List<Modules> getOurschool(@Param("school_id") Long school_id);

}
