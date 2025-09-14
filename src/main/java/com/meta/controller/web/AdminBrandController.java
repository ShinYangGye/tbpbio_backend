package com.meta.controller.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.meta.dto.BrandSaveReqDto;
import com.meta.entity.BrandEntity;
import com.meta.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminBrandController {
	
	private final BrandService brandService;

	// 브랜드 관리 화면
	@GetMapping("/brand/list")
	public String getBrandList(@RequestParam(name="brand", defaultValue = "ALL") String brand, Model model) {		
				
		BrandSaveReqDto item = new BrandSaveReqDto();
		model.addAttribute("item", item);
		
		List<BrandEntity> items = brandService.getBrandList(brand);				
		String startLetters = brandService.getStartLetter();
		
		model.addAttribute("items", items);
		model.addAttribute("startLetters", startLetters.toUpperCase());
		
		return "admin/brand/brand_list";
	}

	
	// 브랜드 등록
	@PostMapping("/brand/list")
	public String saveBrand(@ModelAttribute BrandSaveReqDto reqData, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {		
		
		if (!StringUtils.hasText(reqData.getBrandName()) 
				|| !StringUtils.hasText(reqData.getBrandProduct())
				|| !StringUtils.hasText(reqData.getBrandUrl())
				|| reqData.getAttachFile().isEmpty()
				) {
			return "redirect:/admin/brand/list";
		}
		
		brandService.saveBrand(reqData);				
		return "redirect:/admin/brand/list";
	}
	
	// 브랜드 삭제
	@GetMapping("/brand/{brandId}/delete")
	public String deleteBrand(@PathVariable("brandId") Long brandId) {
		brandService.deleteBrand(brandId);				
		return "redirect:/admin/brand/list";
	}
	
	// 브랜드 이미지 가져오기
	@ResponseBody
	@GetMapping("/brand/image/{filename}")
	public Resource downloadImage(@PathVariable("filename") String filename) throws MalformedURLException {		
		return brandService.downloadImage(filename);
	}
	
}
