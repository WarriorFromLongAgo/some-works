package com.orhonit.ole.common.utils;

/**
 * 系统信息工具类
 * @author 武跃忠
 *
 */
public class SysUtil {

	/**
	 * 返回当前系统类型
	 * windows返回true
	 * 其他返回false
	 * @return
	 */
	public static boolean sysIsWin(){
		String osName = System.getProperties().getProperty("os.name"); //操作系统名称   
		if(osName.startsWith("Win")){
			return true;
		}
		return false;
	}
}
