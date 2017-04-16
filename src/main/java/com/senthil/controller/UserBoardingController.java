package com.senthil.controller;

import java.util.List;
import java.util.Map;

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
import com.senthil.service.BoardingDetailService;
import com.senthil.service.RouteService;
import com.senthil.service.UserBoardingDetailService;

@Controller
@RequestMapping("userboardings")
public class UserBoardingController {

	@Autowired
	private UserBoardingDetailService userBoardingService;
	
	@Autowired
	private BoardingDetailService boardingDetailService;
	
	@Autowired
	private RouteService routeService;


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
			UserBoardingDetail list = userBoardingService.findByUserId(user.getId());
			System.out.println("list:" + list);
			modelMap.addAttribute("USER_BOARDING_DETAIL", list);

			return "userboarding/listUser";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}
	@GetMapping("/listByStudent")
	public String listByStudent(@RequestParam("boardingId") Long boardingId,ModelMap modelMap, HttpSession session) throws Exception {

		try {
			BoardingDetail bd=boardingDetailService.findById(boardingId);
			modelMap.addAttribute("BOARDING_DETAIL", bd);
			List<UserBoardingDetail> list = userBoardingService.findByStudent(boardingId);
			System.out.println("list:" + list);
			modelMap.addAttribute("STUDENT_BOARDING_DETAIL", list);

			return "userboarding/listStudent";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}
	
	@GetMapping("/listByRouteName")
	public String listByRouteName( @RequestParam("routeName") String routeName,ModelMap modelMap, HttpSession session) throws Exception {

		try {
			 String []  s=routeName.split("-");			 
			 Long routeId=Long.valueOf(s[0]);
			
			Route r=routeService.findById(routeId);
			modelMap.addAttribute("ROUTE_DETAIL", r);
			
			Map<String, Long> list = userBoardingService.findByRouteNo(routeId);
			System.out.println("list:" + list);
			modelMap.addAttribute("USER_BOARDING_LIST", list);

			return "userboarding/listrouteName";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}
	
	@GetMapping("/create")
	public String create(ModelMap modelMap) {
		List<BoardingDetail> list = boardingDetailService.list();
		modelMap.addAttribute("BOARDING_LIST" , list );
		return "userboarding/add";
	}
	
	@GetMapping("/save")
	public String save( @RequestParam("boardingId") Long boardingId,
			ModelMap modelMap, HttpSession session) throws Exception {

		try {

			// Step : Store in View
			UserBoardingDetail userBoardingDetail = new UserBoardingDetail();
			userBoardingDetail.setActive(true);

			User user = (User) session.getAttribute("LOGGED_IN_USER");
			user.setId(user.getId());
			userBoardingDetail.setUser(user);

			BoardingDetail boardingDetail = new BoardingDetail();
			boardingDetail.setId(boardingId);

			userBoardingDetail.setBoardingDetail(boardingDetail);

			userBoardingService.saveOrUpdate(userBoardingDetail);

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
	
	@GetMapping("/boardingstats")
	public String findBoardingPointStats(ModelMap modelMap) {
		try{
			
			Map<String, Long> map= userBoardingService.findBoardingPointStats();
		
		System.out.println("Map:" + map);
		modelMap.addAttribute("BOARDING_STATS", map);

		return "userboarding/listboardingstats";

	} catch (Exception e) {
		e.printStackTrace();
		modelMap.addAttribute("errorMessage", e.getMessage());
		return "/home";
	}
	}
	
	@GetMapping("/routestats")
	public String findByRouteStats(ModelMap modelMap) {
		
		try{
			Map<String, Long> map = userBoardingService.findByRouteStats();
		    System.out.println("Map:" + map);
			modelMap.addAttribute("ROUTE_STATS", map);

			return "userboarding/listroutestats";

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "/home";
		}
	}
}
