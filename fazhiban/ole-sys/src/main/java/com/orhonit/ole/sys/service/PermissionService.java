package com.orhonit.ole.sys.service;

import java.util.List;

import com.orhonit.ole.sys.model.Permission;

public interface PermissionService {

	void save(Permission permission);

	void update(Permission permission);

	void delete(Long id);

	List<Permission> getAllowedAccessSysByUser(Long id, Integer code);
	
	List<Permission> getOwnMenuByUserIdAndSysId(Long userId, String sysId);
}
