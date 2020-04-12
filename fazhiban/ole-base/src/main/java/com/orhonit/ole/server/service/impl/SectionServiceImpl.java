package com.orhonit.ole.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orhonit.ole.server.dao.LesDao;
import com.orhonit.ole.server.model.Lesection;
import com.orhonit.ole.server.service.SectionService;
import com.orhonit.ole.sys.dto.UserDto;
import com.orhonit.ole.sys.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "adminLogger")
@Service
public class SectionServiceImpl implements SectionService {

	@Autowired
	private LesDao lesDao;

//	@Override
//	@Transactional
//	public User saveSection(UserDto userDto) {
//		User user = userDto;
//		user.setSalt(DigestUtils
//				.md5Hex(UUID.randomUUID().toString() + System.currentTimeMillis() + UUID.randomUUID().toString()));
//		user.setStatus(Status.VALID);
//		userDao.save(user);
//		saveUserRoles(user.getId(), userDto.getRoleIds());
//
//		log.debug("新增用户{}", user.getUsername());
//		return user;
//	}
//
//	private void saveUserRoles(Long userId, List<Long> roleIds) {
//		if (roleIds != null) {
//			userDao.deleteUserRole(userId);
//			if (!CollectionUtils.isEmpty(roleIds)) {
//				userDao.saveUserRoles(userId, roleIds);
//			}
//		}
//	}
//
//	@Override
//	public User getSection(String username) {
//		return userDao.getUser(username);
//	}
//
//	@Autowired
//	private ApplicationContext applicationContext;
//
//	@Override
//	@Transactional
//	public User updateSection(UserDto userDto) {
//		userDao.update(userDto);
//		saveUserRoles(userDto.getId(), userDto.getRoleIds());
//		updateUserSession(userDto.getId());
//
//		applicationContext.publishEvent(new AdminEvent(UserUtil.getCurrentUser(), EventType.USER_CHANGE));
//
//		return userDto;
//	}
//
//	private void updateUserSession(Long id) {
//		User current = UserUtil.getCurrentUser();
//		if (current.getId().equals(id)) {
//			User user = userDao.getById(id);
//			UserUtil.setUserSession(user);
//		}
//	}

	@Override
	@Transactional
	public void delSection(long id) {
		// TODO Auto-generated method stub
		lesDao.delete(id);
	}
//
//	@Override
//	public User getSectionById(Long userId) {
//		// TODO Auto-generated method stub
//		return userDao.getById(userId);
//	}

	@Override
	public int saveSection(Lesection lesection) {
		int temp=lesDao.save(lesection);
		return temp;
	}

	@Override
	public Lesection updateSection(Lesection lesection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lesection getSection(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lesection getSectionById(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
