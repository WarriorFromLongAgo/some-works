package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.CoreOpinionDao;
import com.orhonit.modules.generator.dao.CoreOpinionFileDao;
import com.orhonit.modules.generator.dao.CoreOpinionReplyDao;
import com.orhonit.modules.generator.entity.CoreOpinionEntity;
import com.orhonit.modules.generator.service.CoreOpinionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("coreOpinionService")
public class CoreOpinionServiceImpl extends ServiceImpl<CoreOpinionDao, CoreOpinionEntity> implements CoreOpinionService {

    @Autowired
    CoreOpinionDao coreOpinionDao;

    @Autowired
    CoreOpinionFileDao coreOpinionFileDao;

    @Autowired
    CoreOpinionReplyDao coreOpinionReplyDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String opinion = (String) params.get("opinion");
        String creatTime = (String) params.get("creatTime");
        Integer userId = Integer.parseInt((String) params.get("userId"));
        Page<CoreOpinionEntity> page = this.selectPage(
                new Query<CoreOpinionEntity>(params).getPage(),
                new EntityWrapper<CoreOpinionEntity>().like(StringUtils.isNotBlank(opinion), "opinion", opinion).and(StringUtils.isNotBlank(creatTime), "DATE_FORMAT(creat_time,'%Y-%m-%d')=" + "'" + creatTime + "'").and("user_id="+userId).or("add_id="+userId).orderBy("creat_time DESC")
        );
        page.setTotal(this.selectCount(new EntityWrapper<CoreOpinionEntity>().like(StringUtils.isNotBlank(opinion), "opinion", opinion).and(StringUtils.isNotBlank(creatTime), "DATE_FORMAT(creat_time,'%Y-%m-%d')=" + "'" + creatTime + "'").and("user_id="+userId).or("add_id="+userId)));
        return new PageUtils(page);
    }

    @Override
    public void insertOpinion(CoreOpinionEntity coreOpinion) {
        String opinionId = coreOpinion.getOpinionId();
        coreOpinion.setOpinionId(opinionId);
        coreOpinionDao.insertOpinion(coreOpinion);
    }

    @Override
    public void deleteByOpinionId(String opinionId) {
        coreOpinionDao.deleteByOpinionId(opinionId);
        coreOpinionFileDao.removeById(opinionId);
        coreOpinionReplyDao.removeById(opinionId);
    }
}