package com.orhonit.ole.sys.utils;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.orhonit.ole.common.constants.UserConstants;
import com.orhonit.ole.sys.model.Permission;
import com.orhonit.ole.sys.model.User;

public class UserUtil {

	public static User getCurrentUser() {
		User user = (User) getSession().getAttribute(UserConstants.LOGIN_USER);

		return user;
	}

	public static void setUserSession(User user) {
		getSession().setAttribute(UserConstants.LOGIN_USER, user);
	}

	@SuppressWarnings("unchecked")
	public static List<Permission> getCurrentPermissions() {
		List<Permission> list = (List<Permission>) getSession().getAttribute(UserConstants.USER_PERMISSIONS);

		return list;
	}

	public static void setPermissionSession(List<Permission> list) {
		getSession().setAttribute(UserConstants.USER_PERMISSIONS, list);
	}

	public static Session getSession() {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();

		return session;
	}
}
