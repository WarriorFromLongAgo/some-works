package com.orhonit.ole.common.model;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页对象
 * 
 * @author caodw
 *
 */
@Getter
@Setter
@Builder
public class Page<T> implements Serializable {

	private static final long serialVersionUID = 2370307087717576892L;

	private Integer total;
	private List<T> list;

}