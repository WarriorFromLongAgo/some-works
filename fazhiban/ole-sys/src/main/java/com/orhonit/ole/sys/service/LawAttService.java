package com.orhonit.ole.sys.service;

import com.orhonit.ole.sys.model.LawAtt;

/**
 * 日志service
 * 
 * @author caodw
 *
 *
 */
public interface LawAttService {

	void save(LawAtt sysLogs);

	void save(Long userId, String module, Boolean flag, String remark);
}
