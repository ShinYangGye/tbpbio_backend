package com.meta.controller.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.meta.service.item.Item2;
import com.meta.service.item.ItemRepository2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin1")
public class AdminController1 {
	
	private final ItemRepository2 itemRepository;

	@GetMapping({"", "/", "/index"})
	public String home() {		
		return "admin/index";
	}
	
	// 상품목록
	@GetMapping("/items")
	public String items(Model model) {
		List<Item2> items = itemRepository.findAll();
		model.addAttribute("items", items);
		return "/admin/form/items";
	}
	
	// 상품추가 form
	@GetMapping("/item/add")
	public String itemAdd(Model model) {
		model.addAttribute("item", new Item2());
		return "/admin/form/itemAdd";
	}
	
	// 상품추가
	@PostMapping("/item/add")
	public String itemAddProc(@ModelAttribute Item2 item, RedirectAttributes redirectAttributes) {
		log.info("itemName ==== {}", item.getItemName());
		log.info("price ==== {}", item.getPrice());
		log.info("quantity ==== {}", item.getQuantity());
		
		itemRepository.save(item);
		
		return "redirect:/admin/items";
	}
	
	// 상품정보조회
	@GetMapping("/item/{itemId}/info")
	public String itemInfo(@PathVariable("itemId") long itemId, Model model) {
		
		Item2 item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		
		return "/admin/form/itemInfo";
	}
	
	// 상품정보 수정 form
	@GetMapping("/item/{itemId}/edit")
	public String itemEdit(@PathVariable("itemId") Long itemId, Model model) {	
		
		Item2 item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "/admin/form/itemEdit";
	}
	
	// 상품정보 수정
	@PostMapping("/item/{itemId}/edit")
	public String itemEditProc(@PathVariable("itemId") Long itemId, @ModelAttribute Item2 item) {	
		
		itemRepository.update(itemId, item);
		
		return "redirect:/admin/item/{itemId}/info";
	}
}
