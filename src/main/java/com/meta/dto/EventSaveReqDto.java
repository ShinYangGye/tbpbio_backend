package com.meta.dto;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class EventSaveReqDto {

	private Long id;
	private String title;
	private String summary;
	private String contents;
	private MultipartFile attachFile;
	private MultipartFile imgFile;
	private MultipartFile imgListFile;
	
}
