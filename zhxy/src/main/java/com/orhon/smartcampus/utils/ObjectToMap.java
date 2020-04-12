package com.orhon.smartcampus.utils;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ObjectToMap {

	public static HashMap<String, Object> to(Object obj){
		HashMap hashMap = JSONObject.parseObject(JSON.toJSONString(obj),HashMap.class);
        return hashMap;
    }
}
