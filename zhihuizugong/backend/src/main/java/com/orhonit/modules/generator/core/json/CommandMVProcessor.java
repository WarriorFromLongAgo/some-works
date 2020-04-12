package com.orhonit.modules.generator.core.json;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class CommandMVProcessor {
	
	private String orginalKey;
	
	private String desKey;
	
	private String desRoot;
	
	public CommandMVProcessor(String orginalKey,String desKey,String desRoot) {
		this.desKey=desKey;
		this.desRoot=desRoot;
		this.orginalKey = orginalKey;
	}
	
	public JSONObject process(JSONObject oldOne) {
		JSONObject ret = new JSONObject();
		List<Object> retJsonArray=new ArrayList<Object>();
		List<Object> jsonArray = oldOne.getJSONArray("data");
		jsonArray.forEach(item ->{
			JSONObject itm = (JSONObject) item;
			JSONObject mv;
			if(itm.get(this.desRoot) !=null) {
				mv = (JSONObject) itm.get(this.desRoot);
			}else {
				mv = new JSONObject();
			}
			mv.put(this.desKey, itm.get(this.orginalKey));
			itm.remove(this.orginalKey);
		});
		ret.put("data", retJsonArray);
		return ret;
	}

}
