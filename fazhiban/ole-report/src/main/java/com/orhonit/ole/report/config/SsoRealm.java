package com.orhonit.ole.report.config;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.StringUtils;

import com.orhonit.ole.common.constants.SystemConstants;
import com.orhonit.ole.common.utils.SpringContext;
import com.orhonit.ole.report.token.UserIdToken;
import com.orhonit.ole.sys.dao.PermissionDao;
import com.orhonit.ole.sys.dao.RoleDao;
import com.orhonit.ole.sys.model.Permission;
import com.orhonit.ole.sys.model.Role;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.model.User.Status;
import com.orhonit.ole.sys.service.PermissionService;
import com.orhonit.ole.sys.service.UserService;
import com.orhonit.ole.sys.utils.UserUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "adminLogger")
public class SsoRealm extends AuthorizingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		User user = null;
		SimpleAuthenticationInfo authenticationInfo = null;
		UserService userService = SpringContext.getBean(UserService.class);
		PermissionService permissionService=SpringContext.getBean(PermissionService.class);
		if ( token instanceof UserIdToken ) {
			UserIdToken userIdToken = (UserIdToken) token;
			user = userService.getUserById(userIdToken.getUserId());
			if (user == null) {
				throw new UnknownAccountException("用户名不存在");
			}
			authenticationInfo = new SimpleAuthenticationInfo(user,null, getName());
			
		} else {
		
			UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
	
			String username = usernamePasswordToken.getUsername();
			
			user = userService.getUser(username);
			if (user == null) {
				throw new UnknownAccountException("用户名不存在");
			}
	
			if (!user.getPassword()
					.equals(userService.passwordEncoder(new String(usernamePasswordToken.getPassword()), user.getSalt()))) {
				throw new IncorrectCredentialsException("密码错误");
			}
	
			if (user.getStatus() != Status.VALID) {
				throw new IncorrectCredentialsException("无效状态，请联系管理员");
			}
			
			authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(),
					ByteSource.Util.bytes(user.getSalt()), getName());
		
		}
		UserUtil.setUserSession(user);
		//设置菜单
		List<Permission>   list = permissionService.getOwnMenuByUserIdAndSysId(user.getId(), SystemConstants.PERMISSION_ID_REPORT.getCode().toString());
		UserUtil.setPermissionSession(list);
		return authenticationInfo;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		log.debug("权限配置");

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		User user = UserUtil.getCurrentUser();
		List<Role> roles = SpringContext.getBean(RoleDao.class).listByUserId(user.getId());
		Set<String> roleNames = roles.stream().map(Role::getName).collect(Collectors.toSet());
		authorizationInfo.setRoles(roleNames);
		List<Permission> permissionList = SpringContext.getBean(PermissionDao.class).listModuleMenuByUserId(user.getId(), SystemConstants.PERMISSION_ID_REPORT.getCode().toString());
		UserUtil.setPermissionSession(permissionList);
		Set<String> permissions = permissionList.stream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
				.map(Permission::getPermission).collect(Collectors.toSet());
		authorizationInfo.setStringPermissions(permissions);

		return authorizationInfo;
	}

	/**
	 * 重写缓存key，否则集群下session共享时，会重复执行doGetAuthorizationInfo权限配置
	 */
	@Override
	protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
		SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) principals;
		Object object = principalCollection.getPrimaryPrincipal();

		if (object instanceof User) {
			User user = (User) object;

			return "authorization:cache:key:users:" + user.getId();
		}

		return super.getAuthorizationCacheKey(principals);
	}
	
	@Override
	protected void assertCredentialsMatch(AuthenticationToken authcToken,  
	        AuthenticationInfo info) throws AuthenticationException {  
		if ( authcToken instanceof UserIdToken) {
			return;
		}
		super.assertCredentialsMatch(authcToken, info);  
	}

}
