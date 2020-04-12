package com.orhon.smartcampus.modules.user.service;

import com.orhon.smartcampus.modules.user.entity.Users;
import com.orhon.smartcampus.framework.service.BaseService;

import java.util.HashMap;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author mergen
 */
public interface IUsersService extends BaseService<Users> {

    Users queryUser(Integer userId);

    Users queryByUserName(String userName);

    HashMap getByID(Long id);

    /**
     * 通过身份证获取用户信息
     *
     * @param maps
     * @return
     */
    Users userInfo(HashMap<String, Object> maps);
}
