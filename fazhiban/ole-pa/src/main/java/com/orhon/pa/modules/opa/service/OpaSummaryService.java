/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhon.pa.common.mapper.JsonMapper;
import com.orhon.pa.common.persistence.AutoHeader;
import com.orhon.pa.common.service.TreeService;
import com.orhon.pa.modules.opa.dao.OpaItemDao;
import com.orhon.pa.modules.opa.dao.OpaPlanDao;
import com.orhon.pa.modules.opa.dao.OpaPlanOfficeDao;
import com.orhon.pa.modules.opa.dao.OpaPlanTaskDao;
import com.orhon.pa.modules.opa.dao.OpaSchemeItemDao;
import com.orhon.pa.modules.opa.entity.OpaItem;
import com.orhon.pa.modules.opa.entity.OpaPlan;
import com.orhon.pa.modules.opa.entity.OpaPlanOffice;
import com.orhon.pa.modules.opa.entity.OpaPlanTask;
import com.orhon.pa.modules.opa.entity.OpaSchemeItem;
import com.orhon.pa.modules.sys.entity.Office;
import com.orhon.pa.modules.sys.utils.DictUtils;

/**
 * 指标管理模块Service
 * 
 * @author Shawn
 * @version 2017-04-18
 */
@Service
@Transactional(readOnly = true)
public class OpaSummaryService extends TreeService<OpaItemDao, OpaItem> {
	@Autowired
	OpaPlanTaskDao opaPlanTaskDao;
	@Autowired
	OpaSchemeItemDao opaSchemeItemDao;
	@Autowired
	OpaPlanDao opaPlanDao;
	@Autowired
	OpaPlanOfficeDao opaPlanOfficeDao;
	

	public Map<String, Object> getHeadAndDataMap(String planId) {
		OpaPlan opaPlan = opaPlanDao.get(planId);
		OpaSchemeItem itemParam = new OpaSchemeItem();
		itemParam.setSchemeId(opaPlan.getSchemeId());
		int headLevel = opaSchemeItemDao.getHeadLevel(itemParam);
		itemParam.setItemParentId("0");
		List<OpaSchemeItem> parentItemList = opaSchemeItemDao.findList(itemParam);
		Map<String, Object> headAndData= new HashMap<String, Object>();
		List<Object> headList = new ArrayList<Object>();
		int treeCode = 10;
		for (OpaSchemeItem parentItem : parentItemList) {
			treeCode++;
			AutoHeader dept = new AutoHeader();
			dept.setChecked("1");
			dept.setCssstyle("");
			dept.setHeadDesc("");
			dept.setId("DEPT000");
			dept.setIsleaf("Y");
			dept.setIsopen("0");
			dept.setLevel(String.valueOf((parentItem.getLevel())));
			dept.setName("被考核对象");
			dept.setPid("");
			
			dept.setReportColumnName("rep_org_name");
			dept.setReportColumnType("String");
			dept.setTransanalyzz("");
			dept.setSts("Y");
			dept.setTreeCode(String.valueOf(treeCode));
			dept.setType("opa");
			headList.add(dept);
			treeCode++;
			AutoHeader header = new AutoHeader();
			header.setChecked("1");
			header.setCssstyle("");
			if(parentItem.getType().equals(DictUtils.getDictValue("个性指标", "opa_item_type", ""))){
				header.setHeadDesc(parentItem.getName());
				header.setName(parentItem.getName());
			}else{
				header.setHeadDesc(parentItem.getName() + " (" + parentItem.getValue() + " 分)");
				header.setName(parentItem.getName() + " (" + parentItem.getValue() + " 分)");
			}
			
			header.setId(parentItem.getItemId());
			List<OpaSchemeItem> childList = opaSchemeItemDao.findChild(parentItem);
			if(childList!=null&&!childList.isEmpty()){
				header.setIsleaf("N");
				header.setReportColumnName("");
			}else{
				header.setIsleaf("Y");
				header.setReportColumnName(parentItem.getItemId());
			}
			header.setIsopen("0");
			header.setLevel(String.valueOf((parentItem.getLevel())));
			header.setPid(parentItem.getItemParentId());
			header.setReportColumnType("Number");
			header.setTransanalyzz("");
			header.setSts("Y");
			header.setTreeCode(String.valueOf(treeCode));
			header.setType("opa");
			headList.add(header);
			this.findItemChild(parentItem, headList, treeCode);
		}
		headAndData.put("header", JsonMapper.toJsonString(headList));
		
		//获取数据集
		List<Object> dataList = new ArrayList<Object>();
		List<Object> deptList = new ArrayList<Object>();
		OpaPlanOffice officeParam = new OpaPlanOffice();
		OpaPlanTask taskParam = new OpaPlanTask();
		taskParam.setPlanId(planId);
		taskParam.setMethod(DictUtils.getDictValue("机器汇总", "opa_item_method", ""));
		officeParam.setPlanId(planId);
		List<OpaPlanOffice> officeList;
		//objectType 考核对象  1：部门 2：人员
		if(opaPlan.getObjectType()!=null&&opaPlan.getObjectType().endsWith("2"))
		{
			officeList = opaPlanOfficeDao.findPeopleList(officeParam);
		}else{
			officeList = opaPlanOfficeDao.findList(officeParam);
		}
		for(OpaPlanOffice office : officeList){
			Office off = new Office();
			//objectType 考核对象  1：部门 2：人员
			if(opaPlan.getObjectType()!=null&&opaPlan.getObjectType().endsWith("1"))
			{
				off.setId(office.getOffice().getId());
			}
			taskParam.setOffice(off);
			List<OpaPlanTask> taskList = opaPlanTaskDao.findList(taskParam);
			Map<String,String> deptMap = new HashMap<String,String>();
			//objectType 考核对象  1：部门 2：人员
			if(office.getObjectType()!=null&&office.getObjectType().endsWith("2"))
			{
				deptMap.put("id", office.getPeopleId());
				deptMap.put("name", office.getPeopleName());
			}else{
				deptMap.put("id", office.getOffice().getId());
				deptMap.put("name", office.getOffice().getName());
			}
			deptMap.put("pid", "");
			deptMap.put("open", "false");
			deptList.add(deptMap);
			Map<String,String> dataMap = new HashMap<String,String>();
			//objectType 考核对象  1：部门 2：人员
			if(office.getObjectType()!=null&&office.getObjectType().endsWith("2"))
			{
				dataMap.put("rep_org_code", office.getPeopleId());
				dataMap.put("rep_org_name", office.getPeopleName());//数据行单位的标示字段名，标明该行数据归属的说明，将会横向成为统计表格的表头
				dataMap.put("id", office.getPeopleId());
			}else{
				dataMap.put("rep_org_code", office.getOffice().getId());
				dataMap.put("rep_org_name", office.getOffice().getName());//数据行单位的标示字段名，标明该行数据归属的说明，将会横向成为统计表格的表头
				dataMap.put("id", office.getOffice().getId());
			}
			dataMap.put("main_id", "");
			dataMap.put("report_id", "");
			for(OpaPlanTask task : taskList){
				String score = task.getScore() == null? "0": String.valueOf(task.getScore());
				dataMap.put(task.getItemId(), score);
			}
			dataList.add(dataMap);
		}
		
		headAndData.put("data", JsonMapper.toJsonString(dataList));
		headAndData.put("headLevel", headLevel+1);
		headAndData.put("startLevel", headLevel+1);
		headAndData.put("dept", JsonMapper.toJsonString(deptList));
		return headAndData;
	}

