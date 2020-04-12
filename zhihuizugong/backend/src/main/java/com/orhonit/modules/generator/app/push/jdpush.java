package com.orhonit.modules.generator.app.push;

import java.util.Map;


import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import lombok.extern.slf4j.Slf4j;



/**
 * 极光推送 
 * @author YaoSC
 *
 */
@Slf4j
public class jdpush {
	
	//安卓
	public static void jpushAndroid(Map<String,String>param) {
		// 设置好账号的app_key和masterSecret 
		String appKey = "4f42c338bc80caf4d5b2d3b3";
		//String appKey="a2ec0ef13486c0b71750cb39";
		String masterSecret ="1def260f795239c109ac5d11";
		//String masterSecret="de3928e0522d5601cf08e398";
		//创建JPushClient(极光推送的实例)
		JPushClient jpushClient  = new JPushClient(masterSecret,appKey);
		//构造一个payload
		PushPayload payload =PushPayload.newBuilder()
				.setPlatform(Platform.android())//指定安卓端的用户
				.setAudience(Audience.registrationId("120c83f7606be515dfe"))//指定设备
				.setNotification(Notification.android(param.get("title"), param.get("msg"), param))//发送内容
				.setOptions(Options.newBuilder().setApnsProduction(false).build())//指定开发环境
				.setMessage(Message.content(param.get("msg")))//自定义信息内容
				.build();
		try {
			try {
				PushResult  result = jpushClient.sendPush(payload);
				log.info("Got result - " + result);
				 try {
					Thread.sleep(5000);
					//请求结束后，调用NettyHttpClient 中的close方法，否则进程不会退出
					jpushClient.close();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (APIRequestException e) {
				 log.error("Connection error, should retry later", e);
				//e.printStackTrace();
			}
		} catch (APIConnectionException  e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			 log.error("Should review the error, and fix the request", e);
	         //log.info("HTTP Status: " + e.getStatus());
	         //log.info("Error Code: " + ((APIRequestException) e).getErrorCode());
	        // log.info("Error Message: " + e.getErrorMessage());
		}
	}
	
	
	public static void jpushIOS (Map<String,String>param) {
	}

}
