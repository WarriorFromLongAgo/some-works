package com.orhon.smartcampus.modules.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.modules.base.entity.Regions;
import com.orhon.smartcampus.framework.mapper.BaseMapper;

/**
 * <p>
 * 地区信息 Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface RegionsMapper extends BaseMapper<Regions> {

	List<Regions> getRegionsList();

}
