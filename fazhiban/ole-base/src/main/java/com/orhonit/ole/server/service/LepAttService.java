package com.orhonit.ole.server.service;

import com.orhonit.ole.server.model.LtcAtt;


/**
 * 日志service
 * 
 * @author caodw
 *
 *
 */
public interface LepAttService {

	void save(LtcAtt sysLogs);

	void save(Long userId, String module, Boolean flag, String remark);
}
