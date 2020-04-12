package com.orhonit.modules.app.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.orhonit.modules.generator.dao.ZgDefaultScoreDao;
import com.orhonit.modules.sys.entity.SysUserEntity;
import com.orhonit.modules.sys.entity.SysUserTokenEntity;
import com.orhonit.modules.sys.service.SysUserTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.common.utils.R;
import com.orhonit.common.validator.ValidatorUtils;
import com.orhonit.modules.app.annotation.LoginUser;
import com.orhonit.modules.app.entity.AppUserEntity;
import com.orhonit.modules.app.form.LoginForm;
import com.orhonit.modules.app.service.UserService;
import com.orhonit.modules.app.utils.JwtUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * APP登录授权
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:31
 */
@RestController
@RequestMapping("/app")
@Api("APP登录接口")
public class AppLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private ZgDefaultScoreDao zgDefaultScoreDao;

    @Autowired
    private SysUserTokenService sysUserTokenService;
    //12小时后过期
    private final static int EXPIRE = 3600 * 12;


    /**
     * 登录
     */
    @PostMapping("login")
    @ApiOperation("登录")
    public R login(@RequestBody LoginForm form,@LoginUser AppUserEntity user){
        //表单校验
        ValidatorUtils.validateEntity(form);

        //用户登录
        long userId = userService.login(form);
        SysUserEntity userInfo = zgDefaultScoreDao.findUserInfo(userId);
        //生成token
        String token = jwtUtils.generateToken(userId);

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
        //判断是否生成过token
        List<SysUserTokenEntity> tokenEntityList = sysUserTokenService.selectList(new EntityWrapper<SysUserTokenEntity>().and("user_id ="+userId).and("is_app = 1"));
        if(tokenEntityList.size() == 0){
            tokenEntity = new SysUserTokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            tokenEntity.setIsApp(1);
            //保存token
            sysUserTokenService.insert(tokenEntity);
        }else{
            tokenEntityList.get(0).setToken(token);
            tokenEntityList.get(0).setUpdateTime(now);
            tokenEntityList.get(0).setExpireTime(expireTime);
            tokenEntityList.get(0).setIsApp(1);
            //更新token
            sysUserTokenService.updateById(tokenEntityList.get(0));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());
        //map.put("info", user);
        map.put("userId", userId);
        map.put("userInfo",userInfo);
        return R.ok(map);
    }

}
