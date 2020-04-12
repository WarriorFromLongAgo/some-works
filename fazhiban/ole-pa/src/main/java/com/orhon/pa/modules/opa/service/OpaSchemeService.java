/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhon.pa.common.persistence.BaseEntity;
import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.common.service.CrudService;
import com.orhon.pa.common.utils.IdGen;
import com.orhon.pa.common.utils.Status;
import com.orhon.pa.modules.opa.dao.OpaItemDao;
import com.orhon.pa.modules.opa.dao.OpaSchemeDao;
import com.orhon.pa.modules.opa.dao.OpaSchemeItemDao;
import com.orhon.pa.modules.opa.entity.OpaItem;
import com.orhon.pa.modules.opa.entity.OpaScheme;
import com.orhon.pa.modules.opa.entity.OpaSchemeItem;
import com.orhon.pa.modules.sys.entity.User;
import com.orhon.pa.modules.sys.utils.DictUtils;
import com.orhon.pa.modules.sys.utils.UserUtils;

/**
 * 考核方案管理模块Service
 * @author Shawn
 * @version 2017-04-18
 */
@Service
@Transactional(readOnly = true)
public class OpaSchemeService extends CrudService<OpaSchemeDao, OpaScheme> {

	@Autowired
	private OpaItemDao opaItemDao;
	@Autowired
	private OpaSchemeDao opaSchemeDao;
	@Autowired
	private OpaSchemeItemDao opaSchemeItemDao;
	
	public OpaScheme get(String id) {
		OpaScheme opaScheme = super.get(id);
		return opaScheme;
	}
	
	public List<OpaScheme> findList(OpaScheme opaScheme) {
		return super.findList(opaScheme);
	}
	
	public List<Status> findListByStatus(Map<String,Object> param) {
		return opaSchemeDao.findListByStatus(param);
	}
	
	public Page<OpaScheme> findPage(Page<OpaScheme> page, OpaScheme opaScheme) {
		return super.findPage(page, opaScheme);
	}
	
	@Transactional(readOnly = false)
	public void save(OpaScheme opaScheme) {
		super.save(opaScheme);
	}
	
	@Transactional(readOnly = false)
	public void delete(OpaScheme opaScheme) {
		super.delete(opaScheme);
	}
	
	@Transactional(readOnly = false)
	public void auditPass(OpaScheme opaScheme) {
		String rootId = opaScheme.getItemParent().getId();
		List<OpaItem> opaItemList = opaItemDao.findTreeByIdLike(opaScheme.getItemParent());
		String rootType = new String();
		User user = UserUtils.getUser();
		int sort = 0;
		OpaSchemeItem opaBounsItem = new OpaSchemeItem();
		for(OpaItem opaItem : opaItemList){
			OpaSchemeItem opaSchemeItem = new OpaSchemeItem();
			opaSchemeItem.setId(IdGen.uuid());
			opaSchemeItem.setSchemeId(opaScheme.getId());
			opaSchemeItem.setName(opaItem.getName());
			opaSchemeItem.setItemId(opaItem.getId());
			rootType = opaItem.getType();
			if(opaItem.getId().equals(rootId)){
				opaSchemeItem.setItemParentId("0");
				opaSchemeItem.setItemParentIds("0,");
				rootType = opaItem.getType();
				DictUtils.getDictValue("共性指标", "opa_item_type", "");
			}else{
				opaSchemeItem.setItemParentId(opaItem.getParentId());
				String parentIds = opaItem.getParentIds();
				opaSchemeItem.setItemParentIds("0," + parentIds.substring(parentIds.indexOf(rootId)));
			}
			
			opaSchemeItem.setCode(opaItem.getCode());
			//opaSchemeItem.setSchemeId(opaItem.getParentId());
			opaSchemeItem.setSort(opaItem.getSort());
			opaSchemeItem.setLevel(opaItem.getLevel()-opaScheme.getLevel());
			
//			opaSchemeItem.setContent("");
			opaSchemeItem.setMethod(DictUtils.getDictValue("机器汇总", "opa_item_method", ""));
			opaSchemeItem.setIfNum(DictUtils.getDictValue("非数值", "opa_item_num_type", ""));
//			opaSchemeItem.setCount(count);
//			opaSchemeItem.setValue(0D);
			opaSchemeItem.setType(rootType);
			opaSchemeItem.setDateFrom(opaItem.getDateFrom());
			opaSchemeItem.setDateTo(opaItem.getDateTo());
			opaSchemeItem.setRemarks(opaItem.getRemarks());
			opaSchemeItem.setCreateBy(user);
			opaSchemeItem.setUpdateBy(user);
			opaSchemeItem.setCreateDate(new Date());
			opaSchemeItem.setUpdateDate(opaSchemeItem.getCreateDate());
			opaSchemeItem.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
//			opaSchemeItem.setAuditorId("");
			opaSchemeItem.setStatus(DictUtils.getDictValue("待分配", "opa_schemeItem_status", ""));
			opaSchemeItemDao.insert(opaSchemeItem);
			
				
			/* add by lxy start*/
			if(opaItem.getParentId().equals(rootId)){
				if(opaItem.getSort()>=sort){
					sort = opaItem.getSort()+30;
					opaBounsItem = opaSchemeItem;
					opaBounsItem.setSort(sort);
				}
			}
			/* add by lxy end*/
		}
		/* add by lxy start*/
		opaBounsItem.setId(IdGen.uuid());
		//opaBounsItem.setSchemeId(rootId);
		opaBounsItem.setName("加减分项");
		opaBounsItem.setItemId(IdGen.uuid());
		opaBounsItem.setCode("BONUS-PARENT");
		opaBounsItem.setType(DictUtils.getDictValue("个性指标", "opa_item_type", ""));
		//opaBounsItem.setType(DictUtils.getDictValue("共性指标", "opa_item_type", ""));
		opaBounsItem.setMethod(DictUtils.getDictValue("机器汇总", "opa_item_method", ""));
		opaBounsItem.setIfNum(DictUtils.getDictValue("非数值", "opa_item_num_type", ""));
		//opaSchemeItemDao.insert(opaBounsItem);
		opaSchemeItemDao.update(opaBounsItem);
		
		/* add by lxy end*/
		
		opaSchemeDao.update(opaScheme);
		
	}
	
