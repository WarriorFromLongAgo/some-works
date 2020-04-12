package com.orhonit.modules.generator.app.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.modules.generator.app.dao.AppOsQuestionDao;
import com.orhonit.modules.generator.app.entity.AppOsQuestionEntity;
import com.orhonit.modules.generator.app.service.AppOsQuestionService;

/***
 * APP问题表
 * @author YaoSC
 *
 */
@Service("AppOsQuestionService")
public class AppOsQuestionServiceImpl extends ServiceImpl<AppOsQuestionDao,AppOsQuestionEntity>implements AppOsQuestionService{

}
