package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.ZgDefaultScoreDao;
import com.orhonit.modules.generator.entity.ZgDefaultScoreEntity;
import com.orhonit.modules.generator.entity.ZgPortrayalEntity;
import com.orhonit.modules.generator.service.ZgDefaultScoreService;
import com.orhonit.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service("zgDefaultScoreService")
public class ZgDefaultScoreServiceImpl extends ServiceImpl<ZgDefaultScoreDao, ZgDefaultScoreEntity> implements ZgDefaultScoreService {

    @Autowired
    private ZgDefaultScoreDao zgDefaultScoreDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        if (params.get("userId") != null){
            Long userId = Long.parseLong(params.get("userId").toString());
            Page<ZgDefaultScoreEntity> page = this.selectPage(
                    new Query<ZgDefaultScoreEntity>(params).getPage(),
                    new EntityWrapper<ZgDefaultScoreEntity>().where("user_id = "+userId)
            );
            page.setTotal(this.selectCount(new EntityWrapper<ZgDefaultScoreEntity>().where("user_id ="+userId)));
            return new PageUtils(page);
        }else {
            Page<ZgDefaultScoreEntity> page = this.selectPage(
                    new Query<ZgDefaultScoreEntity>(params).getPage(),
                    new EntityWrapper<ZgDefaultScoreEntity>()
            );
            page.setTotal(this.selectCount(new EntityWrapper<ZgDefaultScoreEntity>()));
            return new PageUtils(page);
        }

    }

    @Override
    public void save(ZgDefaultScoreEntity zgDefaultScore) {
        List<ZgDefaultScoreEntity> list = zgDefaultScoreDao.findList(zgDefaultScore.getUserId());
        if (list != null && list.size() > 0){
            SysUserEntity entity = zgDefaultScoreDao.findUserInfo(zgDefaultScore.getUserId());
            zgDefaultScore.setLowerId(entity.getLowerId());
            zgDefaultScore.setLowerName(entity.getLowerName());
            zgDefaultScore.setId(list.get(0).getId());
            zgDefaultScore.setUpdateTime(new Date());
            zgDefaultScoreDao.updateById(zgDefaultScore);
        }else {
            zgDefaultScore.setId(UUID.randomUUID().toString().replace("-",""));
            zgDefaultScore.setUpdateTime(new Date());
            zgDefaultScoreDao.save(zgDefaultScore);
        }
    }

}