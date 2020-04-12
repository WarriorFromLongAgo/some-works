package com.orhon.smartcampus.modules.core.advice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonContainer;
import com.alibaba.fastjson.support.spring.FastJsonViewResponseBodyAdvice;
import com.orhon.smartcampus.modules.core.annotation.JsonForamtCmd;
import com.orhon.smartcampus.modules.core.annotation.JsonFormat;
import com.orhon.smartcampus.modules.core.lib.json.CommandMVProcessor;
import com.orhon.smartcampus.modules.core.lib.json.CommandRAWProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class JsonFormatterAdvice extends FastJsonViewResponseBodyAdvice {

    @Override
    public FastJsonContainer beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        //如果有@JsonFormatter注解...
        if (returnType.getMethod().isAnnotationPresent(JsonFormat.class)) {

            Annotation[] annotations = returnType.getMethodAnnotations();

            //获取当前方法上的JsonFormat注解。。
            Annotation anoJsonFormat = returnType.getMethodAnnotation(JsonFormat.class);

            //拿到原始数据并转换为字符串
            String jsonStr = JSON.toJSONString(body);

            //字符串转换为json对象，从里面提取value部分，value部分是返回的数据
            JSONObject obj = JSON.parseObject(jsonStr);
            JSONObject value = obj.getJSONObject("value");
            JSONObject filters = obj.getJSONObject("filters");

            String msg = value.getString("msg");
            Integer code = value.getInteger("code");


            //判断JsonFormat.dataType 是否等于list_hashmap
            if (((JsonFormat) anoJsonFormat).dataType().equals("list_hashmap")) {

                //拿到子cmds...
                JsonForamtCmd[] cmds = ((JsonFormat) anoJsonFormat).value();
                List<Object> retJsonArray = new ArrayList<Object>();

                //循环cmds...
                for (int i = 0; i < cmds.length; i++) {
                    JsonForamtCmd cmd = cmds[i];

                    //执行移动命令
                    if (cmd.cmd().equals("mv")) {
                        String orgKey = cmd.okey();
                        String desRoot = cmd.d();
                        String desKey = cmd.dkey();
                        CommandMVProcessor cmvp = new CommandMVProcessor(orgKey, desRoot, desKey);
                        value = cmvp.process(value);
                    }

                    if (cmd.cmd().equals("raw")) {
                        String orgKey = cmd.okey();
                        CommandRAWProcessor crawp = new CommandRAWProcessor(orgKey);
                        value = crawp.process(value);
                    }


                }//for

                //修复code和msg的状态
                if (value.getJSONObject("msg") == null) value.put("msg" , msg);
                if (value.getJSONObject("code") == null) value.put("code" , code);
                body = value;

            }//if cmd dataype equals list_hashmap

            
        }
        return super.beforeBodyWrite(body, returnType, selectedContentType, selectedConverterType, request, response);
    }


}
