package com.orhonit.modules.generator.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.modules.generator.app.dao.AppHelpMeDianZanDao;
import com.orhonit.modules.generator.app.entity.AppHelpMeDianZanEntity;
import com.orhonit.modules.generator.app.service.AppHelpMeDianZanService;

/**
 * 
 * Title: 帮帮我  点赞
 * @Description:
 * @author YaoSC
 * @date 2019年6月19日
 */
@Service("AppHelpMeDianZanService")
public class AppHelpMeDianZanServiceImpl extends ServiceImpl
                          <AppHelpMeDianZanDao,AppHelpMeDianZanEntity>implements AppHelpMeDianZanService{
	
	
	@Autowired
	AppHelpMeDianZanDao appHelpMeDianZanDao;

	@Override
	public String selectDianZan(AppHelpMeDianZanEntity entity) {
		 String msg="成功点赞!";
		 String error="已点赞过!";
		int selectCount=appHelpMeDianZanDao.selectCount(new EntityWrapper<AppHelpMeDianZanEntity>()
				.eq("help_id", entity.getHelpId())
				.eq("help_type", entity.getHelpType())
				.eq("help_user_id_zan", entity.getHelpUserIdZan()));
		if(selectCount<=0) {
			appHelpMeDianZanDao.insert(entity);
			return msg;
		}else {
			return error;
		}
		
	}

	@Override
	public String quxiaodianzan(AppHelpMeDianZanEntity entity) {
		String msg="已取消点赞!";
		String error="未点赞,取消不了点赞!";
		int selectCount=appHelpMeDianZanDao.selectCount(new EntityWrapper<AppHelpMeDianZanEntity>()
				.eq("help_id", entity.getHelpId())
				.eq("help_type", entity.getHelpType())
				.eq("help_user_id_zan", entity.getHelpUserIdZan()));
		if(selectCount>0) {
			appHelpMeDianZanDao.delete(new EntityWrapper<AppHelpMeDianZanEntity>()
					.eq("help_id", entity.getHelpId())
					.eq("help_type", entity.getHelpType())
					.eq("help_user_id_zan", entity.getHelpUserIdZan()));
			return msg;
		}else {
			return error;
		}
	}

}
