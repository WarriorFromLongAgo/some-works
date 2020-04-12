package com.orhonit.ole.sys.dto;

import java.util.List;

import com.orhonit.ole.sys.model.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto extends Role {

	private static final long serialVersionUID = 4218495592167610193L;

	private List<Long> permissionIds;
}
