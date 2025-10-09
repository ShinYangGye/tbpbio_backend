package com.meta.dto;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class BannerSaveReqDto {
	private String bannerName;
	private Long productId;
	private String type;
	private MultipartFile attachFile;	
}
