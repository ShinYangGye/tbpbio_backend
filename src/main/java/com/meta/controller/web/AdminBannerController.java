package com.meta.controller.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.meta.dto.BannerSaveReqDto;
import com.meta.entity.BannerEntity;
import com.meta.service.BannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminBannerController {
	
	private final BannerService bannerService;
	
	@ExceptionHandler(Exception.class)
	public String ex(Exception e, Model model) {
	    return "admin/banner/error";
	}

	// 배너 관리 화면
	@GetMapping("/banner/list")
	public String getBannerList(Model model) {		
				
		BannerSaveReqDto item = new BannerSaveReqDto();
		model.addAttribute("item", item);
		
		List<BannerEntity> items = bannerService.getBannerList();				
		
		model.addAttribute("items", items);
		
		return "admin/banner/banner_list";
	}

	
	// 배너 등록
	@PostMapping("/banner/list")
	public String saveBanner(@ModelAttribute BannerSaveReqDto reqData, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {	
		
	    try {
			bannerService.saveBanner(reqData);				
			return "redirect:/admin/banner/list";
	    } catch (Exception e) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found");
	    }

		
		// bannerService.saveBanner(reqData);				
		// return "redirect:/admin/banner/list";
	}
	
	// 배너 삭제
	@GetMapping("/banner/{bannerId}/delete")
	public String deleteBanner(@PathVariable("bannerId") Long bannerId) {
		bannerService.deleteBanner(bannerId);				
		return "redirect:/admin/banner/list";
	}
	
	// 배너 이미지 가져오기
	@ResponseBody
	@GetMapping("/banner/image/{filename}")
	public Resource downloadImage(@PathVariable("filename") String filename) throws MalformedURLException {		
		return bannerService.downloadImage(filename);
	}
	
}
