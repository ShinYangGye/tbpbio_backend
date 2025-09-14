package com.meta.controller.web;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.meta.entity.PriceEntity;
import com.meta.service.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminPriceController {
	
	private final PriceService priceService;
	
	// 견적의뢰목록 조회
	@GetMapping({"/price/list"})
	public String priceList(Model model) {		
		
		List<PriceEntity> items = priceService.getPriceList();		
		model.addAttribute("items", items);
		
		return "admin/price/price_list";
	}	
	
}
