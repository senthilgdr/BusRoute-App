package com.senthil.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.senthil.model.BoardingDetail;
import com.senthil.model.Route;
import com.senthil.model.RouteStats;

@Repository
public class BoardingDetailDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private BoardingDetail convert(ResultSet rs) throws SQLException {
		BoardingDetail bd = new BoardingDetail();
		bd.setId(rs.getLong("id"));
		bd.setName(rs.getString("name"));
		bd.setActive(rs.getBoolean("active"));
		Time time = rs.getTime("pickup_time");
		if (time != null) {
			bd.setPickUpTime(time.toLocalTime());
		}
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

		String sql = "update route_boarding_details set route_id = ? , name = ? ,active = ? ,pickup_time = ?  where id = ? ";

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
	
	public List<RouteStats> findRouteStats() {
		String sql = "SELECT route_id, r.name AS route_name,  MIN(pickup_time) start_time, MAX(pickup_time) finish_time, "
				+ " COUNT(*) no_of_boarding_points FROM route_boarding_details bd, routes r WHERE bd.route_id = r.id GROUP BY route_id, route_name";

		List<RouteStats> list = jdbcTemplate.query(sql, (rs, rowNum) -> {

			RouteStats r = new RouteStats();
			r.setRouteNo(rs.getLong("route_id"));
			r.setRouteName(rs.getString("route_name"));
			r.setStartTime(rs.getTime("start_time").toLocalTime());
			r.setFinishTime(rs.getTime("finish_time").toLocalTime());
			r.setNoOfBoardingPoints(rs.getInt("no_of_boarding_points"));
			return r;

		});

		return list;

	}
}
