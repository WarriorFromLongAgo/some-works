package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.ZgDefaultScoreDeptDao;
import com.orhonit.modules.generator.entity.ZgDefaultScoreDeptEntity;
import com.orhonit.modules.generator.service.ZgDefaultScoreDeptService;
import com.orhonit.modules.sys.entity.TaDepartmentMemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service("zgDefaultScoreDeptService")
public class ZgDefaultScoreDeptServiceImpl extends ServiceImpl<ZgDefaultScoreDeptDao, ZgDefaultScoreDeptEntity> implements ZgDefaultScoreDeptService {

    @Autowired
    private ZgDefaultScoreDeptDao zgDefaultScoreDeptDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        if (params.get("lowerId") != null){
            Integer lowerId = Integer.parseInt(params.get("lowerId").toString());
            Page<ZgDefaultScoreDeptEntity> page = this.selectPage(
                    new Query<ZgDefaultScoreDeptEntity>(params).getPage(),
                    new EntityWrapper<ZgDefaultScoreDeptEntity>().where("lower_id ="+lowerId)
            );
            page.setTotal(this.selectCount(new EntityWrapper<ZgDefaultScoreDeptEntity>().where("lower_id ="+lowerId)));
            return new PageUtils(page);
        }else {
            Page<ZgDefaultScoreDeptEntity> page = this.selectPage(
                    new Query<ZgDefaultScoreDeptEntity>(params).getPage(),
                    new EntityWrapper<ZgDefaultScoreDeptEntity>()
            );
            page.setTotal(this.selectCount(new EntityWrapper<ZgDefaultScoreDeptEntity>()));
            return new PageUtils(page);
        }

    }

    @Override
    public void save(ZgDefaultScoreDeptEntity zgDefaultScoreDept) {
        List<ZgDefaultScoreDeptEntity> list = zgDefaultScoreDeptDao.findDefaultDeptList(zgDefaultScoreDept.getLowerId());
        if (list != null && list.size() > 0){
            TaDepartmentMemberEntity taDepartmentMemberEntity = zgDefaultScoreDeptDao.findLowerName(zgDefaultScoreDept.getLowerId());
            zgDefaultScoreDept.setId(list.get(0).getId());
            zgDefaultScoreDept.setLowerName(taDepartmentMemberEntity.getLowerName());
            zgDefaultScoreDept.setUpdateTime(new Date());
            zgDefaultScoreDeptDao.updateById(zgDefaultScoreDept);
        }else {
            zgDefaultScoreDept.setId(UUID.randomUUID().toString().replace("-",""));
            zgDefaultScoreDept.setUpdateTime(new Date());
            zgDefaultScoreDeptDao.save(zgDefaultScoreDept);
        }

    }

    @Override
    public TaDepartmentMemberEntity findLowerName(Integer lowerId) {
        return zgDefaultScoreDeptDao.findLowerName(lowerId);
    }

}