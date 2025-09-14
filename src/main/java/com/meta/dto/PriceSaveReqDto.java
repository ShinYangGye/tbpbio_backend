package com.meta.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PriceSaveReqDto {
	
	@NotBlank
	@Length(min = 1, max = 50)
	private String name;
	
	@NotBlank
	@Length(min = 1, max = 50)
	private String mobile;
	
	@NotBlank
	@Length(min = 1, max = 100)	
	private String email;
	
	@NotBlank
	@Length(min = 1, max = 100)
	private String title;
	
	@NotBlank
	private String contents;
}
