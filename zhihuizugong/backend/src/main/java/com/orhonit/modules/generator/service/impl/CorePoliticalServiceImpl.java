package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.CorePoliticalDao;
import com.orhonit.modules.generator.entity.CorePoliticalEntity;
import com.orhonit.modules.generator.service.CorePoliticalService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("corePoliticalService")
public class CorePoliticalServiceImpl extends ServiceImpl<CorePoliticalDao, CorePoliticalEntity> implements CorePoliticalService {

    @Autowired
    CorePoliticalDao corePoliticalDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String title =  (String) params.get("title");
        String content =  (String) params.get("content");
        String createTime =  (String) params.get("createTime");
        String userName =  (String) params.get("userName");

        Integer politicalType = Integer.parseInt((String) params.get("politicalType"));
        Page<CorePoliticalEntity> page = this.selectPage(
                new Query<CorePoliticalEntity>(params).getPage(),
                new EntityWrapper<CorePoliticalEntity>().like(StringUtils.isNotBlank(content) , "content",content).like(StringUtils.isNotBlank(title) , "title",title).and("political_type="+politicalType).like(StringUtils.isNotBlank(userName) , "user_name",userName).and(StringUtils.isNotBlank(createTime) , "DATE_FORMAT(create_time,'%Y-%m-%d')="+"DATE_FORMAT('"+createTime+"','%Y-%m-%d')").orderBy("create_time DESC")
        );
        page.setTotal(this.selectCount(new EntityWrapper<CorePoliticalEntity>().like(StringUtils.isNotBlank(content) , "content",content).like(StringUtils.isNotBlank(title) , "title",title).and("political_type="+politicalType).like(StringUtils.isNotBlank(userName) , "user_name",userName).and(StringUtils.isNotBlank(createTime) , "DATE_FORMAT(create_time,'%Y-%m-%d')="+"DATE_FORMAT('"+createTime+"','%Y-%m-%d')")));
        return new PageUtils(page);
    }

    @Override
    public void batchDeletePolitical(Integer[] ids) {
        corePoliticalDao.batchDeletePolitical(ids);
    }
}