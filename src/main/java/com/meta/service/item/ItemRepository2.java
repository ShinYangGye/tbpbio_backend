package com.meta.service.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository2 {
	
	private static final Map<Long, Item2> store = new HashMap<>();
	private static long sequence = 0L;
	
	public Item2 save(Item2 item) {
		item.setId(++sequence);
		store.put(item.getId(), item);
		return item;
	}
	
	public Item2 findById(Long id) {
		return store.get(id);
	}
	
	public List<Item2> findAll() {
		return new ArrayList<>(store.values());
	}
	
	public void update(Long itemId, Item2 updateParam) {
		Item2 findItem = findById(itemId);
		findItem.setItemName(updateParam.getItemName());
		findItem.setPrice(updateParam.getPrice());
		findItem.setQuantity(updateParam.getQuantity());
		
	}
}
