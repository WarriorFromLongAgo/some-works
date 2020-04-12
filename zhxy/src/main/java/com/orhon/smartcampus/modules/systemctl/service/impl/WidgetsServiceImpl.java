package com.orhon.smartcampus.modules.systemctl.service.impl;

import com.orhon.smartcampus.modules.systemctl.entity.Widgets;
import com.orhon.smartcampus.modules.systemctl.mapper.WidgetsMapper;
import com.orhon.smartcampus.modules.systemctl.service.IWidgetsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class WidgetsServiceImpl extends BaseServiceImpl<WidgetsMapper, Widgets>implements IWidgetsService {
	
	@Autowired
	WidgetsMapper widgetsMapper;

	@Override
	public List<Widgets> WidgetsList(Widgets widgets) {
		//Map<String,Object>map = new HashMap<String,Object>();
		String title = widgets.getTitle();
		/*
		 * boolean isChine=ChineseAndEnglishUtils.isChinese(title); boolean
		 * isEcglish=ChineseAndEnglishUtils.isEnglish(title); if(isChine) {
		 * map.put("zh", title); System.out.println("这是中文"); }else if(isEcglish) {
		 * map.put("en", title); System.out.println("这是英文"); }else {
		 * map.put("mn",title); System.out.println("这是蒙文"); }
		 */
		//map.put("apis", widgets.getApis());
		 QueryWrapper<Widgets> queryWrapper = new QueryWrapper<>();
		 queryWrapper.like(StringUtils.isNotBlank(widgets.getTitle()),"title", title)
		 .eq(StringUtils.isNotBlank(widgets.getApis()),"apis", widgets.getApis())
		 .eq(StringUtils.isNotBlank(widgets.getType()),"type",widgets.getType())
		 .eq(widgets.getMenu_id()>0, "menu_id",widgets.getMenu_id())
		 .eq(widgets.getStatus()>0,"status",widgets.getStatus())
		 .eq(widgets.getModule_id()>0,"module_id", widgets.getModule_id())
		 .like(StringUtils.isNotBlank(widgets.getClients()),"clients", widgets.getClients());
		List<Widgets> list = widgetsMapper.selectList(queryWrapper);
		//List<Widgets>list=widgetsMapper.WidgetsList(map);
		 //QueryWrapper<Widgets> queryWrapper = new QueryWrapper<>();
		 //queryWrapper.eq("title", "%"+widgets.getTitle()+"%");
		//List t=widgetsMapper.selectList(queryWrapper);
		return list;
	}

	@Autowired
	private WidgetsMapper mapper;
	
	@Override
	public List<Widgets> getList() {
		// TODO Auto-generated method stub
		return mapper.getList();
	}

}
