package com.orhonit.ole.common.utils;

import java.util.List;

import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.model.Result;

/**
 * Controller的返回
 * @author ebusu
 *
 */
public class ResultUtil 
{
	/**
	 * 需要指定ResultCode以及data并返回给客户端
	 * @param resultCode
	 * @param data
	 * @return
	 */
	public static <T> Result<T> toResponseWithData(ResultCode resultCode, T data) {
		Result<T> result = new Result<>();
		result.setCode(resultCode.getCode());
		result.setMsg(resultCode.getMsg());
		result.setData(data);
		return result;
	}
	
	/**
	 * 返回ResultCode指定的枚举
	 * @param resultCode
	 * @return
	 */
	public static <T> Result<T> toResponse(ResultCode resultCode) {
		Result<T> result = new Result<>();
		result.setCode(resultCode.getCode());
		result.setMsg(resultCode.getMsg());
		return ResultUtil.toResponseWithData(resultCode, null);
	}
	
	/**
	 * 返回ResultCode指定的枚举
	 * @param resultCode
	 * @return
	 */
	public static <T> Result<T> toResponseWithMsg(Integer resultCode, String msg) {
		Result<T> result = new Result<>();
		result.setCode(resultCode);
		result.setMsg(msg);
		return result;
	}
	
	/**
	 * 返回给客户端的只是成功标识，没有数据
	 * @return
	 */
	public static <T> Result<T> success() {
		return ResultUtil.toResponse(ResultCode.SUCCESS);
	}
	
	/**
	 * 当给客户端的错误提示msg不确定时增加errorMsg
	 * @param resultCode
	 * @return
	 */
	public static <T> Result<T> toResponseWithMsg(ResultCode resultCode, List<String> errorMsgs) {
		Result<T> result = new Result<>();
		result.setCode(resultCode.getCode());
		result.setMsg(resultCode.getMsg());
		if ( !errorMsgs.isEmpty()) {
			StringBuilder stringBuilder = new StringBuilder(result.getMsg());
			stringBuilder.append("[");
			for (String errorMsg : errorMsgs) {
				stringBuilder.append(errorMsg);
			}
			stringBuilder.append("]");
			result.setMsg(stringBuilder.toString());
		}
		return result;
	}
}
