package com.senthil.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.senthil.model.Role;
import com.senthil.service.RoleService;

@Controller
@RequestMapping("roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping("/list")
	public String list(ModelMap modelMap, HttpSession session) throws Exception {

		try {

			List<Role> list = roleService.list();
			System.out.println("list:" + list);
			modelMap.addAttribute("ROLE_LIST", list);

			return "role/list";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}

	@GetMapping("/create")
	public String create() {
		return "role/add";
	}

	@GetMapping("/save")
	public String save(@RequestParam("name") String name, ModelMap modelMap, HttpSession session) throws Exception {

		try {

			// Step : Store in View
			Role role = new Role();
			role.setName(name);
			role.setActive(true);

			roleService.save(role);

			return "redirect:list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "add";
		}
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {
			roleService.delete(Long.valueOf(id));

			return "redirect:/roles/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "role/list";
		}

	}

	@GetMapping("/edit")
	public String edit(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {

			Role role = roleService.findById(id);
			modelMap.addAttribute("EDIT_ROLE", role);

			return "role/edit";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "role/list";
		}

	}

	@GetMapping("/update")
	public String update(@RequestParam("id") Long id, @RequestParam("name") String name, ModelMap modelMap,
			HttpSession session) throws Exception {

		try {

			Role role = new Role();
			role.setId(id);
			role.setName(name);
			roleService.update(role);

			return "redirect:/roles/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "edit";
		}

	}

}
