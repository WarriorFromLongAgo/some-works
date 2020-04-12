package com.orhonit.ole.sys.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.orhonit.ole.common.constants.EventType;
import com.orhonit.ole.common.constants.UserConstants;
import com.orhonit.ole.common.event.AdminEvent;
import com.orhonit.ole.sys.dao.UserDao;
import com.orhonit.ole.sys.dto.PersonDTO;
import com.orhonit.ole.sys.dto.UserDto;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.model.User.Status;
import com.orhonit.ole.sys.service.UserService;
import com.orhonit.ole.sys.utils.UserUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "adminLogger")
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public User saveUser(UserDto userDto) {
		User user = userDto;
		user.setSalt(DigestUtils
				.md5Hex(UUID.randomUUID().toString() + System.currentTimeMillis() + UUID.randomUUID().toString()));
		user.setPassword(passwordEncoder(user.getPassword(), user.getSalt()));
		user.setStatus(Status.VALID);
		user.setHeadImgUrl(userDao.getImgByPersonId(userDto.getPerson_id()));
		userDao.save(user);
		saveUserRoles(user.getId(), userDto.getRoleIds());

		log.debug("新增用户{}", user.getUsername());
		return user;
	}

	private void saveUserRoles(Long userId, List<Long> roleIds) {
		if (roleIds != null) {
			userDao.deleteUserRole(userId);
			if (!CollectionUtils.isEmpty(roleIds)) {
				userDao.saveUserRoles(userId, roleIds);
			}
		}
	}

	@Override
	public String passwordEncoder(String credentials, String salt) {
		Object object = new SimpleHash("MD5", credentials, salt, UserConstants.HASH_ITERATIONS);
		return object.toString();
	}

	@Override
	public User getUser(String username) {
		return userDao.getUser(username);
	}

	@Override
	public void changePassword(String username, String oldPassword, String newPassword) {
		User u = userDao.getUser(username);
		if (u == null) {
			throw new IllegalArgumentException("用户不存在");
		}

		if (!u.getPassword().equals(passwordEncoder(oldPassword, u.getSalt()))) {
			throw new IllegalArgumentException("密码错误");
		}

		userDao.changePassword(u.getId(), passwordEncoder(newPassword, u.getSalt()));

		log.debug("修改{}的密码", username);
	}

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	@Transactional
	public User updateUser(UserDto userDto) {
		userDao.update(userDto);
		saveUserRoles(userDto.getId(), userDto.getRoleIds());
		updateUserSession(userDto.getId());

		applicationContext.publishEvent(new AdminEvent(UserUtil.getCurrentUser(), EventType.USER_CHANGE));

		return userDto;
	}

	private void updateUserSession(Long id) {
		User current = UserUtil.getCurrentUser();
		if (current.getId().equals(id)) {
			User user = userDao.getById(id);
			UserUtil.setUserSession(user);
		}
	}

	@Override
	@Transactional
	public void delUser(long id) {
		// TODO Auto-generated method stub
		userDao.deleteUserRole(id);
		userDao.delete(id);
		
		log.debug("删除角色id:{}", id);
	}

	@Override
	public User getUserById(Long userId) {
		return userDao.getById(userId);
	}

	@Override
	public User getUserByPersonId(String personId) {
		return this.userDao.getUserByPersonId(personId);
	}

	@Override
	public String appUpdatePassword(String username, String oldPassword, String newPassword) {
	
		User u = userDao.getUser(username);
		if (u == null) {
			return "用户不存在";
		}

		if (!u.getPassword().equals(passwordEncoder(oldPassword, u.getSalt()))) {
			return "密码错误";
		}
		try {
			userDao.changePassword(u.getId(), passwordEncoder(newPassword, u.getSalt()));
		} catch (Exception e) {
			e.printStackTrace();
			return "密码修改失败！";
		}
		

		log.debug("修改{}的密码", username);
		
		return "密码修改成功！";
	}
	
	@Override
	public List<Object> selRoleByPersonNum(String username) {
		return this.userDao.selRoleByPersonNum(username);
	}

	@Override
	public String appUpdatePasswordByCode(String username, String password) {
		User u = userDao.getUser(username);
		if (u == null) {
			return "用户不存在";
		}
		try {
			userDao.changePassword(u.getId(), passwordEncoder(password, u.getSalt()));
		} catch (Exception e) {
			e.printStackTrace();
			return "密码重置失败！";
		}
		log.debug("密码重置", username);
		return "密码重置成功！";
	}
}
