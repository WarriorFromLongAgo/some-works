package com.orhonit.ole.sys.service;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.sys.dto.VersionDTO;

public interface VersionService {
	
	/**
	 * 获取列表总和统计
	 * @param params
	 * @return
	 */
	int getCount(Map<String, Object> params);

	/**
	 * 获取版本列表
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 */
	List<VersionDTO> getList(Map<String, Object> params, Integer start, Integer length);

	/**
	 * 保存版本信息
	 * @param versionDTO
	 */
	void save(VersionDTO versionDTO);

	/**
	 * 重新指定某版本为最新更新版
	 * @param params
	 */
	void reset(Map<String, Object> params);

	/**
	 * 获取最新版信息
	 * @return
	 */
	Map<String, Object> getNewVersion();

	String getPath();

	/**
	 * 获取某版本的下载链接
	 * @param id
	 * @return
	 */
	String getVersionUrl(int id);
	
}
