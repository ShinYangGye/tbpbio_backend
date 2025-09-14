package com.meta.dto;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class BrandSaveReqDto {
	private String brandName;
	private String brandProduct;
	private String brandUrl;
	private MultipartFile attachFile;	
}
