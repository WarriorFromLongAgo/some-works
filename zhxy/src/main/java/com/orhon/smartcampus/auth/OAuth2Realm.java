package com.orhon.smartcampus.auth;

import com.orhon.smartcampus.modules.user.entity.Token;
import com.orhon.smartcampus.modules.user.entity.Users;
import com.orhon.smartcampus.modules.user.service.ITokenService;
import com.orhon.smartcampus.modules.user.service.IUsersService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Autowired
    public ITokenService tokenService;

    @Autowired
    public IUsersService userService;


    /**
     * 让shiro框架的Realm支持自定义Realm
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }



    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
/*
        Users user = (Users) principals.getPrimaryPrincipal();
        Long userId = user.getId();

        //模拟权限列表获取
        Set<String> permissionList = new HashSet<>();
        permissionList.add("test");
        permissionList.add("test2");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permissionList);

        return info;
        */

return null;
    }


    /**
     * 鉴权
     * 1、获取当前token
     * 2、查阅token是否有效
     * 3、查询用户信息
     * 4、返回鉴权信息
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {


        String accessToken = (String) token.getPrincipal();

        String username = JwtUtils.getUsername(accessToken);

        Long userId = JwtUtils.getUserId(accessToken);

        if (username == null || userId == null){
            throw new AuthenticationException("Invalid Token");
        }

        Users user = userService.getById(userId);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(accessToken , accessToken , "aaa");

        return info;

    }

}
