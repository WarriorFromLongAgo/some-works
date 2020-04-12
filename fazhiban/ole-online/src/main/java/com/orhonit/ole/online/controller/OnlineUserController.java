package com.orhonit.ole.online.controller;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.orhonit.ole.common.datatables.TableRequest;
import com.orhonit.ole.common.datatables.TableRequestHandler;
import com.orhonit.ole.common.datatables.TableResponse;
import com.orhonit.ole.common.datatables.TableRequestHandler.CountHandler;
import com.orhonit.ole.common.datatables.TableRequestHandler.ListHandler;
import com.orhonit.ole.online.handler.UserLoginSocketHandler;
import com.orhonit.ole.sys.model.User;

import io.swagger.annotations.ApiOperation;

/**
 * 在线用户接口
 * 
 * @author caodw
 *
 */
@RestController
@RequestMapping("/online/users")
public class OnlineUserController {

	/**
	 * 在线用户
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value="list")
	@ApiOperation(value = "在线用户列表")
	@RequiresPermissions("online:user:query")
	public TableResponse<User> listUsers(TableRequest request) {
		ConcurrentMap<Long, User> onlineUsers = UserLoginSocketHandler.onlineUsers;
		int size = onlineUsers.size();

		TableResponse<User> tableResponse = TableRequestHandler.<User>builder().countHandler(new CountHandler() {

			@Override
			public int count(TableRequest request) {
				return size;
			}
		}).listHandler(new ListHandler<User>() {

			@Override
			public List<User> list(TableRequest request) {
				return Lists.newArrayList(onlineUsers.values());
			}
		}).build().handle(request);

		tableResponse.setRecordsTotal(size);

		return tableResponse;
	}
}
