package com.orhonit.modules.generator.app.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.StringUtils;
import com.orhonit.modules.generator.app.vo.AppIdeaCommentVO;

public class ReflectUtils {
	
	/**
	 * 获取bean对象中的字段
	 * @param o
	 * @return
	 */
	public static String getFiled(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		StringBuffer sb = new StringBuffer();
		for(Field f : fields) {
			f.setAccessible(true);
			sb.append(f.getName()+",");
		}
		return sb.substring(0,sb.length()-1);
	}
	
	/**
	 * 移除没有值的字段
	 * @param o
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map filterEmptyValue(Object o) {
		Map map = new HashMap();
		Field[] fields = o.getClass().getDeclaredFields();
		StringBuffer sb = new StringBuffer();
		for(Field f :fields ) {
			f.setAccessible(true);
			try {
				if(f.get(o)!=null){
					Object value = f.get(o);
					if(!StringUtils.isNullOrEmpty(value.toString())) {
						map.put(f.getName(), value);
						
					}
				}
			} catch (IllegalArgumentException e) {
				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}
	
	public static void main(String[] args){
		AppIdeaCommentVO vo = new AppIdeaCommentVO();
		vo.setCommentCrtName("张开");
		System.out.println("所有属性="+ReflectUtils.getFiled(vo));
		System.out.println("有值的属性"+ReflectUtils.filterEmptyValue(vo));
	}	

}
