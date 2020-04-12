package com.orhonit.ole.tts.exception;

import com.orhonit.ole.common.constants.ResultCode;

import lombok.Getter;

@Getter
public class TTSException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer code;

    public TTSException(ResultCode resultEnum) {
        super(resultEnum.getMsg());

        this.code = resultEnum.getCode();
    }

    public TTSException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
