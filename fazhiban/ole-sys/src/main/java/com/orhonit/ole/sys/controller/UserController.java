package com.orhonit.ole.sys.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orhonit.ole.common.annotation.LogAnnotation;
import com.orhonit.ole.common.constants.ResultCode;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.model.Result;
import com.orhonit.ole.common.utils.ResultUtil;
import com.orhonit.ole.sys.dao.UserDao;
import com.orhonit.ole.sys.dto.UserDto;
import com.orhonit.ole.sys.model.Person;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.UserService;
import com.orhonit.ole.sys.utils.UserUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户相关接口
 * 
 * @author caodw
 *
 */
@Slf4j(topic = "adminLogger")
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userDao;

	@LogAnnotation
	@PostMapping
	@ApiOperation(value = "保存用户")
	public User saveUser(@RequestBody UserDto userDto) {
		User u = userService.getUser(userDto.getUsername());
		if (u != null) {
			throw new IllegalArgumentException(userDto.getUsername() + "已存在");
		}

		return userService.saveUser(userDto);
	}

	@LogAnnotation
	@PutMapping
	@ApiOperation(value = "修改用户")
	public User updateUser(@RequestBody UserDto userDto) {
		return userService.updateUser(userDto);
	}

	@LogAnnotation
	@PutMapping(params = "headImgUrl")
	@ApiOperation(value = "修改头像")
	public void updateHeadImgUrl(String headImgUrl) {
		User user = UserUtil.getCurrentUser();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		userDto.setHeadImgUrl(headImgUrl);

		userService.updateUser(userDto);
		log.debug("{}修改了头像", user.getUsername());
	}

	@LogAnnotation
	@PutMapping("/{username}")
	@ApiOperation(value = "修改密码")
	@RequiresPermissions("sys:user:password")
	public void changePassword(@PathVariable String username, String oldPassword, String newPassword) {
		userService.changePassword(username, oldPassword, newPassword);
	}
	
	@LogAnnotation
	@GetMapping("/changePass")
	@ApiOperation(value = "修改密码")
	public Result<Object> changePass(String oldPassword, String newPassword) {
		try {
			User user = UserUtil.getCurrentUser();
			userService.changePassword(user.getUsername(), oldPassword, newPassword);
			return ResultUtil.toResponseWithData(ResultCode.SUCCESS, "密码修改成功,请使用新密码重新登录！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.toResponseWithData(ResultCode.ERROR, "密码修改失败");
		}
	}

	@GetMapping(value="/list")
	@ApiOperation(value = "用户列表")
	public TableResponse<User> listUsers(TableRequest request) {
		return TableRequestHandler.<User> builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return userDao.count(request.getParams());
			}
		}).listHandler(new ListHandler<User>() {

			@Override
			public List<User> list(TableRequest request) {
				List<User> list = userDao.list(request.getParams(), request.getStart(), request.getLength());
				return list;
			}
		}).build().handle(request);
	}

	@ApiOperation(value = "当前登录用户")
	@GetMapping("/current")
	public User currentUser() {
		return UserUtil.getCurrentUser();
	}
	
	@ApiOperation(value = "当前登录用户的人员信息")
	@GetMapping("/current/person")
	public Person currentPerson() {
		if(UserUtil.getCurrentUser().getPerson_id() != null){
			return userDao.getPersonById(UserUtil.getCurrentUser().getPerson_id());
		}
		return null;
	}

	@ApiOperation(value = "根据用户id获取用户")
	@GetMapping("/{id}")
	public User user(@PathVariable Long id) {
		System.out.println(id);
		System.out.println(userDao.getById(id));
		return userDao.getById(id);
	}
	
	@LogAnnotation
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除用户")
	@RequiresPermissions(value = { "sys:user:del" })
	public void delete(@PathVariable Long id) {
		userService.delUser(id);
	}

}
