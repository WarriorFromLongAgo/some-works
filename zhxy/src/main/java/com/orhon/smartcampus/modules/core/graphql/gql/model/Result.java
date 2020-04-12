package com.orhon.smartcampus.modules.core.graphql.gql.model;

import java.util.HashMap;
import java.util.Map;

import com.orhon.smartcampus.utils.R;

import lombok.Data;

@Data
public class Result extends HashMap<String, Object>  {

private int code;

private String msg;

private Object data;

private Object pagenation;

private static final long serialVersionUID = 1L;

public Result() {
    put("code", 200);
    put("msg", "success");
}

public static Result error() {
	Result r = new Result();
    r.put("code", 500);
    r.put("msg", "错误！");
    return r;
}


public static Result error(String msg) {
    return error(500, msg);
}

public static Result error(int code, String msg) {
	Result r = new Result();
    r.put("code", code);
    r.put("msg", msg);
    return r;
}

public static Result ok(String msg) {
	Result r = new Result();
    r.put("msg", msg);
    return r;
}

public static Result ok(Map<String, Object> map) {
	Result r = new Result();
    r.putAll(map);
    return r;
}

public static Result ok() {
    return new Result();
}

public static Result timeOut() {
	Result r = new Result();
    r.put("code", 10000);
    r.put("msg", "修改超时");
    return r;
}

public static Result parameterIsNul() {
	Result r = new Result();
    r.put("code", 10001);
    r.put("msg", "参数为空");
    return r;
}


public static Result Repeat() {
	Result r = new Result();
    r.put("msg", "重复提交");
    return r;
}

public Result put(String key, Object value) {
	 if(value==null) {
     	Object object = new Object();
     	super.put(key, object);
     	 return this;
     }
	super.put(key, value);
    return this;
}

}
