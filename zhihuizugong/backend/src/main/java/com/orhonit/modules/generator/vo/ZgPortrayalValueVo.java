package com.orhonit.modules.generator.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 个人十二边型画像
 */
@Data
public class ZgPortrayalValueVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 科室id
     */
    private Integer lowerId;
    /**
     * 科室名称
     */
    private String lowerName;
    /**
     * 答题考试
     */
    private Integer answer;
    /**
     * 工作点子
     */
    private Integer workIdea;
    /**
     * 成果分享
     */
    private Integer share;
    /**
     * 组织生活
     */
    private Integer orgLive;
    /**
     * 包联帮扶
     */
    private Integer help;
    /**
     * 志愿服务
     */
    private Integer volunteer;
    /**
     * 爱心捐助
     */
    private Integer donate;
    /**
     * 履职尽责
     */
    private Integer resumption;
    /**
     * 亮点工作
     */
    private Integer lightspot;
    /**
     * 特色工作
     */
    private Integer feature;
    /**
     * 理论学习
     */
    private Integer study;
    /**
     * 思悟笔记
     */
    private Integer thinkNote;
}
