package com.orhonit.ole.sys.service;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.sys.model.SysDictEntity;

public interface SysDictService {

	void saveSysDict(SysDictEntity sysDict);
	
	void updateSysDict(SysDictEntity sysDict);
	
	public int getSysDictCount(Map<String, Object> params) ;

	public List<SysDictEntity> getSysDictList(Map<String, Object> params, Integer start, Integer length);

	void delSysDict(String id);

	SysDictEntity getById(String id);

	List<SysDictEntity> getDictByTypeValue(String typeValue);
	
	Map<String,String> getDictMapByTypeValue(String typeValue);
	
	/*
	 * 根据字典大类和字典小类获取字典名称
	 */
	String getDescByValue(String typeValue,String enumValue);

}