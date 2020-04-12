package com.orhonit.ole.enforce.dto.api;

import lombok.Data;

import java.util.Date;

/**
 * @Author : haoshuai
 * CreateDate : 18-1-12
 * CreateTime : 下午4:54
 */

@Data
public class MyReviewDTO {
    private String Id;
    private String businessKey;
    private Date createTime;
    private String assignName;
}
