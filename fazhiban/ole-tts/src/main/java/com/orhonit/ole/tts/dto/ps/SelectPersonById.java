package com.orhonit.ole.tts.dto.ps;

import lombok.Data;

@Data
public class SelectPersonById {
	/*照片				picture
	所属部门			dept_id
	执法人员姓名		name
	性别				sex
	执法人编号			cert_num
	文化程度			edu
	出生日期			birthday
	岗位职责			duty
	民族				nation
	电话				tel
	执法证号			cert_num
	创建人				create_name*/
	private String picture;
	private String deptName;
	private String name;
	private String sex;
	private String code;
	private String edu;
	private String birthday;
	private String duty;
	private String nation;
	private String tel;
	private String certNum;
	private String createName;
	
}
