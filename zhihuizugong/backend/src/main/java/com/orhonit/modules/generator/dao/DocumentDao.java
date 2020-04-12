package com.orhonit.modules.generator.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.orhonit.modules.generator.entity.DocumentEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 公文管理主表
 * 
 * @author xiaobai
 * @email ${email}
 * @date 2019-06-04 14:31:05
 */
@Mapper
public interface DocumentDao extends BaseMapper<DocumentEntity> {
    void insertDocument(DocumentEntity document);

    @Select("select document_id,fwjg,fwzh,jjcd,mj,title,content,blqk,type,createby,create_name createName from tb_document where document_id = #{documentId}")
    DocumentEntity getById(String documentId);

    @Delete("delete from tb_document where document_id = #{documentId}")
    void removeById(String documentId);
    
    //公文通知   未读条数和未读列表
    @Select("SELECT * FROM  tb_document WHERE remarks='0' AND \n" + 
    		"(createby=#{userId} or user_id=#{userId} or work_id=#{userId} or lead_id=#{userId} or minister_id=#{userId}) \n" + 
    		"GROUP BY document_id ")
    List<DocumentEntity>UnreadTotalAndList(@Param("userId")Integer userId);
    
    //公文通知   更改remarks=1 已阅读
    @Update("UPDATE tb_document SET remarks='1' WHERE document_id=#{documentId}")
    void updateRemarks(@Param("documentId")String documentId);
}
