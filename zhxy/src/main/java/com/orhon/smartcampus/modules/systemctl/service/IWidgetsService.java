package com.orhon.smartcampus.modules.systemctl.service;



import java.util.List;

import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.modules.systemctl.entity.Widgets;



/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Orhon
 */
public interface IWidgetsService extends BaseService<Widgets> {
	
	//组建条件筛选
	List<Widgets>WidgetsList(Widgets widgets);

	List<Widgets> getList();

}
