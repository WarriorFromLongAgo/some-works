package com.orhon.smartcampus.auth;

import com.google.gson.Gson;
import com.orhon.smartcampus.modules.user.entity.Token;
import com.orhon.smartcampus.modules.user.service.ITokenService;
import com.orhon.smartcampus.utils.HttpContextUtils;
import com.orhon.smartcampus.utils.R;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuth2Filter extends AuthenticatingFilter{


    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String token = httpRequest.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter("token");
        }

        return token;
    }


    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);

        if(StringUtils.isBlank(token)){
            return null;
        }

        return new OAuth2Token(token);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());

        //检查token是否传递过来
        if (token == null){
            String json = new Gson().toJson(R.error(401, "token不能为空"));
            httpResponse.getWriter().print(json);
            return false;
        }

        //检查是否是空字符串
        if(StringUtils.isBlank(token)){
            String json = new Gson().toJson(R.error(401, "token不能为空"));
            httpResponse.getWriter().print(json);
            return false;
        }

        //检查token是否合法
        Long userid = JwtUtils.getUserId(token);
        String userName = JwtUtils.getUsername(token);
        if (userid == null || userName  == null){
            String json = new Gson().toJson(R.error(401, "token不合法！"));
            httpResponse.getWriter().print(json);
            return false;
        }

        //检查token是否是过期
        Boolean verf = JwtUtils.verify(token , userName , "OrhonSmartCampusToken7893");
        if (!verf){
            String json = new Gson().toJson(R.error(401, "token已经过期！"));
            httpResponse.getWriter().print(json);
            return false;
        }



        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            R r = R.error(401, throwable.getMessage());

            String json = new Gson().toJson(r);
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {

        }

        return false;
    }
}
