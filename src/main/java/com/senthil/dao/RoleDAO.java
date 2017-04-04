package com.senthil.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.senthil.model.Role;


@Repository
public class RoleDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Role findById(Long id) {

		String sql = "select  id,name,active,created_at,modified_at from roles where id= ?";
		Role role = jdbcTemplate.queryForObject(sql, new Object[] { id }, (rs, rowNo) -> {
			return convert(rs);
		});
		return role;

	}

	private Role convert(ResultSet rs) throws SQLException {
		Role role = new Role();
		role.setId(rs.getLong("id"));
		role.setName(rs.getString("name"));
		role.setActive(rs.getBoolean("active"));
		role.setCreatedDate(rs.getDate("created_at").toLocalDate());
		role.setModifiedDate(rs.getDate("modified_at").toLocalDate());
		return role;
	}

	public List<Role> list() {

		String sql = "select  id,name,active,created_at,modified_at from roles";

		List<Role> list = jdbcTemplate.query(sql, new Object[] {}, (rs, rowNum) -> {
			return convert(rs);
		});
		return list;
	}

	public void save(Role role) {

		String sql = "insert into roles ( name ) values ( ? )";

		int rows = jdbcTemplate.update(sql, role.getName());

		System.out.println("No of rows inserted:" + rows);
	}

	public void update(Role role) {

		String sql = "update roles set name=?, active= ? where id =? ";

		Integer rows = jdbcTemplate.update(sql, role.getName(), role.isActive(), role.getId());

		System.out.println("No of rows modified:" + rows);

	}

	public void delete(Long roleId) {

		String sql = "delete from roles where id = ?";
		int rows = jdbcTemplate.update(sql, roleId);
		System.out.println("No of rows deleted:" + rows);

	}

}
