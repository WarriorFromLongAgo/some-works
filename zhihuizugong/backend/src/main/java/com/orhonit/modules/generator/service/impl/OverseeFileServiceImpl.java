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

import com.orhonit.modules.generator.dao.OverseeFileDao;
import com.orhonit.modules.generator.entity.OverseeFileEntity;
import com.orhonit.modules.generator.service.OverseeFileService;


@Service("overseeFileService")
public class OverseeFileServiceImpl extends ServiceImpl<OverseeFileDao, OverseeFileEntity> implements OverseeFileService {

	@Autowired
	OverseeFileDao overseeFileDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<OverseeFileEntity> page = this.selectPage(
                new Query<OverseeFileEntity>(params).getPage(),
                new EntityWrapper<OverseeFileEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public List<OverseeFileEntity> wordList(String overseeId) {
		return overseeFileDao.wordList(overseeId);
	}

	@Override
	public List<OverseeFileEntity> imageList(String overseeId) {
		return overseeFileDao.imageList(overseeId);
	}

	@Override
	public List<OverseeFileEntity> audioList(String overseeId) {
		return overseeFileDao.audioList(overseeId);
	}

	@Override
	public List<OverseeFileEntity> videoList(String overseeId) {
		return overseeFileDao.videoList(overseeId);
	}

}