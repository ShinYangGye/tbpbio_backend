package com.meta.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping({"/login", ""})
	public String login() {		
		return "admin/login";
	}
	
	@GetMapping("/index")
	public String index() {		
		return "admin/index";
	}
	
}
