package com.orhonit.ole.sys.service;

import java.util.List;
import java.util.Map;

import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.dto.UserDto;
import com.orhonit.ole.sys.model.User;

public interface UserService {

	User saveUser(UserDto userDto);
	
	User updateUser(UserDto userDto);
	
	void delUser(long id);

	String passwordEncoder(String credentials, String salt);

	User getUser(String username);

	void changePassword(String username, String oldPassword, String newPassword);
	
	User getUserById(Long userId);
	
	User getUserByPersonId(String personId);
	
	String appUpdatePassword(String username, String oldPassword, String newPassword);
	
	List<Object> selRoleByPersonNum(String username);

	String appUpdatePasswordByCode(String username, String password);
}
