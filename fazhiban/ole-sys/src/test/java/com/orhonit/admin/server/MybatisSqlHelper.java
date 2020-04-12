package com.orhonit.admin.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import com.orhonit.ole.common.utils.StrUtil;

import lombok.Data;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisSqlHelper {

	private String table_name = "sys_permission";

	@Test
	public void insert() {
		List<SqlBean> sqlBeans = jdbcTemplate.query("show columns from " + table_name, rowMapper);
		StringBuilder builder = new StringBuilder("insert into ").append(table_name);
		StringBuilder columns = new StringBuilder();
		StringBuilder values = new StringBuilder();
		StringBuilder updateSet = new StringBuilder();
		sqlBeans.forEach(s -> {
			if (!"id".equals(s.getField())) {
				columns.append(s.getField() + ", ");

				if ("datetime".equals(s.getType())) {
					values.append("now(), ");
				} else {
					values.append("#{" + s.getField() + "}, ");
				}

				updateSet.append(s.getField() + " = #{" + s.getField() + "}, ");
			}
		});

		builder.append("(").append(StringUtils.substringBeforeLast(columns.toString(), ",")).append(") values(");
		builder.append(StringUtils.substringBeforeLast(values.toString(), ",")).append(")");
		String insert = "@Insert(\"" + builder + "\")";
		System.out.println(insert);

		String beanName = getBeanName();
		String paramName = StrUtil.str2hump(beanName);
		insert = "int save(" + beanName + " " + paramName + ");";
		System.out.println(insert);

		String update = "@Update(\"update %s t set %s where t.id = #{id}\")";
		update = String.format(update, table_name, StringUtils.substringBeforeLast(updateSet.toString(), ","));
		System.out.println(update);

		update = "int update(" + beanName + " " + paramName + ");";
		System.out.println(update);

		String select = "@Select(\"select * from %s t where t.id = #{id}\")";
		System.out.println(String.format(select, table_name));

		select = beanName + " getById(Long id);";
		System.out.println(select);

		String delete = "@Delete(\"delete from %s where id = #{id}\")";
		delete = String.format(delete, table_name);
		System.out.println(delete);

		delete = "int delete(Long id);";
		System.out.println(delete);
	}

	public String getBeanName() {
		String name = StrUtil.str2hump(table_name);
		String firstChar = name.substring(0, 1);
		name = name.replaceFirst(firstChar, firstChar.toUpperCase());

		return name;
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	RowMapper<SqlBean> rowMapper = new RowMapper<SqlBean>() {

		@Override
		public SqlBean mapRow(ResultSet rs, int paramInt) throws SQLException {
			SqlBean bean = new SqlBean();
			bean.setField(rs.getString("Field"));
			bean.setType(rs.getString("Type"));
			bean.setNull(rs.getString("Null"));
			bean.setKey(rs.getString("Key"));
			bean.setDefault(rs.getString("Default"));
			bean.setExtra(rs.getString("Extra"));

			return bean;
		}
	};

	@Data
	public class SqlBean {

		private String field;
		private String type;
		private String Null;
		private String key;
		private String Default;
		private String extra;
	}
}
