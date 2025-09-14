package com.meta.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.meta.dto.PriceSaveReqDto;
import com.meta.dto.ResponseMessageDto;
import com.meta.entity.PriceEntity;
import com.meta.service.PriceService;
import com.meta.service.ResponseMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PriceController {
	
	private final ResponseMessageService responseMessageService;
	private final PriceService priceService;
	
	// 견적의뢰 저장
	@PostMapping("/price/save")
	public ResponseEntity<ResponseMessageDto> savePriceInfo(@Validated @RequestBody PriceSaveReqDto reqData) {
		priceService.savePrice(reqData);
		return ResponseEntity.ok(responseMessageService.getSuccessResult());
	}

	// 견적의뢰 단건조회(관리자)
	@GetMapping("/admin/price/{id}/detail")
	public PriceEntity getPrileById(@PathVariable("id") Long id) {		
		PriceEntity result = priceService.getPrileById(id);		
		return result;		
	}
	
}
