package com.orhonit.modules.generator.app.msg;



/**
 * 返回
 * @author YaoSC
 *
 * @param <T>
 */
public class ObjectRestResponse<T> {
	
	T data;
	
	@SuppressWarnings("rawtypes")
	public ObjectRestResponse data(T data) {
		this.setData(data);
		return this;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	

}
