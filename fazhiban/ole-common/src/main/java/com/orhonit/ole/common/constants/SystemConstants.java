package com.orhonit.ole.common.constants;

import lombok.Getter;

@Getter
public enum SystemConstants {
	
	USER_STATUS_NORMAL(0, "正常"),
	USER_STATUS_LOCK(1, "锁定"),
	USER_STATUS_UNNORMAL(2, "非正常"),
	
	SYS_ENFORCE(1,"在线执法"),
	SYS_BASE(2,"基础"),
	SYS_AUTH(3,"权限"),
	
	MENU_SYS(0,"系统"),
	MENU_FRIST(1,"菜单"),
	MENU_BTN(2,"按钮"),
	
	PERMISSION_ID_AUTH(46,"权限"),
	PERMISSION_ID_ENFORCE(44, "在线执法"),
	PERMISSION_ID_BASE(45, "基础"),
	PERMISSION_ID_REPORT(60,"决策分析"),
	PERMISSION_ID_WARN(151,"预警监控"),
	FLOW_CASE(1, "case");

    private Integer code;

    private String message;

    SystemConstants(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}


