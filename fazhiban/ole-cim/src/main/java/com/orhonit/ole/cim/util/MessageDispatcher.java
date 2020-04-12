/**
 * Copyright 2013-2023 Xia Jun(3979434@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ***************************************************************************************
 *                                                                                     *
 *                        Website : http://www.farsunset.com                           *
 *                                                                                     *
 ***************************************************************************************
 */
package com.orhonit.ole.cim.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.farsunset.cim.sdk.server.model.Message;

public class MessageDispatcher {
	private static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5, 20, TimeUnit.SECONDS, queue);;
	final static String sendUrl = "http://%1$s:8087/cim/message/push/send";

	public static void execute(final Message msg, final String ip) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					httpPost(String.format(sendUrl, ip), msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static String httpPost(String url, Message msg) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-type","application/json; charset=utf-8");
		httpPost.setHeader("Accept", "application/json");
		JSONObject postData = new JSONObject();  
		postData.put("mid", msg.getMid());
		postData.put("extra", msg.getExtra());
		postData.put("action", msg.getAction());
		postData.put("title", msg.getTitle());
		postData.put("sender", msg.getSender());
		postData.put("receiver", msg.getReceiver());
		postData.put("format", msg.getFormat());
		postData.put("timestamp", String.valueOf(msg.getTimestamp()));
		postData.put("content", msg.getContent());
		httpPost.setEntity(new StringEntity(postData.toString(),"UTF-8"));
		CloseableHttpResponse response2 = httpclient.execute(httpPost);
		String data = null;
		try {
			System.out.println(response2.getStatusLine());
			HttpEntity entity2 = response2.getEntity();
			data = EntityUtils.toString(entity2);
		} finally {
			response2.close();
		}

		return data;
	}

}
