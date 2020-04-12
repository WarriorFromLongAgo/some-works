package com.orhon.smartcampus.modules.core.lib.json;
import java.util.ArrayList;
import java.util.List;
import	java.util.jar.JarFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class CommandRAWProcessor {

    private String orgKey;

    public CommandRAWProcessor(String originalKey){
        this.orgKey = originalKey;
    }


    public JSONObject process(JSONObject oldOne) {
        JSONObject ret = new JSONObject();
        List<Object> retJsonArray = new ArrayList<Object>();
        List<Object> jsonArray = oldOne.getJSONArray("data");
        jsonArray.forEach(item -> {
            JSONObject itm = (JSONObject) item;
            String rawStr = itm.getString(this.orgKey);

            Object object = JSON.parse(rawStr);
            if (object instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) object;
                itm.remove(this.orgKey);
                itm.put(this.orgKey , jsonObject);
            }
            else if (object instanceof JSONArray) {
                JSONArray jsonArr = (JSONArray) object;
                itm.remove(this.orgKey);
                itm.put(this.orgKey , jsonArr);
            }

            retJsonArray.add(itm);
        });
        ret.put("data" , retJsonArray);
        return ret;
    }
}