	@Transactional(readOnly = false)
	public void auditPass1(OpaScheme opaScheme) {
		String rootId = opaScheme.getItemParent().getId();
		List<OpaItem> opaItemList = opaItemDao.findTreeByIdLike(opaScheme.getItemParent());
		String rootType = new String();
		User user = UserUtils.getUser();
		int sort = 0;
		OpaSchemeItem opaBounsItem = new OpaSchemeItem();
		for(OpaItem opaItem : opaItemList){
			OpaSchemeItem opaSchemeItem = new OpaSchemeItem();
			opaSchemeItem.setId(IdGen.uuid());
			opaSchemeItem.setSchemeId(opaScheme.getId());
			opaSchemeItem.setName(opaItem.getName());
			opaSchemeItem.setItemId(opaItem.getId());
			rootType = opaItem.getType();
			if(opaItem.getId().equals(rootId)){
				opaSchemeItem.setItemParentId("0");
				opaSchemeItem.setItemParentIds("0,");
				rootType = opaItem.getType();
				DictUtils.getDictValue("共性指标", "opa_item_type", "");
			}else{
				opaSchemeItem.setItemParentId(opaItem.getParentId());
				String parentIds = opaItem.getParentIds();
				opaSchemeItem.setItemParentIds("0," + parentIds.substring(parentIds.indexOf(rootId)));
			}
			
			opaSchemeItem.setCode(opaItem.getCode());
			opaSchemeItem.setSort(opaItem.getSort());
			opaSchemeItem.setLevel(opaItem.getLevel()-opaScheme.getLevel());
			
//			opaSchemeItem.setContent("");
			opaSchemeItem.setMethod(DictUtils.getDictValue("机器汇总", "opa_item_method", ""));
			opaSchemeItem.setIfNum(DictUtils.getDictValue("非数值", "opa_item_num_type", ""));
//			opaSchemeItem.setCount(count);
//			opaSchemeItem.setValue(0D);
			opaSchemeItem.setType(rootType);
			opaSchemeItem.setDateFrom(opaItem.getDateFrom());
			opaSchemeItem.setDateTo(opaItem.getDateTo());
			opaSchemeItem.setRemarks(opaItem.getRemarks());
			opaSchemeItem.setCreateBy(user);
			opaSchemeItem.setUpdateBy(user);
			opaSchemeItem.setCreateDate(new Date());
			opaSchemeItem.setUpdateDate(opaSchemeItem.getCreateDate());
			opaSchemeItem.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
//			opaSchemeItem.setAuditorId("");
			opaSchemeItem.setStatus(DictUtils.getDictValue("待分配", "opa_schemeItem_status", ""));
			opaSchemeItemDao.update(opaSchemeItem);
			
				
			/* add by lxy start*/
			if(opaItem.getParentId().equals(rootId)){
				if(opaItem.getSort()>=sort){
					sort = opaItem.getSort()+30;
					opaBounsItem = opaSchemeItem;
					opaBounsItem.setSort(sort);
				}
			}
			/* add by lxy end*/
		}
		/* add by lxy start*/
		opaBounsItem.setId(IdGen.uuid());
		opaBounsItem.setName("加减分项");
		opaBounsItem.setItemId(IdGen.uuid());
		opaBounsItem.setCode("BONUS-PARENT");
		//opaBounsItem.setType(DictUtils.getDictValue("共性指标", "opa_item_type", ""));
		opaBounsItem.setType(DictUtils.getDictValue("个性指标", "opa_item_type", ""));
		opaBounsItem.setMethod(DictUtils.getDictValue("机器汇总", "opa_item_method", ""));
		opaBounsItem.setIfNum(DictUtils.getDictValue("非数值", "opa_item_num_type", ""));
		//opaSchemeItemDao.insert(opaBounsItem);
		opaSchemeItemDao.update(opaBounsItem);
		
		/* add by lxy end*/
		
		opaSchemeDao.update(opaScheme);
		
	}
	
	
}