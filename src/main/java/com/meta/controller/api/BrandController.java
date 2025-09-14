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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.meta.dto.EventSaveReqDto;
import com.meta.dto.PriceSaveReqDto;
import com.meta.dto.ResponseMessageDto;
import com.meta.entity.BrandEntity;
import com.meta.entity.EventEntity;
import com.meta.entity.PriceEntity;
import com.meta.service.BrandService;
import com.meta.service.EventService;
import com.meta.service.PriceService;
import com.meta.service.ResponseMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BrandController {
	
	private final BrandService brandService;
	
	// 브랜드 등록 문자 조회
	@GetMapping({"/brand/letters"})
	public String getBrandLetters() {				
		String startLetters = brandService.getStartLetter().toUpperCase();
		return startLetters;
	}
	
	// 브랜드 목록 조회
	@GetMapping({"/brand/list/{brand}"})
	public List<BrandEntity> getBrandList(@PathVariable(name="brand") String brand) {
		List<BrandEntity> result = brandService.getBrandList(brand);
		return result;
	}
	
	// 브랜드 이미지 가져오기
	@ResponseBody
	@GetMapping("/brand/image/{filename}")
	public Resource downloadImage(@PathVariable("filename") String filename) throws MalformedURLException {		
		return brandService.downloadImage(filename);
	}
	
}
