package com.orhonit.modules.generator.app.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.modules.generator.app.dao.AppOsErrorQuestionDao;
import com.orhonit.modules.generator.app.entity.AppOsErrorQuestionEntity;
import com.orhonit.modules.generator.app.service.AppOsErrorQuestionService;

/**
 * 错题记录
 * @author YaoSC
 *
 */
@Service("AppOsErrorQuestionService")
public class AppOsErrorQuestionServiceImpl extends ServiceImpl<AppOsErrorQuestionDao,AppOsErrorQuestionEntity> implements AppOsErrorQuestionService{

}
