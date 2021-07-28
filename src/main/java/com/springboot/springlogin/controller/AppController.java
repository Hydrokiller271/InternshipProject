package com.springboot.springlogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {
	@RequestMapping(value = "/signup")
	public String getSignUpForm() {
		return "signup";
	}

	@RequestMapping(value = "/login")
	public String getLoginForm() {
		return "login";
	}
}
