package com.orhonit.modules.generator.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 题库表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-23 10:58:55
 */
@Data
@TableName("os_question_library")
public class OsQuestionLibraryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 题库id
	 */
    @TableId
    @GeneratedValue( generator = "UUID")
	private String libraryId;
	/**
	 * 题库名称
	 */
    @Column(name = "library_title")
	private String libraryTitle;
	/**
	 * 题库类型
	 */
    @Column(name = "library_type")
   // @MergeField(key = "os_dict_library_type", feign = DictFeign.class, method = "getDictValues")
	private String libraryType;
	/**
	 * 每题分值
	 */
    @Column(name = "question_scope")
	private Integer questionScope;
    
    /**
         * 创建时间
     */
    @Column(name="library_create_time")
    private Date libraryCreateTime;
    
    /**
         * 更新时间
     */
    @Column(name="library_update_time")
    private Date libraryUpdateTime;
    
    /**
     * 是否删除  0:未删   1:已删
     */
    @Column(name="is_del")
    private String isDel;     
    

}
