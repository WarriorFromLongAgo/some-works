package com.orhonit.modules.generator.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.app.vo.AppWjwfVO;
import com.orhonit.modules.generator.entity.SupervisoryEntity;


@Mapper
public interface SupervisoryDao extends BaseMapper<SupervisoryEntity>{
	
	/**
	 * 干部职工列表
	 * @param params
	 * @return
	 */
	List<SupervisoryEntity>getSuperviList(@Param("params")Map<String,Object>params);
	
	/**
	 * 详情
	 * @param id
	 * @return
	 */
	SupervisoryEntity getOneSup(@Param("id")Integer id);
	
	/**
	 * 某单位下的所有干部职工信息
	 * @param id
	 * @return
	 */
	List<SupervisoryEntity>getSupList(@Param("id")Integer id);
	
	
	/**
	 *  查询用户考勤,请销假,出差培训情况统计
	 * @param userId
	 * @return
	 */
	@Select("select \r\n" + 
			"sum(go_out) AS goOut,\r\n" + 
			"sum(train) AS train,\r\n" + 
			"sum(late) AS late,\r\n" + 
			"sum(early_retirement) AS earlyRetirement,\r\n" + 
			"sum(miner) AS miner,\r\n" + 
			"sum(sick_leave) AS sickLeave,\r\n" + 
			"sum(compassionate_leave) AS compassionateLeave,\r\n" + 
			"sum(vacation) AS vacation,\r\n" + 
			"ceng_mian AS cengMian\r\n"+
			"from se_supervisory_information\r\n" + 
			"where is_del='0'and user_id=#{userId}")
	SupervisoryEntity countAttendance(@Param("userId")Integer userId);
	
	/**
	 * 违纪违法
	 * @param userId
	 * @return
	 */
	List<AppWjwfVO>getWjwfList(@Param("params")Map<String,Object>params);
	
	
	
}
