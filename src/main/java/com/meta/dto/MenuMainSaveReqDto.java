package com.meta.dto;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class MenuMainSaveReqDto {

	private String menuName;
	private String menuInfo;
	private MultipartFile attachFile;
	
}
