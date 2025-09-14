package com.meta.validation;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import com.meta.entity.Item;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ItemValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Item.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Item item = (Item) target;
		
		Errors bindingResult = errors;
		

		log.info("itemName--->" + item.getItemName());
		log.info("price--->" + item.getPrice());
		log.info("quantity--->" + item.getQuantity());
		log.info("open--->" + item.isOpen());		
		log.info("region--->" + item.getRegions());
		log.info("itemType--->" + item.getItemType());
		log.info("deliveryCode--->" + item.getDeliveryCode());
				
		if (!StringUtils.hasText(item.getItemName())) {
			log.error("상품이름이 없습니다.");
			// bindingResult.addError(new FieldError("item","itemName", item.getItemName(), false, null, null, "상품이름이 없습니다."));
			bindingResult.rejectValue("itemName", "required");
		}
		
		if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
			log.error("가격은 1,000 ~ 1,000,000 입니다.");
			// bindingResult.addError(new FieldError("item","price", item.getPrice(), false, null, null, "가격은 1,000 ~ 1,000,000 입니다."));
			bindingResult.rejectValue("price", "range", new Object[] {1000, 10000}, null);			
		}
		
		if (item.getQuantity() == null || item.getQuantity() > 9999) {
			log.error("수량은 최대 9999 입니다.");
			// bindingResult.addError(new FieldError("item","quantity", "수량은 최대 9999 입니다."));
			bindingResult.rejectValue("quantity", "max", new Object[] {9999}, null);
		}
		
		if (item.getPrice() != null && item.getQuantity() != null) {
			int resultPrice = item.getPrice() * item.getQuantity();
			log.error("가격 수량은 10,000원 이상이어야 합니다. " + resultPrice);
			
			if (resultPrice < 10000) {
				// bindingResult.addError(new ObjectError("item", "가격 수량은 10,000원 이상이어야 합니다. " + resultPrice));
				// bindingResult.rejectValue("totalPrice", "가격 수량은 10,000원 이상이어야 합니다. " + resultPrice);
				bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, "구매 금액은 10000 원 이상입니다.");
			}
		}		
		
	}

}
