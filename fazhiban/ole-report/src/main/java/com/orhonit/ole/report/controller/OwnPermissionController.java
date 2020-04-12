package com.orhonit.ole.report.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.orhonit.ole.sys.model.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.orhonit.ole.common.constants.SystemConstants;
import com.orhonit.ole.common.utils.JsonUtil;
import com.orhonit.ole.sys.model.Permission;
import com.orhonit.ole.sys.model.User;
import com.orhonit.ole.sys.service.PermissionService;
import com.orhonit.ole.sys.utils.UserUtil;

@RestController
@RequestMapping("/ownpermissions")
public class OwnPermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	@GetMapping("/current")
	public JSONArray permissionsCurrent() {
		List<Permission> list = UserUtil.getCurrentPermissions();
		if (list == null) {
			User user = UserUtil.getCurrentUser();
			list = this.permissionService.getOwnMenuByUserIdAndSysId(user.getId(), SystemConstants.PERMISSION_ID_REPORT.getCode().toString());
			UserUtil.setPermissionSession(list);
		}
		final List<Permission> permissions = list;
		return reset(permissions, SystemConstants.PERMISSION_ID_REPORT.getCode().toString());
	}
	
	private JSONArray reset(List<Permission> permissions, String sysId) {
		JSONArray array = new JSONArray();

		List<Permission> parents = permissions.stream().filter(p -> p.getParentId().equals(Long.valueOf(sysId)))
				.collect(Collectors.toList());
		parents.forEach(p -> {
			String string = JsonUtil.toJson(p);
			JSONObject parent = (JSONObject) JSONObject.parse(string);
			List<Permission> child = permissions.stream().filter(per -> per.getParentId().equals(p.getId()))
					.collect(Collectors.toList());
			parent.put("child", child);

			array.add(parent);
		});

		return array;
	}
}
