package com.orhonit.ole.sys.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.sys.dao.DictDao;
import com.orhonit.ole.sys.model.SysDictEntity;
import com.orhonit.ole.sys.repository.SysDictRepository;
import com.orhonit.ole.sys.service.SysDictService;

@Service
public class SysDictServiceImpl implements SysDictService {

	@Autowired
	private SysDictRepository sysDictRepository;
	
	@Autowired
	private DictDao dictDao;
	
	@Override
	public void saveSysDict(SysDictEntity sysDictEntity) {
		sysDictEntity.setId(UUID.randomUUID().toString());
		sysDictEntity.setCreatedDate(new Date());
		sysDictRepository.save(sysDictEntity);
	}

	@Override
	public void updateSysDict(SysDictEntity sysDict) {
		 sysDict.setLastUpdate(new Date());
		 this.sysDictRepository.save(sysDict);
	}
	
	@Override
	public int getSysDictCount(Map<String, Object> params) {
		return dictDao.count(params);
	}

	@Override
	public List<SysDictEntity> getSysDictList(Map<String, Object> params, Integer start, Integer length) {
		List<SysDictEntity> list =this.dictDao.list(params, start,length);
		return list;
	}

	@Override
	public SysDictEntity getById(String id) {
		return  sysDictRepository.getOne(id);
		
	}

	@Override
	public List<SysDictEntity> getDictByTypeValue(String typeValue) {
		return this.sysDictRepository.findByTypeValueOrderBySortAsc(typeValue);
	}

	@Override
	public String getDescByValue(String typeValue, String enumValue) {
		String desc=dictDao.getDescByValue(typeValue,enumValue);
		return desc;
	}

	@Override
	public void delSysDict(String id) {
		sysDictRepository.delete(id);
	}

	@Override
	public Map<String, String> getDictMapByTypeValue(String typeValue) {
		List<SysDictEntity> dicts = this.sysDictRepository.findByTypeValueOrderBySortAsc(typeValue);
		Map<String, String> dictMap = new HashMap<String, String>();
		for (SysDictEntity sysDictEntity : dicts) {
			dictMap.put(sysDictEntity.getEnumValue(), sysDictEntity.getEnumDesc());
		}
		return dictMap;
	}

}
