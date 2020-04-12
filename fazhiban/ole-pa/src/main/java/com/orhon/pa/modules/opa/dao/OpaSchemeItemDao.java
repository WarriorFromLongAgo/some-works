/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.dao;

import java.util.List;
import java.util.Map;

import com.orhon.pa.common.persistence.CrudDao;
import com.orhon.pa.common.persistence.annotation.MyBatisDao;
import com.orhon.pa.modules.opa.entity.OpaSchemeItem;

/**
 * 方案指标模块DAO接口
 * @author Shawn
 * @version 2017-04-21
 */
@MyBatisDao
public interface OpaSchemeItemDao extends CrudDao<OpaSchemeItem> {

	OpaSchemeItem getParentSchemeItem(OpaSchemeItem opaSchemeItem);
	
	List<OpaSchemeItem> findListForAssign(OpaSchemeItem opaSchemeItem);

	void editAssign(Map<String, Object> param);

	void auditPass(OpaSchemeItem opaSchemeItem);

	List<OpaSchemeItem> findNotPassList(OpaSchemeItem param);

	void setAllToStatus(OpaSchemeItem param);

	Double getChildSum(OpaSchemeItem parent);

	int getHeadLevel(OpaSchemeItem itemParam);

	List<OpaSchemeItem> findChild(OpaSchemeItem item);

}