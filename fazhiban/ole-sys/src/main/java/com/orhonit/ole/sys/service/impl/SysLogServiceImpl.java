package com.orhonit.ole.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.orhonit.ole.sys.dao.SysLogsDao;
import com.orhonit.ole.sys.model.SysLogs;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.SysLogService;
import com.orhonit.ole.sys.utils.UserUtil;

@Async
@Service
public class SysLogServiceImpl implements SysLogService {

	@Autowired
	private SysLogsDao sysLogsDao;

	@Override
	public void save(SysLogs sysLogs) {
		User user = UserUtil.getCurrentUser();
		if (user == null || user.getId() == null) {
			return;
		}

		sysLogs.setUser(user);
		sysLogsDao.save(sysLogs);
	}

	@Override
	public void save(Long userId, String module, Boolean flag, String remark) {
		SysLogs sysLogs = new SysLogs();
		sysLogs.setFlag(flag);
		sysLogs.setModule(module);
		sysLogs.setRemark(remark);

		User user = new User();
		user.setId(userId);
		sysLogs.setUser(user);

		sysLogsDao.save(sysLogs);

	}
}
