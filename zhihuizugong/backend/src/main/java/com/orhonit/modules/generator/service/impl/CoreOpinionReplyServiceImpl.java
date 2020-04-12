package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.CoreOpinionReplyDao;
import com.orhonit.modules.generator.entity.CoreOpinionReplyEntity;
import com.orhonit.modules.generator.service.CoreOpinionReplyService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("coreOpinionReplyService")
public class CoreOpinionReplyServiceImpl extends ServiceImpl<CoreOpinionReplyDao, CoreOpinionReplyEntity> implements CoreOpinionReplyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Integer userId = Integer.parseInt((String) params.get("userId"));
        Page<CoreOpinionReplyEntity> page = this.selectPage(
                new Query<CoreOpinionReplyEntity>(params).getPage(),
                new EntityWrapper<CoreOpinionReplyEntity>().and("user_id="+userId).or("add_id="+userId).orderBy("create_time DESC")
        );
        page.setTotal(this.selectCount(new EntityWrapper<CoreOpinionReplyEntity>().and("user_id="+userId).or("add_id="+userId)));
        return new PageUtils(page);
    }

}