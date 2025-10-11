package com.meta.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.meta.dto.PriceSaveReqDto;
import com.meta.entity.PriceEntity;
import com.meta.repository.PriceRepository;
import com.meta.utils.AES256Util;

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
		try {
			AES256Util AES256Util = new AES256Util();
			String encName = AES256Util.encrypt(reqData.getName());
			String encMobile = AES256Util.encrypt(reqData.getMobile());
			
			PriceEntity priceEntity = PriceEntity.builder()
					.name(encName)
					.mobile(encMobile)
					.email(reqData.getEmail())
					.title(reqData.getTitle())
					.contents(reqData.getContents())
					.build();
			
			priceRepository.save(priceEntity);
		} catch (UnsupportedEncodingException | GeneralSecurityException e1) {				
			e1.printStackTrace();
		}
		
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
		
		try {
			AES256Util AES256Util = new AES256Util();
			String encName = result.getName();
			String encMobile = result.getMobile();
			
			String decName = AES256Util.decrypt(encName);		
			String decMobile = AES256Util.decrypt(encMobile);	
			
			result.setName(decName);
			result.setMobile(decMobile);
			
		} catch (UnsupportedEncodingException | GeneralSecurityException e1) {				
			e1.printStackTrace();
		}
		
		// PriceEntity result = priceRepository.findById(id).get();		
		return result;
		
	}
	
	
	/**
	* 견적의뢰 삭제하기
	*/
	@Transactional
	public void deletePrice(Long priceId) {
		// PriceEntity priceEntity = priceRepository.findById(priceId).get();
		priceRepository.deleteById(priceId);
	}

}
