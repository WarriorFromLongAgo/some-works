package com.orhonit.ole.cim.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.farsunset.cim.sdk.server.model.Message;
import com.orhonit.ole.cim.push.SystemMessagePusher;
import com.orhonit.ole.cim.util.BeanUtils;

@Component
@RestController
@RequestMapping("/message/push")
public class HttpMessageController {
	
	@PostMapping("/send")
	public String send(HttpServletResponse response,HttpServletRequest request) throws Exception {
		try {
			request.setCharacterEncoding("UTF-8"); // 返回页面防止出现中文乱码
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));// post方式传递读取字符流
			String jsonStr = null;
			StringBuilder result = new StringBuilder();
			try {
				while ((jsonStr = reader.readLine()) != null) {
					result.append(jsonStr);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			reader.close();// 关闭输入流
			JSONObject jsonObject = JSONObject.parseObject(result.toString()); // 取一个json转换为对象
			System.out.println(result.toString());
			Message msg = new Message();
			msg.setMid(jsonObject.getString("mid"));
			msg.setAction(jsonObject.getString("action"));
			msg.setExtra(jsonObject.getString("extra"));
			msg.setFormat(jsonObject.getString("format"));
			msg.setTitle(jsonObject.getString("title"));
			msg.setContent(jsonObject.getString("content"));
			msg.setSender(jsonObject.getString("sender"));
			msg.setReceiver(jsonObject.getString("receiver"));
			SystemMessagePusher push = BeanUtils.getBean(SystemMessagePusher.class);
			push.push(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
}
