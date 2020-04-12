package com.orhonit.modules.generator.vo;

import lombok.Data;

@Data
public class MeetPeopleVo {
	private Integer peopleId;
	private Long meetId;
	private String stationName;
	private String userTrueName;
	private String mobilePhone;
	private Integer peopleNeedMeet;
	private Integer peopleIsSignin;
	private String peopleLveMsg;
}
