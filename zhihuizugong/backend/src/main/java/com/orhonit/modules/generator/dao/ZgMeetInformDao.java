package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.ZgMeetInformEntity;
import com.orhonit.modules.generator.entity.ZgMeetPeopleEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 会议通知
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-04 15:05:24
 */
@Mapper
public interface ZgMeetInformDao extends BaseMapper<ZgMeetInformEntity> {

	/**
	 * 添加
	 * @param zgMeetInform
	 */
	void save(ZgMeetInformEntity zgMeetInform);

	/**
	 * 分页用
	 * @param params
	 * @return
	 */
	List<ZgMeetInformEntity> findAllCount(@Param("params")Map<String, Object> params);
	/**
	 * 查询列表
	 * @param params
	 * @return
	 */
	List<ZgMeetInformEntity> findAll(@Param("params")Map<String, Object> params);
	/**
	 * 人员列表
	 * @return
	 */
	List<Map<String, Object>> findAllUser(@Param("userId")Long userId);
	/**
	 * 返回所有会议通知
	 * @return
	 */
	List<Map<String, Object>> findMeetList();

	/**
	 * 删除相关人员
	 * @param id
	 */
	@Delete("DELETE FROM zg_meet_people where meet_id = #{id}")
    void delPeo(@Param("id") String id);
	
	/**
	 * 根据用户查询所有未读条数
	 * @param userId
	 * @return
	 */
	@Select("SELECT * FROM zg_meet_people WHERE user_id=#{userId} AND read_type=0")
	List<ZgMeetPeopleEntity> readTotal(@Param("userId")int userId);
	
	/**
	 * 未读列表
	 * @param meetId
	 * @return
	 */
	@Select("SELECT id,user_id userId,meet_title meetTitle,meet_host meetHost,meet_report meetReport,meet_site meetSite,start_time startTime,end_time endTime,create_time createTime,remark FROM zg_meet_inform WHERE id=#{meetId}")
	ZgMeetInformEntity meetList(@Param("meetId")String meetId);
	@Update("update zg_meet_people set read_type = 1 where meet_id = #{id} and user_id = #{userId}")
    void updateReadType(@Param("id")String id,@Param("userId")Long userId);
	/**
	 * 根据用户查询所有未读条数
	 * @param userId
	 * @return
	 */
	@Select("SELECT * FROM zg_meet_people WHERE user_id=#{userId} AND read_type=0")
	List<ZgMeetPeopleEntity> readMeetInforTotal(Long userId);
}
