package com.waben.stock.applayer.promotion.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waben.stock.interfaces.dto.manage.MenuDto;

//@RestController
@RequestMapping("/")
public class LoginController {

	@GetMapping("/login")
	public String login(HttpServletResponse response) {
		return "forward:/pages/user/login.html";
	}

	@GetMapping("/menus")
	@ResponseBody
	public List<MenuDto> fetch(HttpServletResponse response) {
		// List<MenuDto> menus = SecurityAccount.current().getMenus();
		// return menus;
		return null;
	}

}
