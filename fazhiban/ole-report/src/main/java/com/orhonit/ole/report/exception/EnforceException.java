package com.orhonit.ole.report.exception;

import com.orhonit.ole.common.constants.ResultCode;

import lombok.Getter;

@Getter
public class EnforceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer code;

    public EnforceException(ResultCode resultEnum) {
        super(resultEnum.getMsg());

        this.code = resultEnum.getCode();
    }

    public EnforceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
