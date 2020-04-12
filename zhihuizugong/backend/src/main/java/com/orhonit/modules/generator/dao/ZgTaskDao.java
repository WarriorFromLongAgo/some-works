package com.orhonit.modules.generator.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.ZgTaskEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 承诺践诺/工作任务
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 16:05:31
 */
@Mapper
public interface ZgTaskDao extends BaseMapper<ZgTaskEntity> {

    void save(ZgTaskEntity zgTask);

	List<ZgTaskEntity> selectCount(@Param("params")Map<String, Object> params);

	List<ZgTaskEntity> selectAllList(@Param("params")Map<String, Object> params);
	/**
	 * 查询任务详情
	 * @param id
	 * @return
	 */
	ZgTaskEntity selectTask(@Param("id")Integer id);
	/**
	 * 修改完成状态
	 * @param id
	 */
	@Update("update zg_task set status = 3 where id = #{id}")
	void updateStatus(Integer id);

	List<ZgTaskEntity> statistiList(@Param("params")Map<String, Object> params);

	List<ZgTaskEntity> selectPieChart(@Param("params")Map<String, Object> params);

	@Insert("insert zg_task(work_task,claim_time,status,people_id) value(#{workTask},#{claimTime},#{status},#{peopleId})")
    void updateTaskClaim(ZgTaskEntity zgTaskEntity);

	List<ZgTaskEntity> selectReportCount(@Param("params")Map<String, Object> params);

	List<ZgTaskEntity> selectReportList(@Param("params")Map<String, Object> params);

	/**
	 * APP端督办任务列表
	 * @param userId
	 * @return
	 */
    List<ZgTaskEntity> selectOversee(Long userId);
	/**
	 * APP端调度任务列表
	 * @param userId
	 * @return
	 */
	List<ZgTaskEntity> selectScheduling(Long userId);

	/**
	 * 修改任务阅读状态
	 * @param id
	 */
	@Update("update ej_task set is_read = 1 where id = #{id}")
	void updateIsRead(Integer id);

	List<ZgTaskEntity> selectCompleteList(@Param("userId")Long userId);
}
