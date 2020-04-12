package com.orhonit.modules.generator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;

import com.orhonit.modules.generator.dao.OverseeRecordsDao;
import com.orhonit.modules.generator.entity.OverseeRecordsEntity;
import com.orhonit.modules.generator.service.OverseeRecordsService;


@Service("overseeRecordsService")
public class OverseeRecordsServiceImpl extends ServiceImpl<OverseeRecordsDao, OverseeRecordsEntity> implements OverseeRecordsService {

	@Autowired
	OverseeRecordsDao overseeRecordsDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<OverseeRecordsEntity> page = this.selectPage(
                new Query<OverseeRecordsEntity>(params).getPage(),
                new EntityWrapper<OverseeRecordsEntity>()
        );

        return new PageUtils(page);
    }
//    领导批示列表
	@Override
	public List<OverseeRecordsEntity> instructionsList(String overseeId) {
		return overseeRecordsDao.instructionsList(overseeId);
	}
//	完成进度列表
	@Override
	public List<OverseeRecordsEntity> scheduleList(String overseeId) {
		return overseeRecordsDao.scheduleList(overseeId);
	}
//	领导点评列表
	@Override
	public List<OverseeRecordsEntity> reviewList(String overseeId) {
		return overseeRecordsDao.reviewList(overseeId);
	}

}