/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.dao;

import java.util.List;

import com.orhon.pa.common.persistence.CrudDao;
import com.orhon.pa.common.persistence.annotation.MyBatisDao;
import com.orhon.pa.modules.opa.entity.OpaPerson;
import com.orhon.pa.modules.opa.entity.OpaPlanOffice;

/**
 * 考核计划部门模块DAO接口
 * @author Shawn
 * @version 2017-04-26
 */
@MyBatisDao
public interface OpaPlanOfficeDao extends CrudDao<OpaPlanOffice> {
	
	/**
	 * 
	 * @param opaPerson
	 * @return
	 */
	List<OpaPerson> findListByDeptId(String deptId);

	/**
	 * 考核计划查询--考核对象为人员的时候
	 * @param officeParam
	 * @return
	 */
	List<OpaPlanOffice> findPeopleList(OpaPlanOffice officeParam);
	
}