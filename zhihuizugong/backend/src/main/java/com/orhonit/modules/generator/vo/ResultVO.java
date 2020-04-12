package com.orhonit.modules.generator.vo;

import lombok.Data;

/**
 * http请求返回的最外层对象
 * @auther:Agr
 */
@Data
public class ResultVO<T> {

    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 返回对象. */
    private T data;
}
