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
import com.senthil.model.User;
import com.senthil.model.UserBoardingDetail;
import com.senthil.service.UserBoardingDetailService;

@Controller
@RequestMapping("userboardings")
public class UserBoardingController {

	@Autowired
	private UserBoardingDetailService userBoardingService;

	@GetMapping("/list")
	public String list(ModelMap modelMap, HttpSession session) throws Exception {

		try {

			List<UserBoardingDetail> list = userBoardingService.list();
			System.out.println("list:" + list);
			modelMap.addAttribute("USER_BOARDING_LIST", list);

			return "userboarding/list";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}
	@GetMapping("/listByUser")
	public String listByUser(ModelMap modelMap, HttpSession session) throws Exception {

		try {
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			List<UserBoardingDetail> list = userBoardingService.findByUserId(user.getId());
			System.out.println("list:" + list);
			modelMap.addAttribute("USER_LIST", list);

			return "userboarding/listUser";

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
						
			List<UserBoardingDetail> list = userBoardingService.findByRouteNo(route.getId());
			System.out.println("list:" + list);
			modelMap.addAttribute("ROUTE_LIST", list);

			return "userboarding/listRoute";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}

	@GetMapping("/create")
	public String create() {
		return "userboarding/add";
	}

	@GetMapping("/save")
	public String save(@RequestParam("userId") Long userId, @RequestParam("boardingId") Long boardingId,
			ModelMap modelMap, HttpSession session) throws Exception {

		try {

			// Step : Store in View
			UserBoardingDetail userBoardingDetail = new UserBoardingDetail();
			userBoardingDetail.setActive(true);

			User user = (User) session.getAttribute("LOGGED_IN_USER");
			user.setId(userId);
			userBoardingDetail.setUser(user);

			BoardingDetail boardingDetail = new BoardingDetail();
			boardingDetail.setId(boardingId);

			userBoardingDetail.setBoardingDetail(boardingDetail);

			userBoardingDetail.setActive(true);

			userBoardingService.save(userBoardingDetail);

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
			userBoardingService.delete(Long.valueOf(id));

			return "redirect:/userboardings/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "userboarding/list";
		}

	}

	@GetMapping("/edit")
	public String edit(@RequestParam("id") Long id, ModelMap modelMap) throws Exception {

		try {

			UserBoardingDetail userBoardingDetail = userBoardingService.findById(id);
			modelMap.addAttribute("EDIT_USER_BOARDING", userBoardingDetail);

			return "userboarding/edit";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "userboarding/list";
		}

	}

	@GetMapping("/update")
	public String update(@RequestParam("id") Long id, @RequestParam("userId") Long userId,
			@RequestParam("boardingId") Long boardingId, ModelMap modelMap, HttpSession session) throws Exception {

		try {

			UserBoardingDetail userBoardingDetail = new UserBoardingDetail();
			userBoardingDetail.setId(id);
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			user.setId(userId);
			userBoardingDetail.setUser(user);

			BoardingDetail boardingDetail = new BoardingDetail();
			boardingDetail.setId(boardingId);

			userBoardingDetail.setBoardingDetail(boardingDetail);

			userBoardingDetail.setActive(true);

			userBoardingService.save(userBoardingDetail);

			return "redirect:/userboardings/list";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "edit";
		}

	}

}
