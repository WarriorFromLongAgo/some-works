package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.DocumentContentEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 
 * 
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-04 14:31:05
 */
@Mapper
public interface DocumentContentDao extends BaseMapper<DocumentContentEntity> {

    @Select("SELECT * FROM `tb_document_content` AS tdc WHERE tdc.`document_id` = #{documentId}")
    List<DocumentContentEntity> getById(String documentId);

    @Delete("DELETE FROM `tb_document_content` WHERE document_id = #{documentId}")
    int deleteByDocumentContentId(String documentId);
}
