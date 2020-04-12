package com.orhonit.modules.religion.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.R;
import com.orhonit.modules.religion.domain.ReligionSiteDomain;
import com.orhonit.modules.religion.entity.ReligionSite;
import com.orhonit.modules.religion.model.ReligionSiteModel;

/**
 * 活动场所
 * 
 * @author cyf
 * @email cyf0477@126.com
 * @version 2018-11-12 15:50:29
 */
public interface ReligionSiteService extends IService<ReligionSite> {
	
	
	/**
	 * 通过活动场所id删除关于场所所有信息
	 */
	public void deleteReligionSiteById(Long id);
	
	/**
	 * 插入活动场所信息
	 * @param religionSiteDomain
	 */
	public void insertOrUpdateReligionSite(ReligionSiteDomain religionSiteDomain);
	
	
	/**
	 * 查询所有活动场所信息,可更具所属嘎查和类型搜索  以及分页
	 * @return
	 */
	public Map<String, Object> selectReligionSiteAll(int page,int pageSize,String type, String gacha);
}
