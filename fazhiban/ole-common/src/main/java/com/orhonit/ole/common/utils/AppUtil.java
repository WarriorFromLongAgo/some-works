package com.orhonit.ole.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.util.StringUtil;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.StringUtils;

/**
 * ole工具类
 * 
 * @author liuzhih
 */
public class AppUtil {
	public static String DATE_FORMAT = "yyyy-MM-dd";

	public static Log logger = LogFactory.getLog(AppUtil.class);

	public static Object getDynamicProxyTarget(Object proxy) {
		Object rtn = null;
		try {
			if (AopUtils.isJdkDynamicProxy(proxy)) {
				Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
				h.setAccessible(true);
				AopProxy aop = (AopProxy) h.get(proxy);
				h.setAccessible(false);

				Field advised = aop.getClass().getDeclaredField("advised");
				advised.setAccessible(true);
				AdvisedSupport a = (AdvisedSupport) advised.get(aop);
				advised.setAccessible(false);

				rtn = a.getTargetSource().getTarget();
			} else {
				rtn = proxy;
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return rtn;
	};

	public static <T> T getDynamicProxyTarget(Object proxy, Class<T> clazz) {
		T rtn = null;
		try {
			if (AopUtils.isJdkDynamicProxy(proxy)) {
				Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
				h.setAccessible(true);
				AopProxy aop = (AopProxy) h.get(proxy);
				h.setAccessible(false);

				Field advised = aop.getClass().getDeclaredField("advised");
				advised.setAccessible(true);
				AdvisedSupport a = (AdvisedSupport) advised.get(aop);
				advised.setAccessible(false);

				rtn = (T) a.getTargetSource().getTarget();
			} else {
				rtn = (T) proxy;
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return rtn;
	};

	/**
	 * 去掉字符串数组中的null元素
	 * 
	 * @param arrays
	 * @return 没有null元素的字符数组
	 */
	public static String[] getNotNullArray(String[] arrays) {
		List<String> l = new ArrayList<String>();
		for (String str : arrays) {
			if (str != null) {
				l.add(str);
			}
		}
		String[] rtn = new String[l.size()];
		l.toArray(rtn);
		return rtn;
	}

	/**
	 * 根据省份证号获取出生日期
	 * 
	 * @param cardid
	 * @return
	 */
	public static String getBirthdate(String cardid) {
		String rtn = "";
		if (cardid == null) {
			return "";
		}
		if (cardid.trim().length() == 18) {
			rtn = cardid.trim().substring(6, 10) + "-" + cardid.trim().substring(10, 12) + "-"
					+ cardid.trim().substring(12, 14);
		}
		if (cardid.trim().length() == 15) {
			rtn = "19" + cardid.trim().substring(6, 8) + "-" + cardid.trim().substring(8, 10) + "-"
					+ cardid.trim().substring(10, 12);
		}
		return rtn;
	}

	/**
	 * 根据出生日期得到年龄（周岁）
	 * 
	 * @param birthdate
	 * @return
	 */
	public static int getAge(Date birthdate) {
		int rtn = 0;
		if (birthdate != null) {
			GregorianCalendar btdate = new GregorianCalendar();
			btdate.setTime(birthdate);

			GregorianCalendar current = new GregorianCalendar();
			rtn = current.get(Calendar.YEAR) - btdate.get(Calendar.YEAR);

			if (btdate.get(Calendar.MONTH) > current.get(Calendar.MONTH)) {
				rtn = rtn - 1;
			} else if (btdate.get(Calendar.MONTH) == current.get(Calendar.MONTH)
					&& btdate.get(Calendar.DAY_OF_MONTH) > current.get(Calendar.DAY_OF_MONTH)) {
				rtn = rtn - 1;
			}
			if (rtn < 0) {
				rtn = 0;
			}
		}
		return rtn;
	}

	/**
	 * 根据出生日期得到 月龄（月龄）
	 * 
	 * @param birthdate
	 * @return
	 */
	public static int getMonthOfAge(Date birthdate) {
		int rtn = 0;
		if (birthdate != null) {
			GregorianCalendar btdate = new GregorianCalendar();
			btdate.setTime(birthdate);

			GregorianCalendar current = new GregorianCalendar();
			rtn = (current.get(Calendar.YEAR) - btdate.get(Calendar.YEAR)) * 12
					+ (current.get(Calendar.MONTH) - btdate.get(Calendar.MONTH));
			if (btdate.get(Calendar.DAY_OF_MONTH) > current.get(Calendar.DAY_OF_MONTH)) {
				rtn = rtn - 1;
			}
		}
		return rtn;
	}

	/**
	 * 根据出生日期得到天数
	 * 
	 * @param birthdate
	 * @return
	 */
	public static int getDayOfAge(Date birthdate) {
		int rtn = 0;
		if (birthdate != null) {
			GregorianCalendar btdate = new GregorianCalendar();
			btdate.setTime(birthdate);
			GregorianCalendar current = new GregorianCalendar();
			rtn = (int) ((current.getTimeInMillis() - btdate.getTimeInMillis()) * 1.0 / (24 * 60 * 60 * 1000));
		}
		return rtn;
	}

	public static byte[] toByteArray(List<Integer> list) {
		if (list != null) {
			int size = list.size();
			byte[] rtn = new byte[size];
			Integer[] l = list.toArray(new Integer[size]);
			for (int i = 0; i < size; i++) {
				rtn[i] = l[i].byteValue();
			}
			return rtn;
		}
		return null;
	}

	/**
	 * 将字符串的ip4 地址转换成 number 数据 计算公司 ipNumber = 16777216*w + 65536*x + 256*y + z
	 * 
	 * @param ipAddress
	 * @return int ip4 地址的数字值返回
	 */
	public static int getIPNumber(String ipAddress) {
		int rtn = 0;
		String s = "";
		if (ipAddress != null) {
			String[] split = ipAddress.split("\\.");
			for (int i = 0; i < split.length; i++) {
				rtn += Integer.parseInt(split[i]) * Math.pow(256, split.length - 1 - i);
				s += Math.pow(256, split.length - 1 - i) + "*" + Integer.parseInt(split[i]) + " + ";
			}
			System.out.println(s);
		}
		return rtn;
	}

	/**
	 * 编码按照规则拆分
	 * 
	 * @param code
	 *            编码：如 01020103
	 * @param role
	 *            规则：如 new int[]{2,4,6,8}
	 * @return
	 */
	public static List<String> splitCode(String code, int[] role) {
		List<String> rtn = new ArrayList<String>();
		if (code == null) {
			return rtn;
		}
		if (role == null) {
			rtn.add(code);
			return rtn;
		}
		StringBuffer sb = new StringBuffer(code);
		for (int i = 0; i < role.length; i++) {
			if (sb.length() >= role[i])
				rtn.add(sb.substring(0, role[i]));
		}
		return rtn;
	}

	/**
	 * 将 含 tree 格式的列表数据，转化成 tree 格式 tree 格式的列表数据格式为含有 主id 和 父id 的数据格式
	 * 
	 * @param list
	 * @param idField,主id属性名
	 * @param pidField,父id属性名
	 * @param pkField
	 *            GlobalsName.FIELD_SYSID
	 * @param labelField,tree
	 *            需要显示的 label 字段名称
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String, Object>> list2Tree(List list, String pidField, String pkField, String refId) {
		return list2Tree(list, pidField, pkField, null, refId);
	}

	/**
	 * 将 含 tree 格式的列表数据，转化成 tree 格式 tree 格式的列表数据格式为含有 主id 和 父id 的数据格式
	 * 
	 * @param list
	 * @param idField,主id属性名
	 * @param pidField,父id属性名
	 * @param pkField
	 *            GlobalsName.FIELD_SYSID
	 * @param labelField,tree
	 *            需要显示的 label 字段名称
	 * @param handle
	 *            AppUtil.List2Tree
	 * @return List<Map<String,Object>>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Map<String, Object>> list2Tree(List list, String pidField, String pkField, List2Tree handle,
			String refId) {
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> firstGradeList = new ArrayList<Map<String, Object>>();
		Map<String,Object> tempMap=new HashMap<>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> obj = null;
			if (list.get(i) instanceof Map) {
				obj = ((Map<String, Object>) list.get(i));
			} else {
				obj = (Map<String, Object>) bean2Map(list.get(i));
			}
			if (map.get(obj.get(pidField) + "") == null) {
				List<Map<String, Object>> sub = new ArrayList<Map<String, Object>>();
				sub.add(obj);
				map.put(obj.get(pidField) + "", sub);
			} else {
				map.get(obj.get(pidField) + "").add(obj);
			}
			if (handle == null) {
				if (obj.get(pidField) == null || "-1".equals(obj.get(pidField)) || "".equals(obj.get(pidField))
						|| "0".equals(obj.get(pidField).toString())
						|| (StrUtil.isNotEmpty(refId) && refId.equals(obj.get("refId").toString()))) {
					if(StrUtil.isNotEmpty(refId)){
						tempMap.put(obj.get(pkField).toString(), obj);
						if(!StringUtils.isEmpty(obj.get(pidField))&&!tempMap.containsKey(obj.get(pidField).toString())){
							firstGradeList.add(obj);
						}
					}else{
						firstGradeList.add(obj);
					}
				}
			} else {
				if (handle.isFirstNode(obj)) {
					firstGradeList.add(obj);
				}
			}
		}
		iterableList(firstGradeList, map, pkField);
		return firstGradeList;
	}

	private static void iterableList(List<Map<String, Object>> firstGradeList,
			Map<String, List<Map<String, Object>>> map, String pkField) {
		for (Map<String, Object> m : firstGradeList) {
			List<Map<String, Object>> sublist = map.remove(m.get(pkField) + "");
			if (sublist != null) {
				m.put("children", sublist);
				iterableList(sublist, map, pkField);
			}
		}
	}

	/**
	 * 将 JavaBean 对象转化成 BasicMap 对象
	 * 
	 * @param javaBean
	 * @return
	 */
	public static Map<String, Object> bean2Map(Object javaBean) {
		Map<String, Object> rtn = new HashMap<String, Object>();
		if (javaBean != null) {
			Method[] ms = javaBean.getClass().getMethods();
			for (Method m : ms) {
				if (!"getClass".equals(m.getName()) && m.getName().matches("get.*")
						&& m.getTypeParameters().length == 0) {
					Object obj = null;
					try {
						obj = m.invoke(javaBean);
					} catch (Exception e) {
						e.printStackTrace();
					}
					rtn.put(m.getName().replace("get", "").toLowerCase(), obj);
				}
			}
		}
		return rtn;
	}

	/**
	 * 将Tree格式数据（含有children Key）转换成List，并生成代理主键及父子关系
	 * 
	 * @param tablename
	 *            存储tree数据的表的名字
	 * @param treeFormat
	 *            tree 格式数据，从页面传递过来的数据
	 * @param pidField
	 *            父编码字段
	 * @param pkField
	 *            GlobalsName.FIELD_SYSID
	 * @param sortField
	 *            排序字段
	 * @param tree2List
	 *            AppUtil.Tree2List
	 * @return 返回 List<Map<String,Object>> 类型
	 * @throws AppException
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> tree2List(List<? extends Map<String, Object>> treeFormat, String pidField,
			String pkField, String sortField, String gradeField) throws Exception {
		return tree2List(treeFormat, pidField, pkField, sortField, gradeField, null);
	}

	/**
	 * 将Tree格式数据（含有children Key）转换成List，并生成代理主键及父子关系
	 * 
	 * @param tablename
	 *            存储tree数据的表的名字
	 * @param treeFormat
	 *            tree 格式数据，从页面传递过来的数据
	 * @param pidField
	 *            父编码字段
	 * @param pkField
	 *            GlobalsName.FIELD_SYSID
	 * @param sortField
	 *            排序字段
	 * @param tree2List
	 *            AppUtil.Tree2List
	 * @return 返回 List<Map<String,Object>> 类型
	 * @throws AppException
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> tree2List(List<? extends Map<String, Object>> treeFormat, String pidField,
			String pkField, String sortField, String gradeField, Tree2List tree2List) throws Exception {
		List<Map<String, Object>> rtn = new ArrayList<Map<String, Object>>();
		if (treeFormat != null) {
			int sortValue = 1;
			for (Map<String, Object> map : treeFormat) {
				/*
				 * 一级节点pid 为 null，无数据 if(!"puid".equals(pidField)){
				 * map.put(pidField, -1); }
				 */
				if (sortField != null) {
					map.put(sortField, sortValue);
					sortValue++;
				}
				if (gradeField != null) {
					map.put(gradeField, 1);
				}
				rtn.add(map);

				/*
				 * map.put("lft", 1); map.put("rgt", 2);
				 */

				iterableList(rtn, map, pidField, pkField, sortField, gradeField, 2, tree2List);
			}
		}
		return rtn;
	}

	@SuppressWarnings({ "unchecked" })
	private static void iterableList(List<Map<String, Object>> list, Map<String, Object> firstMap, String pidField,
			String pkField, String sortField, String gradeField, Integer grade, Tree2List tree2List) throws Exception {
		Object obj = firstMap.remove("children");
		if (obj != null && obj instanceof List) {
			List<Map<String, Object>> children = (List<Map<String, Object>>) obj;
			if (children != null && !children.isEmpty()) {
				int sortValue = 101;
				for (Map<String, Object> m : children) {
					if (tree2List != null) {
						tree2List.processData(firstMap, m);
					}
					if (sortField != null) {
						m.put(sortField, sortValue);
						sortValue++;
					}
					if (gradeField != null) {
						m.put(gradeField, grade);
					}

					/*
					 * if(firstMap.get("lft")!=null){ m.put("lft",
					 * AppUtil.toInteger(firstMap.get("lft")) + 1); }
					 */

					m.put(pidField, firstMap.get(pkField));
					list.add(m);
					Integer l = 2;
					if (gradeField != null) {
						l = (Integer) m.get(gradeField);
					}
					iterableList(list, m, pidField, pkField, sortField, gradeField, ++l, tree2List);
				}
			} else {
				// firstMap.put("leaf", true);
				if (tree2List != null) {
					tree2List.processData(firstMap, null);
				}
			}
		} else {
			// firstMap.put("leaf", true);
			if (tree2List != null) {
				tree2List.processData(firstMap, null);
			}
		}
	}

	/**
	 * list2tree 接口判断
	 * 
	 * @author wangbao
	 */
	public static interface List2Tree {
		boolean isFirstNode(Map<String, Object> obj);
	}

	/**
	 * tree2list 扩展接口
	 * 
	 * @author wang
	 *
	 */
	public static interface Tree2List {
		void processData(Map<String, Object> parent, Map<String, Object> child);
	}

	public static interface GroupHandle {
		/**
		 * 处理每组数据的 List 为横向数据
		 * 
		 * @param list
		 *            每一组中的数据（ groupField 组中的数据列表）
		 * @param data
		 *            最终形成的横向数据
		 */
		void processData(List<Map<String, Object>> list, Map<String, Object> data);

		/**
		 * 处理分组的 key 数据
		 */
		Object processGroupKey(Object groupKey);

	}

	/**
	 * 校验的计算方式： 1. 对前17位数字本体码加权求和 公式为：S = Sum(Ai * Wi), i = 0, ... , 16
	 * 其中Ai表示第i位置上的身份证号码数字值，Wi表示第i位置上的加权因子，其各位对应的值依次为： 7 9 10 5 8 4 2 1 6 3 7 9
	 * 10 5 8 4 2 2. 以11对计算结果取模 Y = mod(S, 11) 3. 根据模的值得到对应的校验码 对应关系为： Y值： 0 1 2
	 * 3 4 5 6 7 8 9 10 校验码： 1 0 X 9 8 7 6 5 4 3 2
	 **/
	// 身份证验证
	public static boolean isPersonCard(String cardno) {
		if (cardno == null) {
			return false;
		}
		// 创建日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// 要求严格检查日期格式
		sdf.setLenient(false);

		if (cardno.trim().length() != 15 && cardno.trim().length() != 18) {
			return false;
		}
		if (cardno.trim().length() == 18) {

			// 当截取的字符串不符合yyyyMMdd格式时，返回false
			try {
				sdf.parse(cardno.trim().substring(6, 14));
			} catch (Exception e) {
				return false;
			}

			String validatechar = cardno.substring(17, 18);
			// 加权因子
			int[] weighting = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
			// 校验码
			String[] validate = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
			int s = 0;
			for (int i = 0; i < weighting.length; i++) {
				s = s + (Integer.parseInt(String.valueOf(cardno.trim().toCharArray()[i]).trim())) * weighting[i];
			}
			if (!validatechar.equals(validate[s % 11])) {
				return false;
			}
		} else {
			// 当截取的字符串不符合yyyyMMdd格式时，返回false
			try {
				sdf.parse("19" + cardno.trim().substring(6, 12));
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 验证移动手机号码是否合法
	 * 
	 * @param num
	 * @return true 合法 false 不合法
	 */
	public static boolean isMobileNum(String num) {
		boolean rtn = false;
		// 验证手机正则表达式
		Pattern p = Pattern.compile("^1[34578][0-9]{9}$");
		Matcher m = p.matcher(num);
		rtn = m.matches();
		return rtn;
	}
}
