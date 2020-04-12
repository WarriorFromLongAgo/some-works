package com.orhonit.ole.enforce.service.complain;

import com.orhonit.ole.enforce.entity.WarnComplainEntity;

public interface ComplainService {
	
	/**
	 * 添加投诉
	 * @param params
	 * @return
	 */
	Boolean save(WarnComplainEntity warnComplainEntity);

}
