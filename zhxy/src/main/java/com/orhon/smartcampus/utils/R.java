package com.orhon.smartcampus.utils;


import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author bao
 * @email
 * @date 2019年06月26日 下午9:59:27
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 200);
        put("msg", "success");
    }

    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }


    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public static R timeOut() {
        R r = new R();
        r.put("code", 10000);
        r.put("msg", "修改超时");
        return r;
    }

    public static R parameterIsNul() {
        R r = new R();
        r.put("code", 10001);
        r.put("msg", "参数为空");
        return r;
    }


    public static R Repeat() {
        R r = new R();
        r.put("msg", "重复提交");
        return r;
    }

    public R put(String key, Object value) {
    	 if(value==null) {
         	Object object = new Object();
         	super.put(key, object);
         	 return this;
         }
    	super.put(key, value);
        return this;
    }
}
