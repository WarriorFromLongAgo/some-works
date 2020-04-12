package com.orhonit.modules.generator.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ZgPortrayalValueEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 学习力
     */
    private Integer studyRank;
    /**
     * 思考力
     */
    private Integer thinkRank;
    /**
     * 执行力
     */
    private Integer executeRank;
    /**
     * 创新力
     */
    private Integer innovateRank;
    /**
     * 协同力
     */
    private Integer synergyRank;
    /**
     * 服务力
     */
    private Integer serveRank;
    /**
     * 所属科室
     */
    private Integer lowerId;
    /**
     * 科室名称
     */
    private String lowerName;
    /**
     * 用户名
     */
    private String userName;
}
