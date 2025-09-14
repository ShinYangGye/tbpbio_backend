package com.meta.service.item;

import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestDataInit2 {

	private final ItemRepository2 itemRepository;
	
	@PostConstruct
	public void init() {
		
		Item2 itemA = Item2.builder()
				.itemName("itemA")
				.price(10000)
				.quantity(10)
				.build();
				
		Item2 itemB = Item2.builder()
				.itemName("itemB")
				.price(30000)
				.quantity(50)
				.build();
		
		itemRepository.save(itemA);
		itemRepository.save(itemB);
		
	}
	
}
