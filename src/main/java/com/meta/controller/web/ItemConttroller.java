package com.meta.controller.web;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.meta.entity.DeliveryCode;
import com.meta.entity.Item;
import com.meta.entity.ItemType;
import com.meta.repository.ItemRepository;
import com.meta.validation.ItemValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin2/items")
@RequiredArgsConstructor
public class ItemConttroller {

	private final ItemRepository itemRepository;
	private final ItemValidator itemValidator;
	
	@ModelAttribute("regions")
	public Map<String, String> regions() {
		Map<String, String> regions = new LinkedHashMap<>();
		regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
		return regions;
	}
	
    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        return ItemType.values();
    }
    
    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodes;
    }
    
	// 상품목록
	@GetMapping
	public String items(Model model) {		
		List<Item> items = itemRepository.findAll();
		model.addAttribute("items", items);
		return "admin2/form/items";
	}
	
	// 상품등록 Form
	@GetMapping("/addForm")
	public String addForm(Model model) {	
		Item item = new Item();
		model.addAttribute("item", item);
		return "admin2/form/addForm";
	}
	
	@PostMapping("/add")
	public String add(@Validated @ModelAttribute Item item, BindingResult bindingResult, Model model) {
		
		
		// itemValidator.validate(item, bindingResult);
		
		if (item.getPrice() != null && item.getQuantity() != null) {
			int resultPrice = item.getPrice() * item.getQuantity();
			log.error("가격 수량은 10,000원 이상이어야 합니다. " + resultPrice);
			
			if (resultPrice < 10000) {
				// bindingResult.addError(new ObjectError("item", "가격 수량은 10,000원 이상이어야 합니다. " + resultPrice));
				// bindingResult.rejectValue("totalPrice", "가격 수량은 10,000원 이상이어야 합니다. " + resultPrice);
				bindingResult.reject("totalPrice", new Object[]{10000, resultPrice}, "구매 금액은 10000 원 이상입니다.");
			}
		}	
		
		if (bindingResult.hasErrors()) {
			log.error("errors = {} ", bindingResult);
			return "admin2/form/addForm";
		}
		
		itemRepository.save(item);
		
		return "redirect:/admin2/items";
	}
	
	@GetMapping("/{itemId}")
	public String item(@PathVariable("itemId") Long itemId, Model model) {
		
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "admin2/form/item";
	}
	
	@GetMapping("/{itemId}/editForm")
	public String editForm(@PathVariable("itemId") Long itemId, Model model) {
		
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		
		return "admin2/form/editForm";
	}
	
    @PostMapping("/{itemId}/editForm")
    public String edit(@PathVariable("itemId") Long itemId, @Validated @ModelAttribute Item item, BindingResult bindingResult) {
    	
		if (item.getPrice() != null && item.getQuantity() != null) {
			int resultPrice = item.getPrice() * item.getQuantity();
			log.error("가격 수량은 10,000원 이상이어야 합니다. " + resultPrice);
			
			if (resultPrice < 10000) {
				// bindingResult.addError(new ObjectError("item", "가격 수량은 10,000원 이상이어야 합니다. " + resultPrice));
				// bindingResult.rejectValue("totalPrice", "가격 수량은 10,000원 이상이어야 합니다. " + resultPrice);
				bindingResult.reject("totalPriceMinxxxxxxxxxx", new Object[]{10000, resultPrice}, "구매 금액은 10000 원 이상입니다.");
			}
		}
		
		if (bindingResult.hasErrors()) {
			log.error("errors = {} ", bindingResult);
			return "admin2/form/editForm";
		}
    	
        itemRepository.update(itemId, item);
        return "redirect:/admin2/items/{itemId}";
    }
	
}
