package com.orhonit.ole.enforce.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.orhonit.ole.enforce.entity.SmsEntity;

import net.sf.json.JSONObject;

/**
 * 短信发送接口
 * @author liuzh
 *
 */
public class SmsSend {

	 	public static final String DEF_CHATSET = "UTF-8";
	    public static final int DEF_CONN_TIMEOUT = 30000;
	    public static final int DEF_READ_TIMEOUT = 30000;
	    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	 
	    //配置您申请的KEY
	    public static final String APPKEY ="e9c859cc128998a55b0c09781ff50d36";
	 
	    //1.屏蔽词检查测
	    public static void getRequest1(){
	        String result =null;
	        String url ="http://v.juhe.cn/sms/black";//请求接口地址
	        Map params = new HashMap();//请求参数
	        params.put("word","");//需要检测的短信内容，需要UTF8 URLENCODE
	        params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
	        try {
	            result =net(url, params, "GET");
	            JSONObject object = JSONObject.fromObject(result);
	            if(object.getInt("error_code")==0){
	                System.out.println(object.get("result"));
	            }else{
	                System.out.println(object.get("error_code")+":"+object.get("reason"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	    //2.发送短信
	    public static boolean sendMsg(SmsEntity smsEntity,String tplId){
	    	boolean flag=false;
	        String result =null;
	        String url ="http://v.juhe.cn/sms/send";//请求接口地址
	        Map params = new HashMap();//请求参数
            params.put("mobile",smsEntity.getTelNum());//接收短信的手机号码
            params.put("tpl_id",tplId);//短信模板ID，请参考个人中心短信模板设置
            Map<String,Object> map=new HashMap<>();
           //变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
            params.put("tpl_value","#code#="+smsEntity.getContent());
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
            //params.put("dtype","");//返回数据的格式,xml或json，默认json
	        try {
	            result =net(url, params, "GET");
	            JSONObject object = JSONObject.fromObject(result);
	            if(object.getInt("error_code")==0){
	                System.out.println(object.get("result"));
	                flag=true;
	            }else{
	                System.out.println(object.get("error_code")+":"+object.get("reason"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return  flag;
	        }
	        return  flag;
	    }
	 
	    public static void main(String[] args) {
	 
	    }
	 
	    /**
	     *
	     * @param strUrl 请求地址
	     * @param params 请求参数
	     * @param method 请求方法
	     * @return  网络请求字符串
	     * @throws Exception
	     */
	    public static String net(String strUrl,Map params,String method) throws Exception {
	        HttpURLConnection conn = null;
	        BufferedReader reader = null;
	        String rs = null;
	        try {
	            StringBuffer sb = new StringBuffer();
	            if(method==null || method.equals("GET")){
	                strUrl = strUrl+"?"+urlencode(params);
	            }
	            URL url = new URL(strUrl);
	            conn = (HttpURLConnection) url.openConnection();
	            if(method==null || method.equals("GET")){
	                conn.setRequestMethod("GET");
	            }else{
	                conn.setRequestMethod("POST");
	                conn.setDoOutput(true);
	            }
	            conn.setRequestProperty("User-agent", userAgent);
	            conn.setUseCaches(false);
	            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
	            conn.setReadTimeout(DEF_READ_TIMEOUT);
	            conn.setInstanceFollowRedirects(false);
	            conn.connect();
	            if (params!= null && method.equals("POST")) {
	                try {
	                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
	                        out.writeBytes(urlencode(params));
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	            InputStream is = conn.getInputStream();
	            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
	            String strRead = null;
	            while ((strRead = reader.readLine()) != null) {
	                sb.append(strRead);
	            }
	            rs = sb.toString();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                reader.close();
	            }
	            if (conn != null) {
	                conn.disconnect();
	            }
	        }
	        return rs;
	    }
	 
	    //将map型转为请求参数型
	    public static String urlencode(Map<String,Object>data) {
	        StringBuilder sb = new StringBuilder();
	        for (Map.Entry i : data.entrySet()) {
	            try {
	                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
	            } catch (UnsupportedEncodingException e) {
	                e.printStackTrace();
	            }
	        }
	        return sb.toString();
	    }
	    
	    /** 
	     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 
	     * 此方法中前三位格式有： 
	     * 13+任意数 
	     * 15+除4的任意数 
	     * 18+除1和4的任意数 
	     * 17+除9的任意数 
	     * 147 
	     */  
	    public static boolean isChinaPhoneLegal(String str) {  
	        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";  
	        Pattern p = Pattern.compile(regExp);  
	        Matcher m = p.matcher(str);  
	        return m.matches();  
	    }  
}
