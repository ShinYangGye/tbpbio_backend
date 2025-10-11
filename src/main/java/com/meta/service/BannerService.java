package com.meta.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.meta.dto.BannerSaveReqDto;
import com.meta.dto.BrandSaveReqDto;
import com.meta.dto.UploadFile;
import com.meta.entity.BannerEntity;
import com.meta.entity.BannerFileEntity;
import com.meta.entity.BrandEntity;
import com.meta.entity.BrandFileEntity;
import com.meta.entity.EventEntity;
import com.meta.entity.ProductEntity;
import com.meta.repository.BannerFileRepository;
import com.meta.repository.BannerRepository;
import com.meta.repository.BrandFileRepository;
import com.meta.repository.BrandRepository;
import com.meta.repository.EventRepository;
import com.meta.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BannerService {
	
	@Value("${bannerFile.dir}")
	private String bannerFileDir;
	
	private final FileStore fileStore;	
	private final BannerRepository bannerRepository;
	private final BannerFileRepository bannerFileRepository;
	
	private final ProductRepository productRepository;
	private final EventRepository eventRepository;
	
	/**
	* 배너 등록 저장
	*/
	@Transactional
	public void saveBanner(BannerSaveReqDto reqData)  throws IllegalStateException, IOException {
		
		// 파일저장
		MultipartFile attachFile = reqData.getAttachFile();
		UploadFile uploadAttachFile = fileStore.storeFile(attachFile, bannerFileDir);
		
		BannerFileEntity bannerFileEntity = null;
		
		if (uploadAttachFile != null) {
			
			String orgFileName =  uploadAttachFile.getUploadFilename();
			String savedFileName = uploadAttachFile.getStoreFileName();
			
			bannerFileEntity = BannerFileEntity.builder()
					.orgFileName(orgFileName)
					.savedFileName(savedFileName)
					.savedFilePath(bannerFileDir + savedFileName)
					.build();
					
			bannerFileRepository.save(bannerFileEntity);
			
		}
		
		
		if ("product".equals(reqData.getType())) {
			ProductEntity productEntity = productRepository.findById(reqData.getProductId()).get();
			
			if (productEntity == null) {
				log.error("해당 제품이 존재하지 않습니다.");
				return;
			}
			
			BannerEntity bannerEntity = BannerEntity.builder()
					.bannerName(reqData.getBannerName())
					.productId(reqData.getProductId())
					.productName(productEntity.getProductName())
					.menuMainId(productEntity.getMenuMainId())
					.menuSubId(productEntity.getMenuSubId())
					.type(reqData.getType())
					.file(bannerFileEntity)
					.build();
			bannerRepository.save(bannerEntity);
		} else if ("event".equals(reqData.getType())) {
			
			EventEntity eventEntity = eventRepository.findById(reqData.getProductId()).get();
			
			if (eventEntity == null) {
				log.error("해당 이벤트가 존재하지 않습니다.");
				return;
			}
			
			BannerEntity bannerEntity = BannerEntity.builder()
					.bannerName(reqData.getBannerName())
					.productId(reqData.getProductId())
					.productName(eventEntity.getTitle())
					.menuMainId(0L)
					.menuSubId(0L)
					.type(reqData.getType())
					.file(bannerFileEntity)
					.build();
			bannerRepository.save(bannerEntity);
			
		}
		
		
	}

	
	/**
	* 배너 목록 조회
	*/
	public List<BannerEntity> getBannerList() {				
		List<BannerEntity> result =  bannerRepository.findByOrderBySortAscIdDesc();
		return result;		
	}
	
	/**
	* 배너 삭제
	*/
	@Transactional
	public void deleteBanner(Long bannerId) {
		
		BannerEntity bannerEntity = bannerRepository.findById(bannerId).get();
		
		BannerFileEntity bannerFileEntity = bannerEntity.getFile();
		
		if (bannerFileEntity != null) {
			Long fileId = bannerEntity.getFile().getId();
			bannerFileRepository.deleteById(fileId);
		}
		
		bannerRepository.deleteById(bannerId);
		
	}
	
	/**
	* 배너 이미지 가져오기
	*/
	public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {		
		return new UrlResource("file:" + fileStore.getFullPath(filename, bannerFileDir));	
	}
	

	
}
