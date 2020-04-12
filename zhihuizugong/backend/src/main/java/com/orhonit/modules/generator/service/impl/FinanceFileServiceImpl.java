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

import com.orhonit.modules.generator.dao.FinanceFileDao;
import com.orhonit.modules.generator.entity.FinanceFileEntity;
import com.orhonit.modules.generator.service.FinanceFileService;


@Service("financeFileService")
public class FinanceFileServiceImpl extends ServiceImpl<FinanceFileDao, FinanceFileEntity> implements FinanceFileService {

	@Autowired
	FinanceFileDao financeFileDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<FinanceFileEntity> page = this.selectPage(
                new Query<FinanceFileEntity>(params).getPage(),
                new EntityWrapper<FinanceFileEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public List<FinanceFileEntity> wordList(String financeId) {
		return financeFileDao.wordList(financeId);
	}

	@Override
	public List<FinanceFileEntity> imageList(String financeId) {
		return financeFileDao.imageList(financeId);
	}

	@Override
	public List<FinanceFileEntity> audioList(String financeId) {
		return financeFileDao.audioList(financeId);
	}

	@Override
	public List<FinanceFileEntity> videoList(String financeId) {
		return financeFileDao.videoList(financeId);
	}

	@Override
	public List<FinanceFileEntity> allList(String financeId) {
		return financeFileDao.allList(financeId);
	}

}