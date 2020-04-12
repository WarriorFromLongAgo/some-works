package com.orhonit.modules.generator.app.vo;

import lombok.Data;

/**
 * 讲堂&&书苑排行
 * @author YaoSC
 *
 */
@Data
public class AppLectureHallVO {
	
	private Integer index;//排行
	
	private String userName;//姓名
	
	private Integer totalScore; //总分
	
	private Integer TotalLengthTime; //总看时间
	
	//private String readoutRate;//看完比例
	
	//private Integer sp;   //综合视频数量

}
