package com.orhonit.ole.enforce.service.checktype.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orhonit.ole.enforce.dao.CheckTypeDao;
import com.orhonit.ole.enforce.entity.CheckTypeEntity;
import com.orhonit.ole.enforce.service.checktype.CheckTypeService;

@Service
public class CheckTypeServiceImpl implements CheckTypeService {
	
	@Autowired
	private CheckTypeDao checkTypeDao;
	
	//检查类型列表
	@Override
	public List<CheckTypeEntity> getCheckTypeList(Map<String, Object> params, Integer start, Integer length) {
		return this.checkTypeDao.getCheckTypeList(params, start, length);
	}

	//检查类型统计
	@Override
	public Integer getCheckTypeCount(Map<String, Object> params) {
		return this.checkTypeDao.getCheckTypeCount(params);
	}

	//添加检查类型
	@Override
	public void save(CheckTypeEntity checkTypeEntity) {
		checkTypeDao.save(checkTypeEntity);
	}

	//修改检查类型
	@Override
	public void update(CheckTypeEntity checkTypeEntity) {
		checkTypeDao.update(checkTypeEntity);	
	}

	//删除检查类型
	@Override
	public void delete(CheckTypeEntity checkTypeEntity) {
		checkTypeDao.delete(checkTypeEntity);
	}

	//根据ID查询
	@Override
	public CheckTypeEntity finCheckTypeById(int id) {	
		return checkTypeDao.finCheckTypeById(id);
	}
	
	//根据部门ID查找title
	@Override
	public List<CheckTypeEntity> checkTypeByDeotId(String deptID) {
		// TODO Auto-generated method stub
		return checkTypeDao.checkTypeByDeotIdEntity(deptID);
	}
	
	

}
