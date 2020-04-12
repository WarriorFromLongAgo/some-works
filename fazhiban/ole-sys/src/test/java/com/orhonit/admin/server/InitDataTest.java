package com.orhonit.admin.server;

import java.lang.reflect.Field;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;
import com.orhonit.ole.sys.model.Permission;

public class InitDataTest {

	@Test
	public void val() {
		Class<Permission> cla = Permission.class;
		Class<?> sup = cla.getSuperclass();

		outField(sup);
		outField(cla);
	}

	private static final Set<String> SET = Sets.newHashSet("serialVersionUID", "createTime", "updateTime");

	private void outField(Class<?> cla) {
		Field[] fields = cla.getDeclaredFields();
		for (Field f : fields) {
			String name = f.getName();
			if (!SET.contains(name)) {
				String val = "$(\"#" + name + "\").val(data." + name + ");";
				System.out.println(val);
			}
		}
	}
}
