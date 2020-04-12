package com.orhonit.ole.server.service;

import com.orhonit.ole.server.model.LtcAtt;


/**
 * 日志service
 * 
 * @author caodw
 *
 *
 */
public interface LtcAttService {

	void saveLtc(LtcAtt ltcAtt);
    void updateLtc(LtcAtt ltcAtt);
	void delLtc(String id);
	LtcAtt getLtc(String username);
	LtcAtt getLtcById(Long userId);
}
