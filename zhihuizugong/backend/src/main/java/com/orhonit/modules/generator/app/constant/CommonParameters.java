package com.orhonit.modules.generator.app.constant;



/**
 * 常量类
 */
public interface CommonParameters {
	
	
	/**
	 * 学习园地
	 * title:考一考
	 * param:  1:套卷练习   2:在线考试 
	 * @author YaoSC
	 *
	 */
	public static class AppOsQuestionDomain {
		// 套卷练习
		 public static final String SeriesExercise = "1";
		// 在线答题
		 public static final String OnlineExam = "2";
	}
	public static class OsScopeType {
		// 测试
		 public static final String Exercise = "1";
		// 正式
		 public static final String Formal = "2";
	}
	/**
	 * 
	 * Title: CommonParameters.java
	 * @Description:出题
	 * @author YaoSC
	 * @date 2019年7月30日
	 */
	public static class OsExamRandom{
		//顺序
		public static final String IS_ORDER="0";
		//随机
		public static final String IS_RANDOM="1";
	}
	
	/**
	  * 大家来分享
	 * param: 	1:图片  2:视频  3:其他
	 * @author YaoSC
	 *
	 */
	public static class AppEverBodyShareL{
		 //图片
		 public static final Integer file_picture=1;
		 //视频
		 public static final Integer file_video=2;
		 //其他
		 public static final Integer file_other=3;
	}
	
	
	public static class TypeSoul{
		 //书苑类型
		 public static final Integer BOOKSTORE_TYPE=1;
		 //视频类型
		 public static final Integer VIDEO_TYPE=2;
	}
	
	/**
	  * 模块
	 * @author YaoSC
	 *
	 */
	public static class ProjectModeules{
		//大家来分享
		public static final Integer APP_EVER_BODY_SHAREL=1;
		//悟一悟
		public static final Integer COME_TO_REALIZE=2;
		//组工讲堂
		public static final Integer LEARNALESSON_ORGANIZATIONWORKSHOP=3;
		//组工书苑
		public static final Integer LEARNALESSON_BOOKESTORE=4;
		//请你帮帮我
		public static final Integer PLEASS_HELP_ME=5;
		//我为组工出点子
		public static final Integer IWITH_IDEAS_THE_TEAM=6;
	}
	
	
	/**
	 * 组工讲堂 推荐排行
	 * @author YaoSC
	 *
	 */
	public static class Forum{
		//精品
		public static final String BOUTIQYUE="2";
		//一般
		public static final String COMMONLY="0";
		//优选
		public static final String OPTIMIZATION="1";
		//高分值
		public static final String HIGH_SCORE="3";
		//置顶
		public static final String ROOF_PLEACEMENT="4";
	}
	
	/**
	 * 组工讲堂    1:未学习  2:已学习
	 */
	public static class studystatus {
		//未学习
		public static final String STUDY_STATUS_NO="1";
		//已学习
		public static final String STUDY_STATUS_YES="2";
	}
	
	public static class ideaType{
		//帖子
		public static final String IDEA_TYPE_ONE="1";
		//评论点赞
		public static final String IDEA_TYPE_TWO="2";
		//回复点赞
		public static final String IDEA_TYPE_THREE="3";
	}
	
	public static class isDel{
		//已删除
		public static final String IS_DEL_YES="1";
		//未删除
		public static final String IS_DEL_NO="0";
	}

}