	private void findItemChild(OpaSchemeItem item, List<Object> headList, int treeCode) {
		int childTreeCode = treeCode * 100;
		List<OpaSchemeItem> childList = opaSchemeItemDao.findChild(item);
		if (childList != null) {
			for (int i = 0; i < childList.size(); i++) {
				OpaSchemeItem child = childList.get(i);
				childTreeCode++;
				AutoHeader childHeader = new AutoHeader();
				childHeader.setChecked("1");
				childHeader.setCssstyle("");
				if(child.getType().equals(DictUtils.getDictValue("个性指标", "opa_item_type", ""))){
					childHeader.setHeadDesc(child.getName());
					childHeader.setName(child.getName());
				}else{
					childHeader.setHeadDesc(child.getName() + " (" + child.getValue() + " 分)");
					childHeader.setName(child.getName() + " (" + child.getValue() + " 分)");
				}
				childHeader.setId(child.getItemId());
				List<OpaSchemeItem> hasChild = opaSchemeItemDao.findChild(child);
				if(hasChild!=null&&!hasChild.isEmpty()){
					childHeader.setIsleaf("N");
					childHeader.setReportColumnName("");
				}else{
					childHeader.setIsleaf("Y");
					childHeader.setReportColumnName(child.getItemId());
				}
				childHeader.setIsopen("0");
				childHeader.setLevel(String.valueOf((child.getLevel())));
				childHeader.setPid(child.getItemParentId());
				childHeader.setReportColumnType("Number");
				childHeader.setTransanalyzz("");
				childHeader.setSts("Y");
				childHeader.setTreeCode(String.valueOf(childTreeCode));
				childHeader.setType("opa");
				headList.add(childHeader);
				this.findItemChild(child, headList, childTreeCode);
				if(i==childList.size()-1){
					childTreeCode++;
					AutoHeader sumHeader = new AutoHeader();
					sumHeader.setChecked("1");
					sumHeader.setCssstyle("");
					sumHeader.setHeadDesc(item.getName() + " (" + item.getValue() + " 分)");
					sumHeader.setId("SUMMARY"+childTreeCode);
					sumHeader.setIsleaf("Y");
					sumHeader.setIsopen("0");
					sumHeader.setLevel(String.valueOf((child.getLevel())));
					sumHeader.setName("合计 (" + item.getValue() + " 分)");
					sumHeader.setPid(child.getItemParentId());
					sumHeader.setReportColumnName(child.getItemParentId());
					sumHeader.setReportColumnType("Number");
					sumHeader.setTransanalyzz("");
					sumHeader.setSts("Y");
					sumHeader.setTreeCode(String.valueOf(childTreeCode));
					sumHeader.setType("opa");
					headList.add(sumHeader);
				}
				
			}
		}
	}
}