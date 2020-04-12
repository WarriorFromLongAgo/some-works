package com.orhon.smartcampus.modules.core.lib.json;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommandMVProcessor {

    private String orginalKey;
    private String desKey;
    private String desRoot;

    public CommandMVProcessor(String orginalKey , String desRoot , String desKey){

        this.orginalKey = orginalKey;
        this.desRoot = desRoot;
        this.desKey = desKey;
    }


    public JSONObject process(JSONObject oldOne){
        JSONObject ret = new JSONObject();
        List<Object> retJsonArray = new ArrayList<Object>();
        List<Object> jsonArray = oldOne.getJSONArray("data");
        jsonArray.forEach(item -> {
            JSONObject itm = (JSONObject) item;
            JSONObject mv;
            if (itm.get(this.desRoot) != null){
                mv = (JSONObject) itm.get(this.desRoot);
            }
            else{
                mv = new JSONObject();
            }
            mv.put(this.desKey , itm.get(this.orginalKey));
            itm.remove(this.orginalKey);
            itm.put(this.desRoot , mv);
            retJsonArray.add(itm);
        });
        ret.put("data" , retJsonArray);
        return ret;
    }


}
