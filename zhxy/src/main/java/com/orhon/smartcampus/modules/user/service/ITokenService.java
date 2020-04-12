package com.orhon.smartcampus.modules.user.service;

import com.orhon.smartcampus.modules.user.entity.Token;
import com.orhon.smartcampus.framework.service.BaseService;
import com.orhon.smartcampus.utils.R;

/**
 * <p>
 *  Token服务类
 * </p>
 *
 * @author mergen
 */
public interface ITokenService extends BaseService<Token> {

    //从一个token值查询
    Token queryByToken(String accessToken);

    //从userid查询
    Token queryByUserId(Long userId);

    //从username查询
    Token queryByUserName(String username);

    //生成token机制
    R createToken(long userId);

    //生成jwt token
    R createJwtToken(String userName , Long userId);

    //Integer queryTokenCount(long User)

    //退出登录
    void logout(long userId);

}
