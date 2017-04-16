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
	
	public List<UserBoardingDetail> findByStudent(Long boardingId) {
		return userBoardingDetailDAO.findByStudent(boardingId);
	}
	

	public Map<String, Long> findByRouteNo(Long routeNo) {
		List<Map<String, Object>> findByRouteNo = userBoardingDetailDAO.findByRouteNo(routeNo);
		Map<String,Long> boardingMap = new HashMap<>();

		for (Map<String, Object> map : findByRouteNo) {
			String boardingName = (String) map.get("name");
			Long count = (Long) map.get("no_of_students");
			boardingMap.put(boardingName, count);
		}
		return boardingMap;
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

	public Map<String, Long> findBoardingPointStats() {
		
		List<Map<String, Object>> findBoardingPointStats = userBoardingDetailDAO.findBoardingPointStats();

		Map<String, Long> routeMap = new HashMap<>();
		
		for (Map<String, Object> map : findBoardingPointStats) {
			String boarding_name = (String) map.get("boarding_name");
			Long noOfStudents = (Long) map.get("no_of_students");
			routeMap.put(boarding_name, noOfStudents);
		}

		return routeMap;
	}

	public Map<String, Long> findByRouteStats() {

		List<Map<String, Object>> routesList = userBoardingDetailDAO.findByRouteStats();

		Map<String, Long> routeMap = new HashMap<>();
		
		for (Map<String, Object> map : routesList) {
			String routeName = (String) map.get("route_name");
			Long noOfStudents = (Long) map.get("no_of_students");
			System.out.println("routeNo:"+ routeName);
			System.out.println("no_of_students:"+ noOfStudents);
			routeMap.put(routeName, noOfStudents);
		}
		return routeMap;
	}

}
