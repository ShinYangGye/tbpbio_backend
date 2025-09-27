package com.meta.controller.api;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.meta.dto.MenuSubSaveReqDto;
import com.meta.dto.ResponseMessageDto;
import com.meta.entity.MenuMainEntity;
import com.meta.service.MenuService;
import com.meta.service.ResponseMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenuController {
	
	private final ResponseMessageService responseMessageService;
	private final MenuService menuService;
	
	// 서브메뉴 저장
	@PostMapping("/admin/menu/sub")
	public ResponseEntity<ResponseMessageDto> saveMenuStep2(@Validated @RequestBody MenuSubSaveReqDto reqData) {
		menuService.saveMenuSub(reqData);
		return ResponseEntity.ok(responseMessageService.getSuccessResult());
	}
	
	// 메뉴 조회
	@GetMapping("/menu/list")
	public List<MenuMainEntity> getMenuList() {
		List<MenuMainEntity> items = menuService.getMenuMainList();
		return items;
	}
	
	// 브랜드 이미지 가져오기
	@ResponseBody
	@GetMapping("/menu/image/{filename}")
	public Resource downloadImage(@PathVariable("filename") String filename) throws MalformedURLException {		
		return menuService.downloadMenuImage(filename);
	}
	
}
