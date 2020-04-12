package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.DocumentFileDao;
import com.orhonit.modules.generator.entity.DocumentFileEntity;
import com.orhonit.modules.generator.service.DocumentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("documentFileService")
public class DocumentFileServiceImpl extends ServiceImpl<DocumentFileDao, DocumentFileEntity> implements DocumentFileService {

    @Autowired
    DocumentFileDao documentFileDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String documentId = (String) params.get("documentId");
        Page<DocumentFileEntity> page = this.selectPage(
                new Query<DocumentFileEntity>(params).getPage(),
                new EntityWrapper<DocumentFileEntity>().and("document_id="+documentId)
        );
        page.setTotal(this.selectCount(new EntityWrapper<DocumentFileEntity>().where("document_id="+documentId)));
        return new PageUtils(page);
    }

    @Override
    public List<DocumentFileEntity> getById(String documentId) {
        return documentFileDao.getById(documentId);
    }

}