package com.orhon.smartcampus.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orhon.smartcampus.auth.JwtUtils;
import com.orhon.smartcampus.auth.TokenGenerator;
import com.orhon.smartcampus.modules.user.entity.Token;
import com.orhon.smartcampus.modules.user.mapper.TokenMapper;
import com.orhon.smartcampus.modules.user.service.ITokenService;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.utils.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class TokenServiceImpl extends BaseServiceImpl<TokenMapper, Token>implements ITokenService {

    //12小时后过期
    private final static int EXPIRE = 3600 * 12;

    //通过token拿到当前实体类 token entity
    @Override
    public Token queryByToken(String accessToken) {
        QueryWrapper<Token> query = new QueryWrapper<>();
        query.eq("token" , accessToken);
        return this.getOne(query);
    }

    @Override
    public Token queryByUserId(Long userId) {
        QueryWrapper<Token> query = new QueryWrapper<>();
        query.eq("userid" , userId);
        return this.getOne(query);
    }

    @Override
    public Token queryByUserName(String username) {
        QueryWrapper<Token> query = new QueryWrapper<>();
        query.eq("username" , username);
        return this.getOne(query);

    }

    @Override
    public R createToken(long userId) {
        String token_str = TokenGenerator.generateValue();

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        Token tokenEntity = this.queryByUserId(userId);

        if (tokenEntity == null){
            //保存生成的token
            Token t = new Token();
            t.setUserid(userId);
            t.setToken(token_str);
            t.setExpire_time(expireTime);
            t.setUpdate_time(now);
            this.save(t);
        }
        else{
            //更新token
            tokenEntity.setUpdate_time(now);
            tokenEntity.setExpire_time(expireTime);
            tokenEntity.setToken(token_str);
            this.updateById(tokenEntity);
        }

        return R.ok().put("token" , token_str).put("expire" , expireTime);
    }

    @Override
    public R createJwtToken(String userName , Long userId) {
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
        //生成jwt
        String token_str = JwtUtils.sign(userName , userId , "OrhonSmartCampusToken7893" , expireTime);


        Token tokenEntity = this.queryByUserName(userName);
        if (tokenEntity == null){
            //保存生成的token
            Token t = new Token();
            t.setUserid(userId);
            t.setToken(token_str);
            t.setExpire_time(expireTime);
            t.setUpdate_time(now);
            t.setUsername(userName);
            this.save(t);
        }
        else{
            //更新token
            tokenEntity.setUserid(userId);
            tokenEntity.setUpdate_time(now);
            tokenEntity.setExpire_time(expireTime);
            tokenEntity.setToken(token_str);
            this.updateById(tokenEntity);
        }

        return R.ok().put("token" , token_str).put("expire" , expireTime);
    }

    @Override
    public void logout(long userId) {

    }
}
