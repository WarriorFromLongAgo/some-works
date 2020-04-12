package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.DocumentContentDao;
import com.orhonit.modules.generator.dao.DocumentDao;
import com.orhonit.modules.generator.dao.DocumentFileDao;
import com.orhonit.modules.generator.entity.DocumentContentEntity;
import com.orhonit.modules.generator.entity.DocumentEntity;
import com.orhonit.modules.generator.entity.DocumentFileEntity;
import com.orhonit.modules.generator.service.DocumentService;
import com.orhonit.modules.generator.vo.DocumentDataVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("documentService")
public class DocumentServiceImpl extends ServiceImpl<DocumentDao, DocumentEntity> implements DocumentService {

    @Autowired
    DocumentDao documentDao;

    @Autowired
    DocumentFileDao documentFileDao;

    @Autowired
    DocumentContentDao documentContentDao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Integer jjcd1 = 0;
        String jjcd =  (String) params.get("jjcd");
        if(StringUtils.isNotBlank(jjcd)){
            jjcd1 = Integer.parseInt(jjcd);
        }
        Integer mj1 = 0;
        String mj =  (String) params.get("mj");
        if(StringUtils.isNotBlank(mj)){
            mj1 = Integer.parseInt(mj);
        }
        Integer type1 = null;
        String type =  (String) params.get("type");
        if(StringUtils.isNotBlank(type)){
            type1 = Integer.parseInt(type);
        }
        String title =  (String) params.get("title");
        String userId = (String) params.get("userId");
        Page<DocumentEntity> page = this.selectPage(
                new Query<DocumentEntity>(params).getPage(),
                new EntityWrapper<DocumentEntity>()
                        .where(StringUtils.isNotBlank(userId),"(user_id = "+Long.parseLong(userId)+" or createby = "+Long.parseLong(userId)+" or work_id = "+Long.parseLong(userId)+" or lead_id="+Long.parseLong(userId)+" or minister_id="+Long.parseLong(userId)+")")
                        .like(StringUtils.isNotBlank(title),"title",title)
                        .and(jjcd1 != null && jjcd1 != 0,"jjcd ="+jjcd1)
                        .and(mj1 != null && mj1 != 0,"mj ="+mj1)
                        .and(type1 != null && type1 == 1,"type ="+type1)
                        .and(type1 != null && type1 == 2,"type in (2,3,4)")
                        .and(type1 != null && type1 == 5,"type ="+type1)
                        .and(type1 != null && type1 == 6,"type ="+type1)
                        .orderBy("createdate DESC")
        );
        page.setTotal(this.selectCount(new EntityWrapper<DocumentEntity>()
                .where(StringUtils.isNotBlank(userId),"(user_id = "+Long.parseLong(userId)+" or createby = "+Long.parseLong(userId)+" or work_id = "+Long.parseLong(userId)+" or lead_id="+Long.parseLong(userId)+" or minister_id="+Long.parseLong(userId)+")")
                .like(StringUtils.isNotBlank(title),"title",title)
                .and(jjcd1 != null && jjcd1 != 0,"jjcd ="+jjcd1)
                .and(mj1 != null && mj1 != 0,"mj ="+mj1)
                .and(type1 != null && type1 == 1,"type ="+type1)
                .and(type1 != null && type1 == 2,"type in (2,3,4)")
                .and(type1 != null && type1 == 5,"type ="+type1)
                .and(type1 != null && type1 == 6,"type ="+type1)));
        return new PageUtils(page);
    }

    @Override
    public void insertDocument(DocumentEntity document) {
        documentDao.insertDocument(document);
    }

    @Override
    public DocumentEntity getById(String documentId) {
        documentDao.updateRemarks(documentId);
        return documentDao.getById(documentId);
    }

    @Override
    public void removeById(String documentId) {
        documentDao.removeById(documentId);
        documentFileDao.deleteByDocumentId(documentId);
        documentContentDao.deleteByDocumentContentId(documentId);
    }


	@Override
	public Map UnreadTotalAndList(Integer userId) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<DocumentEntity>totalAndList =documentDao.UnreadTotalAndList(userId);
		map.put("total",totalAndList.size());
		map.put("list",totalAndList);
		return map;
	}
    
	//公文通知 更改状态 已阅读
	@Override
	public void updateRemarks(String documentId) {
		documentDao.updateRemarks(documentId);
	}



    @Override
    public Map<String, Object> findData(Map<String, Object> params) {
        Integer page = 1;
        Integer limit = 10;
        if (params.get("page") != null){
            page = Integer.parseInt(params.get("page").toString());
        }
        if (params.get("limit") != null){
            limit = Integer.parseInt(params.get("limit").toString());
        }
        PageHelper.startPage(page,limit);
        List<DocumentDataVO> list = new ArrayList<>();
        PageUtils pageUtils = this.queryPage(params);
        List<DocumentEntity> list1 = (List<DocumentEntity>) pageUtils.getList();
        if (list1 != null && list1.size() > 0){
            for (DocumentEntity documentEntity : list1) {
                DocumentDataVO documentDataVO = new DocumentDataVO();
                documentDataVO.setDocumentEntity(documentEntity);
                List<DocumentContentEntity> docList = documentContentDao.getById(documentEntity.getDocumentId());
                documentDataVO.setDocList(docList);
                params.put("documentId",documentEntity.getDocumentId());
                List<DocumentFileEntity> list2 = documentFileDao.getById(documentEntity.getDocumentId());
                documentDataVO.setFileList(list2);
                list.add(documentDataVO);
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("total",pageUtils.getTotalCount());
        map.put("results",list);
        return map;
    }

}