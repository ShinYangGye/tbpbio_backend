package com.meta.controller.api;

import java.net.MalformedURLException;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.meta.dto.ProductUpdReqDto;
import com.meta.dto.ResponseMessageDto;
import com.meta.entity.ProductEntity;
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
	
	// 상품 상세조회
	@GetMapping({"/product/detail/{id}"})
	public ProductUpdReqDto getProduct(@PathVariable("id") Long id) {		
		
		ProductUpdReqDto result = productService.getProduct(null, null, id);
		
		return result;
	}
	
	// 상품 목록
	@GetMapping({"/product/list/{subId}"})
	public List<ProductEntity> getProductList(@PathVariable("subId") Long subId) {		
		
		// 상품 조회
		List<ProductEntity> result = productService.getProductList(null, subId);		
		
		return result;
	}
	
	// 상품 상위 4 목록
	@GetMapping({"/product/list-top"})
	public List<ProductEntity> getProductTop4() {		
		
		// 상품 조회
		List<ProductEntity> result = productService.getProductTop4();				
		return result;
	}
	
	// 이미지 가져오기
	@ResponseBody
	@GetMapping("/product/image/{filename}")
	public Resource downloadImage(@PathVariable("filename") String filename) throws MalformedURLException {		
		return productService.downloadImage(filename);
	}
	
	//  파일 다운로드
	@GetMapping("/product/attach/{fileId}")
	public ResponseEntity<Resource> downloadAttach(@PathVariable("fileId") Long fileId) throws MalformedURLException {
		return productService.getDownloadAttach(fileId);
	}
	
	// 파일삭제
	@GetMapping("/admin/product/file/{fileId}/delete")
	public ResponseEntity<ResponseMessageDto> savePriceInfo(@PathVariable("fileId") Long fileId) {
		productService.deleteFile(fileId);
		return ResponseEntity.ok(responseMessageService.getSuccessResult());
	}
	
}
