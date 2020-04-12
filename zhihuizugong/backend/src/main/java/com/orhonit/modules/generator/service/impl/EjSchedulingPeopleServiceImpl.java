package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.EjSchedulingPeopleDao;
import com.orhonit.modules.generator.dao.EjSchedulingRecordDao;
import com.orhonit.modules.generator.entity.EjSchedulingPeopleEntity;
import com.orhonit.modules.generator.service.EjSchedulingPeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("ejSchedulingPeopleService")
public class EjSchedulingPeopleServiceImpl extends ServiceImpl<EjSchedulingPeopleDao, EjSchedulingPeopleEntity> implements EjSchedulingPeopleService {


    @Autowired
    private EjSchedulingPeopleDao ejSchedulingPeopleDao;
    @Autowired
    private EjSchedulingRecordDao ejSchedulingRecordDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EjSchedulingPeopleEntity> page = this.selectPage(
                new Query<EjSchedulingPeopleEntity>(params).getPage(),
                new EntityWrapper<EjSchedulingPeopleEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void updateTaskClaim(EjSchedulingPeopleEntity ejSchedulingPeople) {
        ejSchedulingPeopleDao.updateTaskClaim(ejSchedulingPeople);
    }

    @Override
    public EjSchedulingPeopleEntity selectPeopleInfo(Integer id) {
        EjSchedulingPeopleEntity ejSchedulingPeopleEntity = ejSchedulingPeopleDao.selectById(id);
//        List<EjSchedulingRecordEntity> recordList = ejSchedulingRecordDao.selectList(new EntityWrapper<EjSchedulingRecordEntity>().and("people_id ="+ejSchedulingPeopleEntity.getId()).orderBy("create_time desc"));
//        ejSchedulingPeopleEntity.setRecordList(recordList);
        return ejSchedulingPeopleEntity;
    }

}