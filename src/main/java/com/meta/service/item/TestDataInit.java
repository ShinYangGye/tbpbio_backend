package com.meta.service.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.meta.entity.Item;
import com.meta.entity.ItemType;
import com.meta.repository.ItemRepository;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;



@Service
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
    	
    	List regionA = new ArrayList<>();
    	regionA.add("SEOUL");
    	regionA.add("JEJU");
    	
    	
    	
    	Item itemA = Item.builder()
    			.itemName("itemA")
    			.price(1000)
    			.quantity(5)
    			.open(true)
    			.regions(regionA)
    			.itemType(ItemType.FOOD)
    			.deliveryCode("FAST")
    			.build();
    	
    	Item itemB = Item.builder()
    			.itemName("itemB")
    			.price(2000)
    			.quantity(8)
    			.open(false)
    			.regions(regionA)
    			.itemType(ItemType.BOOK)
    			.deliveryCode("SLOW")
    			.build();  	
    	
        itemRepository.save(itemA);
        itemRepository.save(itemB);
    }

}