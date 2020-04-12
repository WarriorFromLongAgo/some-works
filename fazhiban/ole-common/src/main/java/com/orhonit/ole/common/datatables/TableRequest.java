package com.orhonit.ole.common.datatables;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页查询参数
 * 
 * @author caodw
 *
 */
@Getter
@Setter
public class TableRequest implements Serializable {

	private static final long serialVersionUID = 5159229192412391644L;

	private Integer start;
	private Integer length;
	private Map<String, Object> params;
}
