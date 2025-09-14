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
import com.meta.dto.BrandSaveReqDto;
import com.meta.dto.UploadFile;
import com.meta.entity.BrandEntity;
import com.meta.entity.BrandFileEntity;
import com.meta.repository.BrandFileRepository;
import com.meta.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BrandService {
	
	@Value("${brandFile.dir}")
	private String brandFileDir;
	
	private final FileStore fileStore;	
	private final BrandRepository brandRepository;
	private final BrandFileRepository brandFileRepository;
	
	/**
	* 브랜드 등록 저장
	*/
	@Transactional
	public void saveBrand(BrandSaveReqDto reqData)  throws IllegalStateException, IOException {
		
		// 파일저장
		MultipartFile attachFile = reqData.getAttachFile();
		UploadFile uploadAttachFile = fileStore.storeFile(attachFile, brandFileDir);
		
		BrandFileEntity brandFileEntity = null;
		
		if (uploadAttachFile != null) {
			
			String orgFileName =  uploadAttachFile.getUploadFilename();
			String savedFileName = uploadAttachFile.getStoreFileName();
			
			brandFileEntity = BrandFileEntity.builder()
					.orgFileName(orgFileName)
					.savedFileName(savedFileName)
					.savedFilePath(brandFileDir + savedFileName)
					.build();
					
			brandFileRepository.save(brandFileEntity);
			
		}
		
		BrandEntity brandEntity = BrandEntity.builder()
				.brandName(reqData.getBrandName())
				.brandProduct(reqData.getBrandProduct())
				.brandUrl(reqData.getBrandUrl())
				.file(brandFileEntity)
				.build();
		
		brandRepository.save(brandEntity);
		
	}

	
	/**
	* 브랜드 목록 조회
	*/
	public List<BrandEntity> getBrandList(String brand) {				
		
		List<BrandEntity> result = null;
		
		if ("ALL".equals(brand.toUpperCase())) {
			result = brandRepository.findByOrderByBrandNameAsc();
		} else {
			result = brandRepository.findByBrandNameStartsWithOrderByBrandNameAsc(brand);
		}
		
		return result;		
	}
	
	/**
	* 브랜드 시작 알파벳 조회
	*/
	public String getStartLetter() {				
		
		List<String> listStartLetters =  brandRepository.findByOrderByBrandNameAsc().parallelStream()		
			.map(s -> s.getBrandName().substring(0, 1))
			.distinct()
			.toList();
		
		String strStartLetters = String.join(",", listStartLetters);		
		return strStartLetters;		
	}
	
	/**
	* 브랜드 이미지 가져오기
	*/
	public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {		
		return new UrlResource("file:" + fileStore.getFullPath(filename, brandFileDir));	
	}
	
	/**
	* 브랜드 삭제
	*/
	@Transactional
	public void deleteBrand(Long brandId) {
		
		BrandEntity brandEntity = brandRepository.findById(brandId).get();
		
		BrandFileEntity brandFileEntity = brandEntity.getFile();
		
		if (brandFileEntity != null) {
			Long fileId = brandEntity.getFile().getId();
			brandFileRepository.deleteById(fileId);
		}
		
		brandRepository.deleteById(brandId);
		
	}
	
}
