/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.orhon.pa.modules.opa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhon.pa.common.persistence.Page;
import com.orhon.pa.common.service.CrudService;
import com.orhon.pa.modules.opa.dao.OpaSchemeDao;
import com.orhon.pa.modules.opa.dao.OpaSchemeItemDao;
import com.orhon.pa.modules.opa.entity.OpaScheme;
import com.orhon.pa.modules.opa.entity.OpaSchemeItem;
import com.orhon.pa.modules.sys.utils.DictUtils;

/**
 * 方案指标模块Service
 * @author Shawn
 * @version 2017-04-21
 */
@Service
@Transactional(readOnly = true)
public class OpaSchemeItemService extends CrudService<OpaSchemeItemDao, OpaSchemeItem> {

	@Autowired
	private OpaSchemeItemDao opaSchemeItemDao;
	@Autowired
	private OpaSchemeDao opaSchemeDao;
	
	public OpaSchemeItem get(String id) {
		OpaSchemeItem opaSchemeItem = super.get(id);
		return opaSchemeItem;
	}
	
	public OpaSchemeItem getParentSchemeItem(OpaSchemeItem OpaSchemeItem) {
		OpaSchemeItem opaSchemeItem = opaSchemeItemDao.getParentSchemeItem(OpaSchemeItem);
		return opaSchemeItem;
	}
	
	public List<OpaSchemeItem> findList(OpaSchemeItem opaSchemeItem) {
		return super.findList(opaSchemeItem);
	}
	
	public Page<OpaSchemeItem> findPage(Page<OpaSchemeItem> page, OpaSchemeItem opaSchemeItem) {
		return super.findPage(page, opaSchemeItem);
	}
	
	@Transactional(readOnly = false)
	public void save(OpaSchemeItem opaSchemeItem) {
		super.save(opaSchemeItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(OpaSchemeItem opaSchemeItem) {
		super.delete(opaSchemeItem);
	}

	public List<OpaSchemeItem> findListForAssign(OpaSchemeItem opaSchemeItem) {
		return opaSchemeItemDao.findListForAssign(opaSchemeItem);
	}

	@Transactional(readOnly = false)
	public void editAssign(Map<String, Object> param) {
		opaSchemeItemDao.editAssign(param);
	}
	
	@Transactional(readOnly = false)
	public void assignAuditPass(OpaSchemeItem opaSchemeItem) {
		//获取父表yi条数据
		opaSchemeItem = opaSchemeItemDao.get(opaSchemeItem);
		opaSchemeItem.setStatus(DictUtils.getDictValue("待填报", "opa_schemeItem_status", ""));
		super.save(opaSchemeItem);
		
		//全部审核后更新状态为填报
		String schemeId = opaSchemeItem.getSchemeId();
		OpaSchemeItem param = new OpaSchemeItem();
		param.setSchemeId(schemeId);
		param.setStatus(DictUtils.getDictValue("待填报", "opa_schemeItem_status", ""));
		opaSchemeItemDao.update(param);
		//更新方案表状态
		OpaScheme opaScheme2 = opaSchemeDao.get(param.getSchemeId());
		opaScheme2.setStatus(DictUtils.getDictValue("指标填报中", "opa_scheme_status", ""));
		opaSchemeDao.update(opaScheme2);
	
		List<OpaSchemeItem> list = opaSchemeItemDao.findNotPassList(param);
		if(null == list || list.isEmpty()){
			param.setStatus(DictUtils.getDictValue("待填报", "opa_schemeItem_status", ""));
			opaSchemeItemDao.setAllToStatus(param);
			OpaScheme opaScheme = opaSchemeDao.get(schemeId);
			opaScheme.setStatus(DictUtils.getDictValue("指标填报中", "opa_scheme_status", ""));
			opaSchemeDao.update(opaScheme);
		}
	}
	
	@Transactional(readOnly = false)
	public void applyAuditPass(OpaSchemeItem opaSchemeItem) {
		opaSchemeItem = opaSchemeItemDao.get(opaSchemeItem);
		opaSchemeItem.setStatus(DictUtils.getDictValue("填报待审核", "opa_schemeItem_status", ""));
		super.save(opaSchemeItem);
		
		//全部审核后更新状态为填报待审核
		String schemeId = opaSchemeItem.getSchemeId();
		OpaSchemeItem param = new OpaSchemeItem();
		param.setSchemeId(schemeId);
		param.setStatus(DictUtils.getDictValue("填报待审核", "opa_schemeItem_status", ""));
		opaSchemeItemDao.update(param);
		
		//更新方案表状态
		/*OpaScheme opaScheme2 = opaSchemeDao.get(param.getSchemeId());
		opaScheme2.setStatus(DictUtils.getDictValue("指标填报中", "opa_scheme_status", ""));
		opaSchemeDao.update(opaScheme2);
*/		
	List<OpaSchemeItem> list = opaSchemeItemDao.findNotPassList(param);
	if(null == list || list.isEmpty()){
		param.setStatus(DictUtils.getDictValue("填报待审核", "opa_schemeItem_status", ""));
		opaSchemeItemDao.setAllToStatus(param);
		OpaScheme opaScheme = opaSchemeDao.get(schemeId);
		opaScheme.setStatus(DictUtils.getDictValue("指标填报中", "opa_scheme_status", ""));
		opaSchemeDao.update(opaScheme);
		}
		
	}
	
	@Transactional(readOnly = false)
	public void applyAuditPass2(OpaSchemeItem opaSchemeItem) {
		opaSchemeItem = opaSchemeItemDao.get(opaSchemeItem);
		opaSchemeItem.setStatus(DictUtils.getDictValue("填报待审核", "opa_schemeItem_status", ""));
		super.save(opaSchemeItem);
	
		//全部审核后更新状态为填报已审核
				String schemeId = opaSchemeItem.getSchemeId();
				OpaSchemeItem param = new OpaSchemeItem();
				param.setSchemeId(schemeId);
				//更新方案表状态
				OpaScheme opaScheme2 = opaSchemeDao.get(param.getSchemeId());
				opaScheme2.setStatus(DictUtils.getDictValue("已发布", "opa_scheme_status", ""));
				opaSchemeDao.update(opaScheme2);
				List<OpaSchemeItem> list = opaSchemeItemDao.findNotPassList(param);
				if(null == list || list.isEmpty()){
					param.setStatus(DictUtils.getDictValue("填报已审核", "opa_schemeItem_status", ""));
					opaSchemeItemDao.setAllToStatus(param);
					OpaScheme opaScheme = opaSchemeDao.get(schemeId);
					opaScheme.setStatus(DictUtils.getDictValue("已发布", "opa_scheme_status", ""));
					opaSchemeDao.update(opaScheme);
					}

	}

	

	public Double getChildSum(OpaSchemeItem parent) {
		return opaSchemeItemDao.getChildSum(parent);
	}
	
	/**
	 * @param schemeId
	 * @param itemParentId
	 * @return 返回父级评分标准及本级评分标准合计值Map
	 */
	public Map<String, Double> getValueMap(String schemeId, String itemParentId) {
		
		OpaSchemeItem parent = new OpaSchemeItem();
		parent.setSchemeId(schemeId);
		parent.setItemId(itemParentId);
		parent = opaSchemeItemDao.get(parent);
		Double childSum = opaSchemeItemDao.getChildSum(parent);
		Double sumValue = parent.getValue();
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("sumValue", sumValue==null?0:sumValue);
		map.put("childSum", childSum==null?0:childSum);
		return map;
	}
	
}