package com.orhonit.modules.generator.service.impl;

import com.orhonit.modules.generator.entity.ZgMeetInformEntity;
import com.orhonit.modules.generator.entity.ZgMeetPeopleEntity;
import com.orhonit.modules.generator.service.ZgMeetInformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.ZgMeetRecordDao;
import com.orhonit.modules.generator.entity.ZgMeetRecordEntity;
import com.orhonit.modules.generator.service.ZgMeetRecordService;

import java.util.*;


@Service("zgMeetRecordService")
public class ZgMeetRecordServiceImpl extends ServiceImpl<ZgMeetRecordDao, ZgMeetRecordEntity> implements ZgMeetRecordService {

	@Autowired
	private ZgMeetRecordDao zgMeetRecordDao;

	@Autowired
	private ZgMeetInformService zgMeetInformService;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
		int currPage = 1;
		int limit = 10;

		if(params.get("page") != null){
			currPage = Integer.parseInt((String)params.get("page"));
		}
		if(params.get("limit") != null){
			limit = Integer.parseInt((String)params.get("limit"));
		}
		int page = (currPage-1)*limit;
		List<ZgMeetRecordEntity> listCount = zgMeetRecordDao.findReListCount(params);
		params.put("page", page);
		params.put("limit", limit);
		Page<ZgMeetRecordEntity> infPage = new Page<>(currPage,limit);
		List<ZgMeetRecordEntity> list = zgMeetRecordDao.findReList(params);
		infPage.setRecords(list);
		infPage.setTotal(listCount.size());
		return new PageUtils(infPage);
    }

	@Override
	public void save(ZgMeetRecordEntity zgMeetRecord) {
		ZgMeetInformEntity zgMeetInformEntity = zgMeetInformService.selectById(zgMeetRecord.getMeetId());
		zgMeetRecord.setCreateTime(new Date());
		zgMeetRecord.setMeetTitle(zgMeetInformEntity.getMeetTitle());
		zgMeetRecord.setId(UUID.randomUUID().toString().replace("-", ""));
		zgMeetRecordDao.save(zgMeetRecord);
	}

	@Override
	public Map<String, List<ZgMeetRecordEntity>> findInfo(Map<String, Object> params) {
    	String meetId = params.get("meetId").toString();
    	List<ZgMeetRecordEntity> list = new ArrayList<>();
    	List<ZgMeetRecordEntity> oldList = zgMeetRecordDao.findReListCount(params);
		for (ZgMeetRecordEntity zgMeetRecordEntity : oldList) {
			if (meetId.equals(zgMeetRecordEntity.getMeetId())){
				list.add(zgMeetRecordEntity);
			}
		}
		Map<String,List<ZgMeetRecordEntity>> map = new HashMap<>();
		map.put("results",list);
		return map;
	}

}