package com.orhonit.ole.common.constants;

public enum ResultCode {
	INTERNAL_SERVER_ERROR(500, "服务器内部错误."),
	FIELD_ERROR(400, "表单验证不通过."),
	BUSI_ERROR(405, "程序运行过程中出现异常."),
	TOKEN_IS_NULL(406, "token为空"),
    SUCCESS(0, "成功."),
	ERROR(-1, "失败."),
	
	// App接口相关
	APP_ERROR_CERT_NUM             (1001, "执法证件号码不存在"),
	APP_PERSON_CERT_NUM_NOT_UNIQUE (1002, "执法人员证件号重复"),
	APP_PERSON_NOT_EFFECT          (1003, "无效的执法人员"),
	APP_USER_PERSON_NOT_BINDING    (1004, "执法人员未在系统中绑定用户"),
	APP_ERROR_PASSWORD             (1005, "密码错误"),
	APP_PARAM_ERROR                (1006, "参数错误"),
	APP_TOKEN_ERROR                (1007, "token为空"),
	APP_TOKEN_FORMAT_ERROR         (1008, "token格式错误"),
	APP_CASE_NOT_EXIST             (1009, "案件不存在"),
	APP_TEMP_NOT_EXIST             (1010, "模板不存在"),
	APP_TEMP_FORMAT_ERROR          (1011, "文书格式不正确"),
	APP_ERROR_ADVICE			   (1012, "流程错误"),
	APP_SAVE_ERROR                 (1013, "数据保存失败"),
	APP_ROLE_ERROR                 (1014, "角色错误"),
	APP_TEMP_CONTENT_NULL		   (1015, "文书内容为空"),
	APP_YUJ_FAIL				   (1016, "预警处理失败"),
	APP_SUBMIT_ERROR			   (1017, "流程提交失败"),
	APP_TEMP_DOWNLOAD_ERROR		   (1018, "下载PDF格式文书失败"),
	APP_PERSON_TEL_ERROR		   (1019, "电话号码格式不对"),
	APP_PERSON_TEL_NULL		   (1020, "电话号码为空"),
	APP_CODE_ERROR		   (1021, "验证码错误"),
	APP_VERSION_ERROR		   (1022, "有更新版本"),
	// 公式接口相关
	;
	
	private Integer code;

    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}