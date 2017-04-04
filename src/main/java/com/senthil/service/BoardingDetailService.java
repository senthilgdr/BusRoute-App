package com.senthil.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senthil.dao.BoardingDetailDAO;
import com.senthil.model.BoardingDetail;

@Service
public class BoardingDetailService {

	@Autowired
	private BoardingDetailDAO boardingDetailDAO;

	public BoardingDetail findById(Long id) {
		return boardingDetailDAO.findById(id);
	}

	public List<BoardingDetail> list() {
		return boardingDetailDAO.list();

	}

	public void save(BoardingDetail BoardingDetail) {

		boardingDetailDAO.save(BoardingDetail);
	}

	public void delete(Long BoardingDetailId) {

		boardingDetailDAO.delete(BoardingDetailId);
	}

	public void update(BoardingDetail BoardingDetail) {

		boardingDetailDAO.update(BoardingDetail);
	}

	public List<BoardingDetail> findByRouteNo(Long routeNo) {
		return boardingDetailDAO.findByRouteNo(routeNo);
	}

}
