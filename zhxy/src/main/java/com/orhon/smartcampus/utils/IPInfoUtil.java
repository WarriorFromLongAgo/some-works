package com.orhon.smartcampus.utils;


import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @author bao
 */
public class IPInfoUtil {

    private static final Logger log = LoggerFactory.getLogger(IPInfoUtil.class);
    /**
     * Mob全国天气预报接口
     */
    private final static String GET_WEATHER="http://apicloud.mob.com/v1/weather/ip?key=270c4d225bcf0&ip=";

    

    /**
     * 获取IP返回地理天气信息
     * @param ip ip地址
     * @return
     */
    public static String getIpInfo(String ip){
        if(null != ip){
            String url = GET_WEATHER + ip;
            String result=HttpUtil.sendGet(url);
            return result;
        }
        return null;
    }

   
   
}


