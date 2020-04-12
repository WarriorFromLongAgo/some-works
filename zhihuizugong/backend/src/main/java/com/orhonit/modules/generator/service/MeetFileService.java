package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.MeetFileEntity;

import java.util.List;
import java.util.Map;

/**
 * 会议附件
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-17 16:37:36
 */
public interface MeetFileService extends IService<MeetFileEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<MeetFileEntity> getById(String meetId);

    void insertFile(List<MeetFileEntity> meetFile);
    
    int deleteBymeetId(String meetId);
    
    void updateByMeetFile(MeetFileEntity meetFile);
}

