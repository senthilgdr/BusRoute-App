package com.senthil.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.senthil.model.Route;

@Repository
public class RouteDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Route convert(ResultSet rs) throws SQLException {
		Route route = new Route();
		route.setId(rs.getLong("id"));
		route.setName(rs.getString("name"));
		route.setTrip(rs.getString("trip"));
		route.setActive(rs.getBoolean("active"));
		return route;
	}

	public List<Route> list() {

		String sql = "select id,name,active,trip from routes";

		List<Route> list = jdbcTemplate.query(sql, new Object[] {}, (rs, rowNum) -> {
			return convert(rs);
		});
		return list;
	}

	public Route findById(Long id) {

		String sql = "select id,name,active,trip from routes where id = ? ";

		Route route = jdbcTemplate.queryForObject(sql, new Object[] { id }, (rs, rowNum) -> {
			return convert(rs);
		});
		return route;
	}

	public void update(Route route) {

		System.out.println(route);
		String sql = "UPDATE routes SET NAME = ? ,active = ? ,trip =? WHERE id=?";

		Object[] params = new Object[] { route.getName(), route.isActive(), route.getTrip(), route.getId() };
		int rows = jdbcTemplate.update(sql,params);
		System.out.println("No of rows updated" + rows);
	}

	public void delete(Long id) {

		String sql = "delete from routes where id = ? ";

		Object[] params = new Object[] { id };
		int rows = jdbcTemplate.update(sql, params);
		System.out.println("No of rows deleted" + rows);
	}

	public void save(Route route) {

		String sql = "insert into routes ( name, active, trip ) values ( ?, ?, ?)  ";
		Object[] params = new Object[] { route.getName(), route.isActive(), route.getTrip() };
		int rows = jdbcTemplate.update(sql, params);
		System.out.println("No of rows inserted" + rows);
	}

}
