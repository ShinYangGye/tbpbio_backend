package com.meta.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.meta.entity.ProductFileEntity;

import lombok.Data;

@Data
public class ProductUpdReqDto {
	private long menuMainId;
	private long menuSubId;
	private long productId;
	private String productName;
	private String productUse;
	private String productBrand;
	private String productContents;
	private MultipartFile mainImg;
	private List<MultipartFile> subImg;
	private MultipartFile productUseImg;
	private MultipartFile productSpecImg;
	private List<MultipartFile> productDocs;
	
	private List<ProductFileEntity> files;
	
	private String upd;
}
