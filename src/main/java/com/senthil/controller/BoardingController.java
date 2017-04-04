package com.senthil.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.senthil.model.BoardingDetail;
import com.senthil.model.Route;
import com.senthil.service.BoardingDetailService;

@Controller
@RequestMapping("boardings")
public class BoardingController {

	@Autowired
	private BoardingDetailService boardingDetailService;

	@GetMapping("/list")
	public String list(ModelMap modelMap, HttpSession session) throws Exception {

		try {

			List<BoardingDetail> list = boardingDetailService.list();
			System.out.println("list:" + list);
			modelMap.addAttribute("BOARDING_LIST", list);

			return "boarding/list";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}
	@GetMapping("/listByRoute")
	public String listByRoute(ModelMap modelMap, HttpSession session) throws Exception {

		try {
			Route route=new Route();

			List<BoardingDetail> list = boardingDetailService.findByRouteNo(route.getId());
			System.out.println("list:" + list);
			modelMap.addAttribute("ROUTE_LIST", list);

			return "boarding/listRoute";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}

	@GetMapping("/create")
	public String create() {
		return "boarding/add";
	}

	@GetMapping("/save")
	public String save(@RequestParam("routeNo") Long routeNo,@RequestParam("name") String name, ModelMap modelMap, HttpSession session) throws Exception {

		try {

			// Step : Store in View
			BoardingDetail boardingDetail = new BoardingDetail();
			boardingDetail.setName(name);
			boardingDetail.setActive(true);
			
			Route route=new Route();
			route.setId(routeNo);
			
			boardingDetail.setRoute(route);

			boardingDetailService.save(boardingDetail);

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
			boardingDetailService.delete(Long.valueOf(id));

			return "redirect:/boardings/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "boarding/list";
		}

	}

	@GetMapping("/edit")
	public String edit(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {

			BoardingDetail boardingDetail = boardingDetailService.findById(id);
			modelMap.addAttribute("EDIT_BOARDING", boardingDetail);

			return "boarding/edit";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "boarding/list";
		}

	}

	@GetMapping("/update")
	public String update(@RequestParam("id") Long id, @RequestParam("routeNo") Long routeNo,@RequestParam("name") String name, ModelMap modelMap,
			HttpSession session) throws Exception {

		try {

			BoardingDetail boardingDetail = new BoardingDetail();
			boardingDetail.setId(id);
			boardingDetail.setName(name);
			
			Route route=new Route();
			route.setId(routeNo);			
			boardingDetail.setRoute(route);
			
			boardingDetailService.update(boardingDetail);

			return "redirect:/boardings/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "edit";
		}

	}

}