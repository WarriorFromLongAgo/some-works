package com.orhonit.modules.generator.service;



import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.ZgTaskEntity;

import java.util.Map;

/**
 * 承诺践诺/工作任务
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 16:05:31
 */
public interface ZgTaskService extends IService<ZgTaskEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(ZgTaskEntity zgTask);

    ZgTaskEntity selectTaskInfo(Integer id,String schedulingId);

	void deleteTask(Integer id);

	void updateStatus(Integer id);

	Map<String, Object> statistiList(Map<String, Object> params);

    void updateTaskClaim(ZgTaskEntity zgTaskEntity);

    PageUtils selectReportList(Map<String, Object> params);
}

