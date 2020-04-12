package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.ZgNearlyThreeYearsDao;
import com.orhonit.modules.generator.entity.ZgNearlyThreeYearsEntity;
import com.orhonit.modules.generator.service.ZgNearlyThreeYearsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;



@Service("zgNearlyThreeYearsService")
public class ZgNearlyThreeYearsServiceImpl extends ServiceImpl<ZgNearlyThreeYearsDao, ZgNearlyThreeYearsEntity> implements ZgNearlyThreeYearsService {

    @Autowired
    private ZgNearlyThreeYearsDao zgNearlyThreeYearsDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String userNickname = (String)params.get("userNickname");
        if (params.get("particularYear") != null){
            String particularYear = (String)params.get("particularYear");
            Page<ZgNearlyThreeYearsEntity> page = this.selectPage(
                    new Query<ZgNearlyThreeYearsEntity>(params).getPage(),
                    new EntityWrapper<ZgNearlyThreeYearsEntity>().where("user_nickname = "+userNickname).and("particular_year ="+particularYear)
            );
            page.setTotal(this.selectCount(new EntityWrapper<ZgNearlyThreeYearsEntity>().where("user_nickname = "+userNickname).and("particular_year ="+particularYear)));
            return new PageUtils(page);
        }else {
            Page<ZgNearlyThreeYearsEntity> page = this.selectPage(
                    new Query<ZgNearlyThreeYearsEntity>(params).getPage(),
                    new EntityWrapper<ZgNearlyThreeYearsEntity>().where("user_nickname = "+userNickname)
            );
            page.setTotal(this.selectCount(new EntityWrapper<ZgNearlyThreeYearsEntity>().where("user_nickname = "+userNickname)));
            return new PageUtils(page);
        }
    }

    @Override
    public void save(List<ZgNearlyThreeYearsEntity> entityList) {
        for (ZgNearlyThreeYearsEntity zgNearlyThreeYearsEntity : entityList) {
            zgNearlyThreeYearsEntity.setCreateTime(new Date());
            zgNearlyThreeYearsDao.insert(zgNearlyThreeYearsEntity);
        }
    }

}