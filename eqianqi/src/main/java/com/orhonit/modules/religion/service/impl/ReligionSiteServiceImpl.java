package com.orhonit.modules.religion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhonit.common.utils.DateUtils;
import com.orhonit.common.utils.ShiroUtils;
import com.orhonit.modules.religion.domain.ReligionSiteDomain;
import com.orhonit.modules.religion.entity.ReligionPerson;
import com.orhonit.modules.religion.entity.ReligionPersonGroup;
import com.orhonit.modules.religion.entity.ReligionSite;
import com.orhonit.modules.religion.mapper.ReligionPersonGroupMapper;
import com.orhonit.modules.religion.mapper.ReligionSiteMapper;
import com.orhonit.modules.religion.model.ReligionSiteModel;
import com.orhonit.modules.religion.service.ReligionSiteService;

/**
 * 活动场所
 * 
 * @author cyf
 * @email cyf0477@126.com
 * @version 2018-11-12 15:50:29
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class ReligionSiteServiceImpl  extends ServiceImpl<ReligionSiteMapper, ReligionSite> implements ReligionSiteService{
	
	private Gson gson=new Gson();
	
	@Autowired
	private ReligionSiteMapper siteMapper;
	
	@Autowired
	private ReligionPersonGroupMapper groupMapper;

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void insertOrUpdateReligionSite(ReligionSiteDomain religionSiteDomain) {
		if(null  != religionSiteDomain.getId()) {
			siteMapper.deleteById(religionSiteDomain.getId());
			groupMapper.deleteByReligionSiteId(religionSiteDomain.getId());
		}
		ReligionSite religionSite= new ReligionSite();
		BeanUtils.copyProperties(religionSiteDomain, religionSite);
		religionSite.setCreateUser(ShiroUtils.getUserId());
		religionSite.setCreateTime(DateUtils.getNowTime());
		siteMapper.insertReturnId(religionSite);
		String[] primaryPerson = religionSiteDomain.getPrimaryPerson().split(",");//主要教职人员   0
		String[] goupPerson = religionSiteDomain.getGoupPerson().split(",");//民主管理小组组长    1
		String[] leadGroup = religionSiteDomain.getLeadGroup().split(",");//民主领导小组   2
		for (String person : primaryPerson) {
			groupMapper.insert(new ReligionPersonGroup(religionSite.getId(),Long.parseLong(person), "0"));
		}
		for (String goup : goupPerson) {
			groupMapper.insert(new ReligionPersonGroup(religionSite.getId(), Long.parseLong(goup), "1"));
		}
		for (String lead : leadGroup) {
			groupMapper.insert(new ReligionPersonGroup(religionSite.getId(), Long.parseLong(lead), "2"));
		}
	}

	@Override
	public Map<String, Object> selectReligionSiteAll(int page,int pageSize,String type, String gacha) {
		PageHelper.startPage(page, pageSize);
		List<ReligionSiteModel> list = siteMapper.selectReligionSiteAll(type,gacha);
		List<ReligionSiteModel> listSize = siteMapper.selectReligionSiteAll(type,gacha);
		Map<String, Object> results=new HashMap<>();
		results.put("size", listSize.size());
		results.put("results", list);
		return results;
	}

	@Override
	public void deleteReligionSiteById(Long id) {
		siteMapper.deleteById(id);
		groupMapper.deleteByReligionSiteId(id);
	}
}
