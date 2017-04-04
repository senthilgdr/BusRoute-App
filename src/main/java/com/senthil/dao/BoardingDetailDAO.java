package com.senthil.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.senthil.model.BoardingDetail;
import com.senthil.model.Route;

@Repository
public class BoardingDetailDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private BoardingDetail convert(ResultSet rs) throws SQLException {
		BoardingDetail bd = new BoardingDetail();
		bd.setId(rs.getLong("id"));
		bd.setName(rs.getString("name"));
		bd.setActive(rs.getBoolean("active"));
		bd.setPickUpTime(rs.getTime("pickup_time").toLocalTime());

		Route r = new Route();
		r.setId(rs.getLong("route_id"));
		r.setName(rs.getString("route_name"));

		bd.setRoute(r);

		return bd;
	}

	public List<BoardingDetail> list() {

		String sql = "select bd.id,route_id, r.name as route_name, bd.name,pickup_time, bd.active from route_boarding_details bd, routes r where bd.route_id = r.id ";

		List<BoardingDetail> list = jdbcTemplate.query(sql, new Object[] {}, (rs, rowNum) -> {
			return convert(rs);
		});
		return list;
	}

	public List<BoardingDetail> findByRouteNo(Long routeNo) {

		String sql = "select bd.id,route_id, r.name as route_name, bd.name,pickup_time,bd.active from route_boarding_details bd, routes r where bd.route_id = r.id  and bd.route_id = ?";

		List<BoardingDetail> list = jdbcTemplate.query(sql, new Object[] { routeNo }, (rs, rowNum) -> {
			return convert(rs);
		});
		return list;
	}

	public BoardingDetail findById(Long id) {

		String sql = "select bd.id,route_id, r.name as route_name, bd.name,pickup_time,bd.active from route_boarding_details bd, routes r where bd.route_id = r.id  and bd.id = ?";

		BoardingDetail BoardingDetail = jdbcTemplate.queryForObject(sql, new Object[] { id }, (rs, rowNum) -> {
			return convert(rs);
		});
		return BoardingDetail;
	}

	public void update(BoardingDetail bd) {

		String sql = "update route_boarding_details set route_id = ? , name = ? ,active = ? ,pickup_time = ?  from route_boarding_details where id = ? ";

		Object[] params = new Object[] { bd.getRoute().getId(), bd.getName(), bd.isActive(), bd.getPickUpTime(),
				bd.getId() };
		int rows = jdbcTemplate.update(sql, params);
		System.out.println("No of rows updated" + rows);
	}

	public void delete(Long id) {

		String sql = "delete from route_boarding_details where id = ? ";

		Object[] params = new Object[] { id };
		int rows = jdbcTemplate.update(sql, params);
		System.out.println("No of rows deleted" + rows);
	}

	public void save(BoardingDetail bd) {

		String sql = "insert into route_boarding_details ( route_id, name, pickup_time, active ) values ( ?, ?, ? , ?)  ";
		Object[] params = new Object[] { bd.getRoute().getId(), bd.getName(), bd.getPickUpTime(), bd.isActive() };
		int rows = jdbcTemplate.update(sql, params);
		System.out.println("No of rows inserted" + rows);
	}

	
}
