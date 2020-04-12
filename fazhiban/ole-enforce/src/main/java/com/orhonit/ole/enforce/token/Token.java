package com.orhonit.ole.enforce.token;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Restful方式登陆token
 * 
 * @author caodw
 *
 *
 */
@Getter
@Setter
@Builder
public class Token implements Serializable {

	private static final long serialVersionUID = 4043470238789599973L;

	private String token;

	/**
	 * 到期时间
	 */
	private Date expireTime;

	/**
	 * token创建时间
	 */
	private Date createTime = new Date();

}
