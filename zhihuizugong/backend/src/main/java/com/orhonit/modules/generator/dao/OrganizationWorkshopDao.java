package com.orhonit.modules.generator.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.app.vo.AppLectureHallVO;
import com.orhonit.modules.generator.entity.OrganizationWorkshopEntity;
import com.orhonit.modules.generator.vo.OrganizationWorkshopVO;


/**
 * 组工讲堂
 * @author YaoSC
 *
 */
/**
 * Title: OrganizationWorkshopDao.java
 * @Description:
 * @author YaoSC
 * @date 2019年6月12日 
 */
/**
 * Title: OrganizationWorkshopDao.java
 * @Description:
 * @author YaoSC
 * @date 2019年6月12日 
 */
@Mapper
public interface OrganizationWorkshopDao extends BaseMapper<OrganizationWorkshopEntity>{
	
	//单条删
	@Delete("DELETE FROM os__organization_workshop WHERE course_id=#{courseId}")
	void deleteworkShop(Integer courseId);
	
	public boolean updateShop(OrganizationWorkshopEntity entity);
	
	
	//APP端组工讲堂 详情
	@Select("SELECT\r\n" + 
			"course_id,course_name,is_local,course_address,course_time,course_pic,course_type,course_desc,commend_type,course_author,create_time\r\n" + 
			"FROM\r\n" + 
			"os__organization_workshop\r\n" + 
			"WHERE course_id=#{courseId}")
	 OrganizationWorkshopEntity selectWorkShop(@Param("courseId")Integer courseId);
	
	//学习人数+1
	@Update("UPDATE os__organization_workshop SET study_num=study_num+1 WHERE course_id=#{courseId}")
	void upDateStudyNum(Integer courseId);
	
	//讲堂搜索（筛选）
	List<OrganizationWorkshopVO>list(@Param("courseType")String courseType,
			@Param("beginLimit")int beginLimit, @Param("limit")int limit);
	//讲堂列表
	List<OrganizationWorkshopVO>selectList1(@Param("beginLimit")int beginLimit, @Param("limit")int limit);
	/**
	 * 查询用户的总分&总看时长&用户观看视频数量
	 * @param userId
	 * @return
	 */
	@Select("<script>\r\n" + 
			"SELECT \r\n" + 
			"user.user_true_name AS userName,\r\n" + 
			"SUM(record.get_integral) AS totalScore,\r\n" + 
			"SUM(record.remember_time) AS TotalLengthTime\r\n" + 
			"FROM os_course_record record\r\n" + 
			"LEFT JOIN  sys_user user ON record.user_id = user.user_id\r\n" + 
			"<where>\r\n" + 
			"  <if test='userId > 0'>\r\n" + 
			"    record.user_id = 5\r\n" + 
			"	</if>\r\n" + "AND identifier='3'"+
			"</where>\r\n" + 
			"  <if test='flag'>\r\n" + 
			"	  GROUP BY user.user_true_name\r\n" + 
			"  </if>\r\n" + 
			"	LIMIT #{beginLimit},#{limit}\r\n" + 
			"</script>")
	List<AppLectureHallVO> selectLecture(@Param("flag")boolean flag,@Param("userId")Integer userId,@Param("beginLimit")int beginLimit, @Param("limit")int limit);
	
	/**
	 * 发布的视频数量
	 * @return
	 */
	@Select("SELECT count(*) FROM os__organization_workshop")
	Integer videoNumber();
	
	
	/**
	  *  查询用户书苑积分排行榜
	 * @param flag
	 * @param userId
	 * @param beginLimit
	 * @param limit
	 * @return
	 */
	@Select("<script>\r\n" + 
			"			SELECT \r\n" + 
			"			user.user_true_name AS userName,\r\n" + 
			"			SUM(record.get_integral) AS totalScore,\r\n" + 
			"			SUM(record.remember_time) AS TotalLengthTime \r\n" + 
			"			FROM os_course_record record\r\n" + 
			"			LEFT JOIN  sys_user user ON record.user_id = user.user_id\r\n" + 
			"			<where> \r\n" + 
			"			  <if test='userId > 0'>\r\n" + 
			"			    record.user_id = #{userId}\r\n" + 
			"				</if>\r\n" + 
			"			AND identifier='4'\r\n" + 
			"			</where>\r\n" + 
			"			  <if test='flag'>\r\n" + 
			"				  GROUP BY user.user_true_name\r\n" + 
			"			  </if>\r\n" + 
			"				LIMIT #{beginLimit},#{limit}\r\n" + 
			"			</script>")
	List<AppLectureHallVO> selectBookeStore(@Param("flag")boolean flag,@Param("userId")Integer userId,
			@Param("beginLimit")int beginLimit, @Param("limit")int limit);
	
	//讲堂列表
	List<OrganizationWorkshopEntity>shipinList(@Param("params")Map<String,Object>Params);
	
}
