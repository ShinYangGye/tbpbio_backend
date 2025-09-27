package com.meta.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class ProductSaveReqDto {
	private long menuMainId;
	private long menuSubId;
	private String productName;
	private String productUse;
	private String productBrand;
	private String productContents;
	private MultipartFile mainImg;
	private List<MultipartFile> subImg;
	private MultipartFile productUseImg;
	private MultipartFile productSpecImg;
	private List<MultipartFile> productDocs;
}
