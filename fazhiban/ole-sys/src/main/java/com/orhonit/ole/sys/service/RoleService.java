package com.orhonit.ole.sys.service;

import com.orhonit.ole.sys.dto.RoleDto;
import com.orhonit.ole.sys.model.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {

	void saveRole(RoleDto roleDto);

	void deleteRole(Long id);

	List<Role> list(Map<String, Object> params,Integer start,Integer length);
	
	List<Role> listByUserId(Long userId);
}
