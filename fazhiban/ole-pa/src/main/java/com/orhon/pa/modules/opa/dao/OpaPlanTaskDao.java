/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.dao;

import java.util.List;
import java.util.Map;

import com.orhon.pa.common.persistence.CrudDao;
import com.orhon.pa.common.persistence.annotation.MyBatisDao;
import com.orhon.pa.common.utils.Status;
import com.orhon.pa.modules.opa.entity.OpaPlanTask;

/**
 * 计划任务模块DAO接口
 * @author Shawn
 * @version 2017-04-26
 */
@MyBatisDao
public interface OpaPlanTaskDao extends CrudDao<OpaPlanTask> {

	Double getChildSum(OpaPlanTask parent);
	
	List<OpaPlanTask> findCommonChild(OpaPlanTask opt);
	OpaPlanTask findParentTask(OpaPlanTask opt);

	List<OpaPlanTask> findNotPassList(OpaPlanTask param);

	void setAllToStatus(OpaPlanTask param);
	

	List<OpaPlanTask> findChild(OpaPlanTask task);
	
	List<Status> findListByStatus(Map<String, Object> param);

	void insertList(List<OpaPlanTask> taskList);

	int findListCount(OpaPlanTask opaPlanTask);
}