package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.ZgDefaultScoreDao;
import com.orhonit.modules.generator.dao.ZgThirteenMaxDao;
import com.orhonit.modules.generator.entity.ZgThirteenMaxEntity;
import com.orhonit.modules.generator.service.ZgThirteenMaxService;
import com.orhonit.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service("zgThirteenMaxService")
public class ZgThirteenMaxServiceImpl extends ServiceImpl<ZgThirteenMaxDao, ZgThirteenMaxEntity> implements ZgThirteenMaxService {

    @Autowired
    private ZgThirteenMaxDao zgThirteenMaxDao;
    @Autowired
    private ZgDefaultScoreDao zgDefaultScoreDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ZgThirteenMaxEntity> page = this.selectPage(
                new Query<ZgThirteenMaxEntity>(params).getPage(),
                new EntityWrapper<ZgThirteenMaxEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void save(ZgThirteenMaxEntity zgThirteenMax) {
        List<ZgThirteenMaxEntity> list = zgThirteenMaxDao.findThMaxList();
        SysUserEntity userInfo = zgDefaultScoreDao.findUserInfo(zgThirteenMax.getUserId());
        if (list != null && list.size() > 0){
            zgThirteenMax.setId(list.get(0).getId());
            zgThirteenMax.setUserName(userInfo.getUserTrueName());
            zgThirteenMax.setUpdateTime(new Date());
            zgThirteenMaxDao.updateById(zgThirteenMax);
        }else {
            zgThirteenMax.setId(UUID.randomUUID().toString().replace("-",""));
            zgThirteenMax.setUserName(userInfo.getUserTrueName());
            zgThirteenMax.setUpdateTime(new Date());
            zgThirteenMaxDao.save(zgThirteenMax);
        }
    }

}