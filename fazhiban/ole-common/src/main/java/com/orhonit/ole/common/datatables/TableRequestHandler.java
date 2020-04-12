package com.orhonit.ole.common.datatables;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;

/**
 * 分页查询处理器
 * 
 * @author caodw
 *
 */
@Builder
public class TableRequestHandler<T> {
	private CountHandler countHandler;
	private ListHandler<T> listHandler;

	public TableResponse<T> handle(TableRequest dtRequest) {
		int count = 0;
		List<T> list = null;

		count = this.countHandler.count(dtRequest);
		if (count > 0) {
			list = this.listHandler.list(dtRequest);
		}

		if (list == null) {
			list = new ArrayList<T>();
		}

		return TableResponse.<T>builder().recordsTotal(count).recordsFiltered(count).data(list).build();
	}

	public interface ListHandler<T> {
		List<T> list(TableRequest request);
	}

	public interface CountHandler {
		int count(TableRequest request);
	}
}