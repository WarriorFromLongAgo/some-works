package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.PublicNewsDao;
import com.orhonit.modules.generator.entity.PublicNewsEntity;
import com.orhonit.modules.generator.service.PublicNewsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;




@Service("publicNewsService")
public class PublicNewsServiceImpl extends ServiceImpl<PublicNewsDao, PublicNewsEntity> implements PublicNewsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Integer type = null;
        if (params.get("type") != null && !"".equals(params.get("type"))){
            type = Integer.parseInt((String)params.get("type"));
            Page<PublicNewsEntity> page = this.selectPage(
                    new Query<PublicNewsEntity>(params).getPage(),
                    new EntityWrapper<PublicNewsEntity>().like(StringUtils.isNotBlank((String) params.get("title")),"title",(String) params.get("title")).and("type = "+type)
            );
            page.setTotal(this.selectCount(new EntityWrapper<PublicNewsEntity>().like(StringUtils.isNotBlank((String) params.get("title")),"title",(String) params.get("title")).and("type = "+type)));
            return new PageUtils(page);
        }else {
            Page<PublicNewsEntity> page = this.selectPage(
                    new Query<PublicNewsEntity>(params).getPage(),
                    new EntityWrapper<PublicNewsEntity>().like(StringUtils.isNotBlank((String) params.get("title")),"title",(String) params.get("title"))
            );
            page.setTotal(this.selectCount(new EntityWrapper<PublicNewsEntity>().like(StringUtils.isNotBlank((String) params.get("title")),"title",(String) params.get("title"))));
            return new PageUtils(page);
        }

    }

}