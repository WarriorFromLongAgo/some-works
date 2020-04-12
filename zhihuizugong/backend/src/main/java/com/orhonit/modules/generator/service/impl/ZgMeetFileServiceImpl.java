package com.orhonit.modules.generator.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.ZgMeetFileDao;
import com.orhonit.modules.generator.dao.ZgMeetInformDao;
import com.orhonit.modules.generator.entity.ZgMeetFileEntity;
import com.orhonit.modules.generator.entity.ZgMeetInformEntity;
import com.orhonit.modules.generator.entity.ZgMeetPeopleEntity;
import com.orhonit.modules.generator.service.ZgMeetFileService;
@Service
public class ZgMeetFileServiceImpl extends ServiceImpl<ZgMeetFileDao, ZgMeetFileEntity> implements ZgMeetFileService {

	@Autowired
	private ZgMeetFileDao zgMeetFileDao;

	@Override
	public void saveFile(ZgMeetFileEntity zgMeetFileEntity) {
		zgMeetFileDao.saveFile(zgMeetFileEntity);
		
	}

	@Override
	public PageUtils findFile(Map<String, Object> params) {
		String meetId = params.get("meetId").toString();
		Integer fileType = Integer.parseInt((String)params.get("fileType"));
        Page<ZgMeetFileEntity> page = this.selectPage(
                new Query<ZgMeetFileEntity>(params).getPage(),
                new EntityWrapper<ZgMeetFileEntity>().where("meet_id = "+"'"+meetId+"'").and("file_type = "+fileType)
        );
        page.setTotal(this.selectCount(new EntityWrapper<ZgMeetFileEntity>().where("meet_id = "+"'"+meetId+"'").and("file_type = "+fileType)));
        return new PageUtils(page);
	}
}
