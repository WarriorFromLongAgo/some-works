package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.DocumentFileEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 公文管理表
 * 
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-06 15:13:07
 */
@Mapper
public interface DocumentFileDao extends BaseMapper<DocumentFileEntity> {

    @Delete("DELETE FROM `tb_document_file` WHERE document_id = #{documentId}")
    int deleteByDocumentId(String documentId);

    @Select("select * from tb_document_file WHERE document_id = #{documentId}")
    List<DocumentFileEntity> getById(String documentId);
}
