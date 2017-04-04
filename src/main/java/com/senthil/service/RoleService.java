package com.senthil.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senthil.dao.RoleDAO;
import com.senthil.model.Role;

@Service
public class RoleService {
	@Autowired
	private RoleDAO roleDAO;

	public Role findById(Long id) {
		return roleDAO.findById(id);
	}

	public List<Role> list() {
		return roleDAO.list();

	}

	public void save(Role role) {

		roleDAO.save(role);
	}

	public void delete(Long roleId) {

		roleDAO.delete(roleId);
	}

	public void update(Role role) {

		roleDAO.update(role);
	}

}
