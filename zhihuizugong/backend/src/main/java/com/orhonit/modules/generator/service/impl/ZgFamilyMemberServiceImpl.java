package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.ZgFamilyMemberDao;
import com.orhonit.modules.generator.entity.ZgFamilyMemberEntity;
import com.orhonit.modules.generator.service.ZgFamilyMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;



@Service("zgFamilyMemberService")
public class ZgFamilyMemberServiceImpl extends ServiceImpl<ZgFamilyMemberDao, ZgFamilyMemberEntity> implements ZgFamilyMemberService {

    @Autowired
    private ZgFamilyMemberDao zgFamilyMemberDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Long userId = Long.parseLong((String)params.get("userId"));
        Page<ZgFamilyMemberEntity> page = this.selectPage(
                new Query<ZgFamilyMemberEntity>(params).getPage(),
                new EntityWrapper<ZgFamilyMemberEntity>().where("user_id =" + userId)
        );
        page.setTotal(this.selectCount(new EntityWrapper<ZgFamilyMemberEntity>().where("user_id =" + userId)));
        return new PageUtils(page);
    }

    @Override
    public void save(ZgFamilyMemberEntity zgFamilyMember) {
        zgFamilyMember.setCreateTime(new Date());
        zgFamilyMemberDao.insert(zgFamilyMember);
    }

}