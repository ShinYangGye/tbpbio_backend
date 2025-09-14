package com.meta.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.meta.dto.PriceSaveReqDto;
import com.meta.entity.PriceEntity;
import com.meta.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PriceService {
	
	private final PriceRepository priceRepository;
	
	/**
	* 견적의뢰 저장
	*/
	@Transactional
	public void savePrice(PriceSaveReqDto reqData) {
		
		PriceEntity priceEntity = PriceEntity.builder()
				.name(reqData.getName())
				.mobile(reqData.getMobile())
				.email(reqData.getEmail())
				.title(reqData.getTitle())
				.contents(reqData.getContents())
				.build();
		
		priceRepository.save(priceEntity);
		
	}

	/**
	* 견적의뢰 목록조회
	*/
	public List<PriceEntity> getPriceList() {		
		String display = "Y";
		List<PriceEntity> result = priceRepository.findByDisplayOrderByIdDesc(display);
		return result;		
	}
	
	
	/**
	* 견적의뢰 단건조회
	*/
	public PriceEntity getPrileById(Long id) {
		
		PriceEntity result = priceRepository.findById(id).orElse(null);
		
		
		// PriceEntity result = priceRepository.findById(id).get();		
		return result;
		
	}
	

}
