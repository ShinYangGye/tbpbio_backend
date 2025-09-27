package com.meta.controller.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.meta.dto.MenuMainSaveReqDto;
import com.meta.entity.MenuMainEntity;
import com.meta.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminMenuController {
	
	private final MenuService menuService;
	
	// 메뉴관리 화면
	@GetMapping("/menu/list")
	public String menuList(Model model) {		
		
		MenuMainSaveReqDto item = new MenuMainSaveReqDto();
		model.addAttribute("item", item);
		
		List<MenuMainEntity> items = menuService.getMenuMainList();			
		model.addAttribute("items", items);
		
		return "admin/menu/menu_list";
	}
	
	// 메뉴 등록
	@PostMapping("/menu/list")
	public String saveMenuMain(@ModelAttribute MenuMainSaveReqDto reqData, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		menuService.saveMenuMain(reqData);				
		return "redirect:/admin/menu/list";
	}
	
	// 메뉴 삭제
	@GetMapping("/menu/main/{menuId}/delete")
	public String deleteMenuMain(@PathVariable("menuId") Long menuId) {
		menuService.deleteMenuMain(menuId);				
		return "redirect:/admin/menu/list";
	}
	
	// 서브메뉴 삭제
	@GetMapping("/menu/sub/{menuId}/delete")
	public String deleteMenuSub(@PathVariable("menuId") Long menuId) {
		menuService.deleteMenuSub(menuId);				
		return "redirect:/admin/menu/list";
	}
	
	// 메뉴 이미지 가져오기
	@ResponseBody
	@GetMapping("/menu/image/{filename}")
	public Resource downloadImage(@PathVariable("filename") String filename) throws MalformedURLException {		
		return menuService.downloadMenuImage(filename);
	}
	
}
