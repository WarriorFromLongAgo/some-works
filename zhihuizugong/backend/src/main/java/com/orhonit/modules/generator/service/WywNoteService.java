package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.WywNoteEntity;

import java.util.Map;

/**
 * 悟一悟  笔记记录
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-05 10:03:13
 */
public interface WywNoteService extends IService<WywNoteEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    void deleteWywNote(String noteId);
}

