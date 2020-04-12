package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.DocumentContentDao;
import com.orhonit.modules.generator.entity.DocumentContentEntity;
import com.orhonit.modules.generator.service.DocumentContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("documentContentService")
public class DocumentContentServiceImpl extends ServiceImpl<DocumentContentDao, DocumentContentEntity> implements DocumentContentService {

    @Autowired
    DocumentContentDao documentContentDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DocumentContentEntity> page = this.selectPage(
                new Query<DocumentContentEntity>(params).getPage(),
                new EntityWrapper<DocumentContentEntity>()
        );
        page.setTotal(this.selectCount(new EntityWrapper<DocumentContentEntity>()));
        return new PageUtils(page);
    }

    @Override
    public List<DocumentContentEntity> getById(String documentId) {
        return documentContentDao.getById(documentId);
    }
}