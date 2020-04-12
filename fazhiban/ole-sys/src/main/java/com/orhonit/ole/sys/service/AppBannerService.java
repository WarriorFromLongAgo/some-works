package com.orhonit.ole.sys.service;

import java.util.List;
import java.util.Map;
import com.orhonit.ole.sys.dto.AppBannerDTO;

public interface AppBannerService {
	
	/**
	 * 获取列表总和统计
	 * @param params
	 * @return
	 */
	int getCount(Map<String, Object> params);
	
	/** 获取版本列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	List<AppBannerDTO> getList(Map<String, Object> params, Integer start, Integer length);
	
	/** 获取文件路径
	 * @return
	 */
	String getPath();

	/**
	 * 保存banner图信息
	 * @param versionDTO
	 */
	void save(AppBannerDTO appBannerDTO);
	

	/**
	 * 修改banner图信息
	 * @param versionDTO
	 */
	void update(AppBannerDTO appBannerDTO);
	
	/**
	 * 删除banner图信息
	 * @param id
	 */
	void delete(Integer id);
	
	/**
	 * 删除banner图信息
	 * @param id
	 */
	AppBannerDTO findOne(Integer id);
	
	/**
	 * APP获取轮播图
	 * @param roleId
	 * */
	List<AppBannerDTO> findByRoleId(Integer roleId);

}
