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
	
	@GetMapping({"", "/", "/index"})
	public String home() {		
		return "admin/index";
	}
	
	
	/*
	@GetMapping({"/product/list"})
	public String productList() {		
		return "admin/product/product_list";
	}
	
	
	@GetMapping({"/menu/list"})
	public String menuList() {		
		return "admin/menu/menu_list";
	}
	
	

	
	
	@GetMapping({"/brand/list"})
	public String brandList() {		
		return "admin/brand/brand_list";
	}
	*/
	
}
