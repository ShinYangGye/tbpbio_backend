package com.meta.service.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item2 {
	
	private Long id;
	private String itemName;
	private int price;
	private int quantity;
	
	
	
}
