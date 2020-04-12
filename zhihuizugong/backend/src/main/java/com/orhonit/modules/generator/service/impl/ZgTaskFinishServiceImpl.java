package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.ZgTaskFinishDao;
import com.orhonit.modules.generator.entity.ZgTaskFinishEntity;
import com.orhonit.modules.generator.service.ZgTaskFinishService;
import org.springframework.stereotype.Service;
import java.util.Map;



@Service("zgTaskFinishService")
public class ZgTaskFinishServiceImpl extends ServiceImpl<ZgTaskFinishDao, ZgTaskFinishEntity> implements ZgTaskFinishService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Integer taskId = (Integer)params.get("taskId");
        String contentType = (String)params.get("contentType");
        Page<ZgTaskFinishEntity> page = this.selectPage(
                new Query<ZgTaskFinishEntity>(params).getPage(),
                new EntityWrapper<ZgTaskFinishEntity>().where("task_id = "+taskId).and("content_type = "+"'"+contentType+"'")
        );
        page.setTotal(this.selectCount(new EntityWrapper<ZgTaskFinishEntity>().where("task_id = "+taskId).and("content_type = "+"'"+contentType+"'")));
        return new PageUtils(page);
    }

}