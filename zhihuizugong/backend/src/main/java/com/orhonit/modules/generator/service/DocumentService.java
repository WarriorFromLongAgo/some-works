package com.orhonit.modules.generator.service;

import com.baomidou.mybatisplus.service.IService;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.modules.generator.entity.DocumentEntity;

import java.util.Map;

/**
 * 公文管理主表
 *
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-05 09:56:34
 */
public interface DocumentService extends IService<DocumentEntity> {

    PageUtils queryPage(Map<String, Object> params);

    DocumentEntity getById(String documentId);

    void insertDocument(DocumentEntity document);

    void removeById(String documentId);

    
    //公文通知   未读条数及未读列表
    Map  UnreadTotalAndList(Integer userId);
    
    //公文通知   更改阅读状态  已读
    void updateRemarks(String documentId);


    Map<String, Object> findData(Map<String, Object> params);

}

