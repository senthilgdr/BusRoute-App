
package com.senthil.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.senthil.model.BoardingDetail;
import com.senthil.model.Route;
import com.senthil.model.User;
import com.senthil.model.UserBoardingDetail;

@Repository
public class UserBoardingDetailDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private UserBoardingDetail convert(ResultSet rs) throws SQLException {
		UserBoardingDetail ubd = new UserBoardingDetail();
		ubd.setId(rs.getLong("id"));
		ubd.setActive(rs.getBoolean("active"));
		
		User user = new User();
		user.setId(rs.getLong("id"));
		user.setName(rs.getString("name"));

		BoardingDetail bd = new BoardingDetail();
		bd.setId(rs.getLong("id"));
		bd.setName(rs.getString("name"));
		bd.setPickUpTime(rs.getTime("pickup_time").toLocalTime());

		Route r = new Route();
		r.setId(rs.getLong("id"));
		r.setName(rs.getString("name"));
		bd.setRoute(r);

		ubd.setUser(user);
		ubd.setBoardingDetail(bd);

		return ubd;
	}

	public List<UserBoardingDetail> list() {

		String sql = "select ubd.id, user_id, u.name as user_name, boarding_id, bd.name as boarding_name, bd.pickup_time, ubd.active, r.id as route_no, r.name as route_name from user_boarding_details ubd, route_boarding_details bd, routes r , user_accounts u  where ubd.user_id = u.id and ubd.boarding_id =  bd.id and bd.route_id = r.id ";

		List<UserBoardingDetail> list = jdbcTemplate.query(sql, new Object[] {}, (rs, rowNum) -> {
			return convert(rs);
		});
		return list;
	}

	public List<UserBoardingDetail> findByUserId(Long userId) {

		String sql = "select ubd.id, user_id, u.name as user_name, boarding_id, bd.name as boarding_name, bd.pickup_time, ubd.active, r.id as route_no, r.name as route_name from user_boarding_details ubd, route_boarding_details bd, routes r , user_accounts u  where ubd.user_id = u.id and ubd.boarding_id =  bd.id and bd.route_id = r.id  and ubd.user_id = ?";

		List<UserBoardingDetail> list = jdbcTemplate.query(sql, new Object[] { userId }, (rs, rowNum) -> {
			return convert(rs);
		});
		return list;
	}

	public UserBoardingDetail findById(Long id) {

		String sql = "select ubd.id, user_id, u.name as user_name, boarding_id, bd.name as boarding_name, bd.pickup_time, ubd.active, r.id as route_no, r.name as route_name from user_boarding_details ubd, route_boarding_details bd, routes r , user_accounts u  where ubd.user_id = u.id and ubd.boarding_id =  bd.id and bd.route_id = r.id  and ubd.id = ?";

		UserBoardingDetail ubd = jdbcTemplate.queryForObject(sql, new Object[] { id }, (rs, rowNum) -> {
			return convert(rs);
		});
		return ubd;
	}

	public void update(UserBoardingDetail bd) {

		String sql = "update user_boarding_details set user_id = ? , boarding_id = ? , active = ?  from user_boarding_details where id = ? ";

		Object[] params = new Object[] { bd.getUser().getId(), bd.getBoardingDetail().getId(), bd.isActive(),
				bd.getId() };
		int rows = jdbcTemplate.update(sql, params);
		System.out.println("No of rows updated" + rows);
	}

	public void delete(Long id) {

		String sql = "delete from user_boarding_details where id = ? ";

		Object[] params = new Object[] { id };
		int rows = jdbcTemplate.update(sql, params);
		System.out.println("No of rows deleted" + rows);
	}

	public void save(UserBoardingDetail bd) {

		String sql = "insert into user_boarding_details ( user_id,  boarding_id, active ) values ( ?, ?, ? , ?)  ";
		Object[] params = new Object[] { bd.getUser().getId(), bd.getBoardingDetail().getId(), bd.isActive() };
		int rows = jdbcTemplate.update(sql, params);
		System.out.println("No of rows inserted" + rows);
	}

	public List<UserBoardingDetail> findByRouteNo(Long routeNo) {

		String sql = "select ubd.id, user_id, u.name as user_name, boarding_id, bd.name as boarding_name, bd.pickup_time, ubd.active, r.id as route_no, r.name as route_name from user_boarding_details ubd, route_boarding_details bd, routes r , user_accounts u  where ubd.user_id = u.id and ubd.boarding_id =  bd.id and bd.route_id = r.id  and bd.route_id = ?";

		List<UserBoardingDetail> list = jdbcTemplate.query(sql, new Object[] { routeNo }, (rs, rowNum) -> {
			return convert(rs);
		});
		return list;
	}

	public List<Map<String, Object>> findBoardingPointStats() {

		String sql = "SELECT boarding_id, COUNT(*) no_of_students FROM user_boarding_details ubd where active=1 GROUP BY boarding_id";

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;

	}

	public List<Map<String, Object>> findByRouteStats() {

		String sql = "SELECT route_id, COUNT(*) no_of_students FROM user_boarding_details ubd , route_boarding_details rbd WHERE  ubd.boarding_id = rbd.id AND  ubd.active=1 AND rbd.active = 1  GROUP BY route_id";

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;

	}

}
