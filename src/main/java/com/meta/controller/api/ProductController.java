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
import org.springframework.web.bind.annotation.RestController;

import com.meta.dto.EventSaveReqDto;
import com.meta.dto.PriceSaveReqDto;
import com.meta.dto.ResponseMessageDto;
import com.meta.entity.EventEntity;
import com.meta.entity.PriceEntity;
import com.meta.service.EventService;
import com.meta.service.PriceService;
import com.meta.service.ProductService;
import com.meta.service.ResponseMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
	
	private final ResponseMessageService responseMessageService;
	private final ProductService productService;
	
	// 이벤트 파일 다운로드
	@GetMapping("/product/attach/{fileId}")
	public ResponseEntity<Resource> downloadAttach(@PathVariable("fileId") Long fileId) throws MalformedURLException {
		return productService.getDownloadAttach(fileId);
	}
	
	// 견적의뢰 저장
	@GetMapping("/admin/product/file/{fileId}/delete")
	public ResponseEntity<ResponseMessageDto> savePriceInfo(@PathVariable("fileId") Long fileId) {
		productService.deleteFile(fileId);
		return ResponseEntity.ok(responseMessageService.getSuccessResult());
	}
	
}
