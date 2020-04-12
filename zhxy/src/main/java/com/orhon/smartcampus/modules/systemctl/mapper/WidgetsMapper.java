package com.orhon.smartcampus.modules.systemctl.mapper;

import java.util.List;

import java.util.Map;


import org.apache.ibatis.annotations.Mapper;

import com.orhon.smartcampus.framework.mapper.BaseMapper;
import com.orhon.smartcampus.modules.systemctl.entity.Widgets;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Orhon
 */
@Mapper
public interface WidgetsMapper extends BaseMapper<Widgets> {
	
	//筛选列表
	List<Widgets>WidgetsList(Map<String,Object> map);

	List<Widgets> getList();

}
