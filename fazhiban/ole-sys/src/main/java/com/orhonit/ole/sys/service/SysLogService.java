package com.orhonit.ole.sys.service;

import com.orhonit.ole.sys.model.SysLogs;

/**
 * 日志service
 * 
 * @author caodw
 *
 *
 */
public interface SysLogService {

	void save(SysLogs sysLogs);

	void save(Long userId, String module, Boolean flag, String remark);
}
