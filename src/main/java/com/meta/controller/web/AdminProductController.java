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
import com.meta.dto.MenuMainSaveReqDto;
import com.meta.dto.ProductSaveReqDto;
import com.meta.dto.ProductUpdReqDto;
import com.meta.entity.MenuMainEntity;
import com.meta.entity.ProductEntity;
import com.meta.service.MenuService;
import com.meta.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminProductController {
	
	private final MenuService menuService;
	private final ProductService productService;
	
	// 상품 목록
	@GetMapping({"/product/list"})
	public String getProductList(@RequestParam(name="mainId", defaultValue = "0") Long mainId, @RequestParam(name="subId", defaultValue = "0") Long subId, Model model) {		
		
		if (mainId == 0) mainId = null;		
		if (subId == 0) subId = null;	
		
		// 메뉴 조회
		List<MenuMainEntity> items = menuService.getMenuMainList();			
		model.addAttribute("items", items);
		
		// 상품 조회
		List<ProductEntity> prds = productService.getProductList(mainId, subId);		
		model.addAttribute("prds", prds);
		
		return "admin/product/product_list";
	}
	
	// 상품 등록 폼
	@GetMapping("/product/save")
	public String saveProductForm(@RequestParam(name="mainId") Long mainId, @RequestParam(name="subId") Long subId, Model model) {		
		
		ProductSaveReqDto item = new ProductSaveReqDto();
		item.setMenuMainId(mainId);
		item.setMenuSubId(subId);
		model.addAttribute("item", item);
					
		return "admin/product/product_save";
	}
	
	// 상품 등록 저장
	@PostMapping("/product/save")
	public String saveProduct(@ModelAttribute ProductSaveReqDto reqData, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {		
		
		Long mainId = reqData.getMenuMainId();
		Long subId = reqData.getMenuSubId();
		
		productService.saveProduct(reqData);				
		return "redirect:/admin/product/list?mainId="+mainId+"&subId="+subId;
	}
	
	// 상품 수정 폼
	@GetMapping("/product/upd")
	public String updProductForm(
			@RequestParam(name="mainId", defaultValue = "0") Long mainId, 
			@RequestParam(name="subId", defaultValue = "0") Long subId, 
			@RequestParam(name="productId") Long productId,  
			Model model) {			
		
		if (mainId == 0) mainId = null;		
		if (subId == 0) subId = null;
		
		ProductUpdReqDto item = productService.getProduct(mainId, subId, productId);
		model.addAttribute("item", item);					
		return "admin/product/product_upd";
	}
	
	// 상품 수정 저장
	@PostMapping("/product/upd")
	public String updProduct(@ModelAttribute ProductUpdReqDto reqData, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {				
		productService.updProduct(reqData);				
		return "redirect:/admin/product/upd?productId="+reqData.getProductId();		
		// return "redirect:/admin/product/list";
	}
	
	// 상품 삭제
	@GetMapping("/product/delete")
	public String deleteProduct(@RequestParam(name="productId") Long productId, 
			@RequestParam(name="mainId") Long mainId, @RequestParam(name="subId") Long subId) {		
		productService.deleteProduct(productId);				
		return "redirect:/admin/product/list?mainId="+mainId+"&subId="+subId;
	}
	
	// 이미지 가져오기
	@ResponseBody
	@GetMapping("/product/image/{filename}")
	public Resource downloadImage(@PathVariable("filename") String filename) throws MalformedURLException {		
		return productService.downloadImage(filename);
	}
}




