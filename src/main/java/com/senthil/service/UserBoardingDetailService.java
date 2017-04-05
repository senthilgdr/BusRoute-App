package com.senthil.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.senthil.dao.UserBoardingDetailDAO;
import com.senthil.model.UserBoardingDetail;

@Repository
public class UserBoardingDetailService {

	@Autowired
	private UserBoardingDetailDAO userBoardingDetailDAO;

	public List<UserBoardingDetail> list() {

		return userBoardingDetailDAO.list();
	}

	public UserBoardingDetail findByUserId(Long userId) {
		return userBoardingDetailDAO.findByUserId(userId);
	}

	public List<UserBoardingDetail> findByRouteNo(Long routeNo) {
		return userBoardingDetailDAO.findByRouteNo(routeNo);
	}

	public UserBoardingDetail findById(Long id) {

		return userBoardingDetailDAO.findById(id);
	}

	public void update(UserBoardingDetail bd) {

		userBoardingDetailDAO.update(bd);
	}

	public void delete(Long id) {

		userBoardingDetailDAO.delete(id);
	}

	public void save(UserBoardingDetail bd) {

		userBoardingDetailDAO.save(bd);
	}
	
	public void saveOrUpdate(UserBoardingDetail bd) {

		Long userId = bd.getUser().getId();
		UserBoardingDetail boardingObj = userBoardingDetailDAO.findByUserId(userId);
		//if boarding point not exists - insert
		if (boardingObj == null) {
		
			userBoardingDetailDAO.save(bd);
		}
		else
		{
			//if boarding point exists update
			boardingObj.setBoardingDetail(bd.getBoardingDetail()); // update boarding point
			userBoardingDetailDAO.update(boardingObj);
		}
		
	}

	public Map<Long, Long> findBoardingPointStats() {
		List<Map<String, Object>> findBoardingPointStats = userBoardingDetailDAO.findBoardingPointStats();

		Map<Long, Long> routeMap = new HashMap<>();
		for (Map<String, Object> map : findBoardingPointStats) {
			Long boarding_id = (Long) map.get("boarding_id");
			Long noOfStudents = (Long) map.get("no_of_students");
			routeMap.put(boarding_id, noOfStudents);
		}

		return routeMap;
	}

	public Map<Integer, Long> findByRouteStats() {

		List<Map<String, Object>> routesList = userBoardingDetailDAO.findByRouteStats();

		Map<Integer, Long> routeMap = new HashMap<>();
		for (Map<String, Object> map : routesList) {
			Integer routeNo = (Integer) map.get("route_no");
			Long noOfStudents = (Long) map.get("no_of_students");
			routeMap.put(routeNo, noOfStudents);
		}
		return routeMap;
	}

}
