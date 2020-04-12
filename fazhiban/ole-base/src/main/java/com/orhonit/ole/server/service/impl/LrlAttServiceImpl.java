package com.orhonit.ole.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.orhonit.ole.server.dao.LtcAttDao;
import com.orhonit.ole.server.model.LtcAtt;
import com.orhonit.ole.server.service.LrlAttService;
import com.orhonit.ole.server.service.LtcAttService;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.utils.UserUtil;

@Async
@Service
public class LrlAttServiceImpl implements LrlAttService {

	@Autowired
	private LtcAttDao lawAttDao;

	@Override
	public void save(LtcAtt sysLogs) {
		User user = UserUtil.getCurrentUser();
		if (user == null || user.getId() == null) {
			return;
		}

		//sysLogs.setUser(user);
		lawAttDao.save(sysLogs);
	}

	@Override
	public void save(Long userId, String module, Boolean flag, String remark) {
		LtcAtt lawAtt = new LtcAtt();
//		sysLogs.setFlag(flag);
//		sysLogs.setModule(module);
//		sysLogs.setRemark(remark);
//
//		User user = new User();
//		user.setId(userId);
//		sysLogs.setUser(user);

		lawAttDao.save(lawAtt);

	}
}
