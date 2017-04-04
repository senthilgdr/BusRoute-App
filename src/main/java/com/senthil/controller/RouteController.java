package com.senthil.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.senthil.model.Route;
import com.senthil.service.RouteService;

@Controller
@RequestMapping("routes")
public class RouteController {

	@Autowired
	private RouteService routeService;

	@GetMapping("/list")
	public String list(ModelMap modelMap, HttpSession session) throws Exception {

		try {

			List<Route> list = routeService.list();
			System.out.println("list:" + list);
			modelMap.addAttribute("ROUTE_LIST", list);

			return "route/list";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}

	@GetMapping("/create")
	public String create() {
		return "route/add";
	}

	@GetMapping("/save")
	public String save(@RequestParam("name") String name, @RequestParam("trip") String trip, ModelMap modelMap,
			HttpSession session) throws Exception {

		try {

			// Step : Store in View
			Route route = new Route();
			route.setName(name);
			route.setTrip(trip);
			route.setActive(true);

			routeService.save(route);

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
			routeService.delete(Long.valueOf(id));

			return "redirect:/routes/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "route/list";
		}

	}

	@GetMapping("/edit")
	public String edit(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {

			Route route = routeService.findById(id);
			modelMap.addAttribute("EDIT_ROUTE", route);

			return "route/edit";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "route/list";
		}

	}

	@GetMapping("/update")
	public String update(@RequestParam("id") Long id, @RequestParam("name") String name,
			@RequestParam("trip") String trip, ModelMap modelMap, HttpSession session) throws Exception {

		try {

			Route route = new Route();
			route.setId(id);
			route.setName(name);
			route.setTrip(trip);
			routeService.update(route);

			return "redirect:/routes/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "edit";
		}

	}

}
