package com.orhon.smartcampus.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.modules.user.entity.Users;
import com.orhon.smartcampus.modules.user.mapper.UsersMapper;
import com.orhon.smartcampus.modules.user.service.IUsersService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mergen
 */
@Service
public class UsersServiceImpl extends BaseServiceImpl<UsersMapper, Users> implements IUsersService {

    @Override
    public Users queryUser(Integer userId) {
        QueryWrapper<Users> query = new QueryWrapper<>();
        query.eq("id", userId);
        return this.getOne(query);
    }

    @Override
    public Users queryByUserName(String userName) {
        QueryWrapper<Users> query = new QueryWrapper<>();
        query.eq("username", userName);
        return this.getOne(query);
    }

    @Override
    public HashMap getByID(Long id) {
        return this.baseMapper.getByID(id);
    }

    /**
     * 通过身份证获取用户信息
     *
     * @param maps
     * @return
     */
    @Override
    public Users userInfo(HashMap<String, Object> maps) {
        String idcard = (String) maps.get("idcard");
        QueryWrapper<Users> query = new QueryWrapper<>();
        query.eq("idcard", idcard);
        return this.getOne(query);
    }
}
