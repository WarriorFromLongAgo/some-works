package com.orhonit.modules.sys.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;

import com.orhonit.modules.sys.dao.UserMemorandumDao;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.entity.UserMemorandumEntity;
import com.orhonit.modules.sys.service.UserMemorandumService;


@Service("userMemorandumService")
public class UserMemorandumServiceImpl extends ServiceImpl<UserMemorandumDao, UserMemorandumEntity> implements UserMemorandumService {

    @Override
    public PageUtils queryPage(Map<String, Object> params,long userId) {
        Page<UserMemorandumEntity> page = this.selectPage(
                new Query<UserMemorandumEntity>(params).getPage(),
                new EntityWrapper<UserMemorandumEntity>().where("user_id={0}", userId).orderBy("memorandum_id")
        );
        page.setTotal(this.selectCount(new EntityWrapper<UserMemorandumEntity>().where("user_id={0}", userId).orderBy("memorandum_id")));
        return new PageUtils(page);
    }

}
