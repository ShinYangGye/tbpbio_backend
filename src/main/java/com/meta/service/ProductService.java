package com.meta.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.meta.dto.BrandSaveReqDto;
import com.meta.dto.PriceSaveReqDto;
import com.meta.dto.ProductSaveReqDto;
import com.meta.dto.ProductUpdReqDto;
import com.meta.dto.UploadFile;
import com.meta.entity.BannerEntity;
import com.meta.entity.BrandEntity;
import com.meta.entity.BrandFileEntity;
import com.meta.entity.EventEntity;
import com.meta.entity.EventFileEntity;
import com.meta.entity.ProductEntity;
import com.meta.entity.ProductFileEntity;
import com.meta.repository.BannerRepository;
import com.meta.repository.BrandFileRepository;
import com.meta.repository.BrandRepository;
import com.meta.repository.ProductFileRepository;
import com.meta.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
	
	@Value("${productFile.dir}")
	private String productFileDir;
	
	private final FileStore fileStore;	
	private final ProductRepository productRepository;
	private final ProductFileRepository productFileRepository;
	
	private final BannerRepository bannerRepository;
	
	/**
	* 상품 등록 저장
	*/
	@Transactional
	public void saveProduct(ProductSaveReqDto reqData)  throws IllegalStateException, IOException {
		
		// 제품정보 저장
		ProductEntity productEntity = ProductEntity.builder()
				.productName(reqData.getProductName())
				.productUse(reqData.getProductUse())
				.productBrand(reqData.getProductBrand())
				.productContents(reqData.getProductContents())	
				.menuSubId(reqData.getMenuSubId())
				.menuMainId(reqData.getMenuMainId())
				.build();
		
		productRepository.save(productEntity);
				
		// 대표 파일저장
		MultipartFile mainImg = reqData.getMainImg();
		UploadFile uploadMainImg = fileStore.storeFile(mainImg, productFileDir);
		
		ProductFileEntity productFileEntity = null;
		
		if (uploadMainImg != null) {
			
			String orgFileName =  uploadMainImg.getUploadFilename();
			String savedFileName = uploadMainImg.getStoreFileName();
			
			productFileEntity = ProductFileEntity.builder()
					.orgFileName(orgFileName)
					.savedFileName(savedFileName)
					.savedFilePath(productFileDir + savedFileName)
					.productId(productEntity.getId())
					.kind("main")
					.build();
					
			productFileRepository.save(productFileEntity);
			
		}
		
		// 기타 파일 저장
		List<MultipartFile> subImgs = reqData.getSubImg();
		List<UploadFile> uploadSubImgs = fileStore.storeFiles(subImgs, productFileDir);
		
		if (uploadSubImgs != null && uploadSubImgs.size() > 0) {			
			for (int i=0;i<uploadSubImgs.size(); i++) {
				
				String orgFileName =  uploadSubImgs.get(i).getUploadFilename();
				String savedFileName = uploadSubImgs.get(i).getStoreFileName();
				
				productFileEntity = ProductFileEntity.builder()
						.orgFileName(orgFileName)
						.savedFileName(savedFileName)
						.savedFilePath(productFileDir + savedFileName)
						.productId(productEntity.getId())
						.kind("sub")
						.build();
						
				productFileRepository.save(productFileEntity);
				
			}
		}
		
		// 제품특징 파일저장
		MultipartFile useImg = reqData.getProductUseImg();
		UploadFile uploadUseImg = fileStore.storeFile(useImg, productFileDir);
		
		if (uploadUseImg != null) {
			
			String orgFileName =  uploadUseImg.getUploadFilename();
			String savedFileName = uploadUseImg.getStoreFileName();
			
			productFileEntity = ProductFileEntity.builder()
					.orgFileName(orgFileName)
					.savedFileName(savedFileName)
					.savedFilePath(productFileDir + savedFileName)
					.productId(productEntity.getId())
					.kind("use")
					.build();					
			productFileRepository.save(productFileEntity);			
		}
		
		// 제품사양 파일저장
		MultipartFile specImg = reqData.getProductSpecImg();
		UploadFile uploadSpecImg = fileStore.storeFile(specImg, productFileDir);
		
		if (uploadSpecImg != null) {
			
			String orgFileName =  uploadSpecImg.getUploadFilename();
			String savedFileName = uploadSpecImg.getStoreFileName();
			
			productFileEntity = ProductFileEntity.builder()
					.orgFileName(orgFileName)
					.savedFileName(savedFileName)
					.savedFilePath(productFileDir + savedFileName)
					.productId(productEntity.getId())
					.kind("spec")
					.build();					
			productFileRepository.save(productFileEntity);			
		}
		
		// 제품자료 파일 저장
		List<MultipartFile> docImgs = reqData.getProductDocs();
		List<UploadFile> uploadDocImgs = fileStore.storeFiles(docImgs, productFileDir);
		
		if (uploadDocImgs != null && uploadDocImgs.size() > 0) {			
			for (int i=0;i<uploadDocImgs.size(); i++) {
				
				String orgFileName =  uploadDocImgs.get(i).getUploadFilename();
				String savedFileName = uploadDocImgs.get(i).getStoreFileName();
				
				productFileEntity = ProductFileEntity.builder()
						.orgFileName(orgFileName)
						.savedFileName(savedFileName)
						.savedFilePath(productFileDir + savedFileName)
						.productId(productEntity.getId())
						.kind("doc")
						.build();						
				productFileRepository.save(productFileEntity);
				
			}
		}
		
	}

	
	/**
	* 상품 목록 조회
	*/
	public List<ProductEntity> getProductList(Long mainId, Long subId) {				
		
		List<ProductEntity> result = null;
		
		/*
		if ("ALL".equals(brand.toUpperCase())) {
			result = brandRepository.findByOrderByBrandNameAsc();
		} else {
			result = brandRepository.findByBrandNameStartsWithOrderByBrandNameAsc(brand);
		}
		*/
		
		if (mainId == null && subId == null) {
			result = productRepository.findByOrderByIdDesc();
		} else {
			if (mainId != null && subId != null) {
				result = productRepository.findByMenuMainIdAndMenuSubIdOrderByIdDesc(mainId, subId);
			} else {
				if (subId == null) {
					result = productRepository.findByMenuMainIdOrderByIdDesc(mainId);
				} else {
					result = productRepository.findByMenuSubIdOrderByIdDesc(subId);
				}
			}
		}
		
		return result;		
	}
	
	/**
	* 상품 4개 목록 조회
	*/
	public List<ProductEntity> getProductTop4() {				
		
		List<ProductEntity> result = productRepository.findTop4ByOrderByIdDesc();	
		return result;		
	}
	
	
	/**
	* 상품 단건 조회
	*/
	public ProductUpdReqDto getProduct(Long mainId, Long subId, Long productId) {				
				
		ProductEntity result = productRepository.findById(productId).get();
		
		ProductUpdReqDto productUpdReqDto = new ProductUpdReqDto();
		
		if (mainId != null) {
			productUpdReqDto.setMenuMainId(mainId);
		}
		
		if (subId != null) {
			productUpdReqDto.setMenuSubId(subId);
		}
		
		productUpdReqDto.setProductId(productId);
		productUpdReqDto.setProductName(result.getProductName());
		productUpdReqDto.setProductUse(result.getProductUse());
		productUpdReqDto.setProductBrand(result.getProductBrand());
		productUpdReqDto.setProductContents(result.getProductContents());
		productUpdReqDto.setFiles(result.getFiles());
		
		return productUpdReqDto;		
	}
	
	/**
	* 상품 수정 저장
	*/
	@Transactional
	public void updProduct(ProductUpdReqDto reqData)  throws IllegalStateException, IOException {
		
		ProductEntity productEntity = productRepository.findById(reqData.getProductId()).get();
		productEntity.setProductName(reqData.getProductName());
		productEntity.setProductUse(reqData.getProductUse());
		productEntity.setProductBrand(reqData.getProductBrand());
		productEntity.setProductContents(reqData.getProductContents());
		
		
		// 제품자료 파일 저장
		List<UploadFile> uploadDocImgs = fileStore.storeFiles(reqData.getProductDocs(), productFileDir);
		
		if (uploadDocImgs != null && uploadDocImgs.size() > 0) {	
			
			// 현재 파일 조회 및 삭제
			List<ProductFileEntity> docFiles =   productFileRepository.findByProductIdAndKind(reqData.getProductId(), "doc");
			if (docFiles.size() > 0) {				
				for (int i=0;i<docFiles.size();i++) {
					productFileRepository.deleteById(docFiles.get(i).getId());
				}			
			}
			
			// 새로운 파일 저장
			for (int i=0;i<uploadDocImgs.size(); i++) {
				
				String orgFileName =  uploadDocImgs.get(i).getUploadFilename();
				String savedFileName = uploadDocImgs.get(i).getStoreFileName();
				
				ProductFileEntity productFileEntity = ProductFileEntity.builder()
						.orgFileName(orgFileName)
						.savedFileName(savedFileName)
						.savedFilePath(productFileDir + savedFileName)
						.productId(productEntity.getId())
						.kind("doc")
						.build();						
				productFileRepository.save(productFileEntity);
				
			}
		}
		
		// 대표이미지 파일 저장
		UploadFile uploadMainImg = fileStore.storeFile(reqData.getMainImg(), productFileDir);
		
		if (uploadMainImg != null) {	
			
			// 현재 파일 조회 및 삭제
			List<ProductFileEntity> mainFiles =   productFileRepository.findByProductIdAndKind(reqData.getProductId(), "main");
			if (mainFiles.size() > 0) {				
				for (int i=0;i<mainFiles.size();i++) {
					productFileRepository.deleteById(mainFiles.get(i).getId());
				}			
			}
			
			// 새로운 파일 저장				
			String orgFileName =  uploadMainImg.getUploadFilename();
			String savedFileName = uploadMainImg.getStoreFileName();
			
			ProductFileEntity productFileEntity = ProductFileEntity.builder()
					.orgFileName(orgFileName)
					.savedFileName(savedFileName)
					.savedFilePath(productFileDir + savedFileName)
					.productId(productEntity.getId())
					.kind("main")
					.build();						
			productFileRepository.save(productFileEntity);
		
		}
		
		// 제품 기타 이미지 파일 저장
		List<UploadFile> uploadSubImgs = fileStore.storeFiles(reqData.getSubImg(), productFileDir);
		
		if (uploadSubImgs != null && uploadSubImgs.size() > 0) {	
			
			// 현재 파일 조회 및 삭제
			List<ProductFileEntity> subFiles =   productFileRepository.findByProductIdAndKind(reqData.getProductId(), "sub");
			if (subFiles.size() > 0) {				
				for (int i=0;i<subFiles.size();i++) {
					productFileRepository.deleteById(subFiles.get(i).getId());
				}			
			}
			
			// 새로운 파일 저장
			for (int i=0;i<uploadSubImgs.size(); i++) {
				
				String orgFileName =  uploadSubImgs.get(i).getUploadFilename();
				String savedFileName = uploadSubImgs.get(i).getStoreFileName();
				
				ProductFileEntity productFileEntity = ProductFileEntity.builder()
						.orgFileName(orgFileName)
						.savedFileName(savedFileName)
						.savedFilePath(productFileDir + savedFileName)
						.productId(productEntity.getId())
						.kind("sub")
						.build();						
				productFileRepository.save(productFileEntity);
				
			}
		}
		
		// 제픔 특징 이미지 파일 저장
		UploadFile uploadUseImg = fileStore.storeFile(reqData.getProductUseImg(), productFileDir);
		
		if (uploadUseImg != null) {	
			
			// 현재 파일 조회 및 삭제
			List<ProductFileEntity> useFiles =   productFileRepository.findByProductIdAndKind(reqData.getProductId(), "use");
			if (useFiles.size() > 0) {				
				for (int i=0;i<useFiles.size();i++) {
					productFileRepository.deleteById(useFiles.get(i).getId());
				}			
			}
			
			// 새로운 파일 저장				
			String orgFileName =  uploadUseImg.getUploadFilename();
			String savedFileName = uploadUseImg.getStoreFileName();
			
			ProductFileEntity productFileEntity = ProductFileEntity.builder()
					.orgFileName(orgFileName)
					.savedFileName(savedFileName)
					.savedFilePath(productFileDir + savedFileName)
					.productId(productEntity.getId())
					.kind("use")
					.build();						
			productFileRepository.save(productFileEntity);
		
		}
		
		// 제픔 사양 이미지 파일 저장
		UploadFile uploadSpecImg = fileStore.storeFile(reqData.getProductSpecImg(), productFileDir);
		
		if (uploadSpecImg != null) {	
			
			// 현재 파일 조회 및 삭제
			List<ProductFileEntity> specFiles =   productFileRepository.findByProductIdAndKind(reqData.getProductId(), "spec");
			if (specFiles.size() > 0) {				
				for (int i=0;i<specFiles.size();i++) {
					productFileRepository.deleteById(specFiles.get(i).getId());
				}			
			}
			
			// 새로운 파일 저장				
			String orgFileName =  uploadSpecImg.getUploadFilename();
			String savedFileName = uploadSpecImg.getStoreFileName();
			
			ProductFileEntity productFileEntity = ProductFileEntity.builder()
					.orgFileName(orgFileName)
					.savedFileName(savedFileName)
					.savedFilePath(productFileDir + savedFileName)
					.productId(productEntity.getId())
					.kind("spec")
					.build();						
			productFileRepository.save(productFileEntity);
		
		}		
		
	}
	
	// 상품 삭제
	@Transactional
	public void deleteProduct(Long productId) {		
		
		int bannerSize = bannerRepository.findByProductId(productId).size();
				
		if (bannerSize > 0) {
			for (int i=0;i<bannerSize;i++) {
				BannerEntity bannerEntity = bannerRepository.findByProductId(productId).get(i);
				bannerRepository.delete(bannerEntity);
			}
		}

		productRepository.deleteById(productId);
		productFileRepository.deleteByProductId(productId);
	}
	
	/**
	* 브랜드 이미지 가져오기
	*/
	public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {		
		return new UrlResource("file:" + fileStore.getFullPath(filename, productFileDir));	
	}
	
	// 파일 다운로드
	public ResponseEntity<Resource> getDownloadAttach(Long fileId) throws MalformedURLException {
		
		
		ProductFileEntity productFileEntity = productFileRepository.findById(fileId).get();

		String orgFileName = productFileEntity.getOrgFileName();
		String filePath = productFileEntity.getSavedFilePath();
		
		UrlResource resource = new UrlResource("file:" + filePath);
		
		String encodeUploadFileName = UriUtils.encode(orgFileName, StandardCharsets.UTF_8);
		String contentDisposition = "attachment; filename=\""+ encodeUploadFileName + "\"";
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
				.body(resource);

	}
	
	// 파일 사가제
	@Transactional
	public void deleteFile(Long fileId) {
		productFileRepository.deleteById(fileId);
	}
	

	
	
}
