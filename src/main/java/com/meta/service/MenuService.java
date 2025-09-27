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
import com.meta.dto.MenuMainSaveReqDto;
import com.meta.dto.MenuSubSaveReqDto;
import com.meta.dto.UploadFile;
import com.meta.entity.MenuMainEntity;
import com.meta.entity.MenuMainFileEntity;
import com.meta.entity.MenuSubEntity;
import com.meta.repository.MenuMainFileRepository;
import com.meta.repository.MenuMainRepository;
import com.meta.repository.MenuSubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {
	
	@Value("${menuFile.dir}")
	private String menuFileDir;
	
	private final FileStore fileStore;	
	private final MenuMainRepository menuMainRepository;
	private final MenuMainFileRepository menuMainFileRepository;
	private final MenuSubRepository menuSubRepository;
	
	/**
	* 메인메뉴 등록 저장
	*/
	@Transactional
	public void saveMenuMain(MenuMainSaveReqDto reqData)  throws IllegalStateException, IOException {
		
		// 파일저장
		MultipartFile attachFile = reqData.getAttachFile();
		UploadFile uploadAttachFile = fileStore.storeFile(attachFile, menuFileDir);
		
		MenuMainFileEntity menuMainFileEntity = null;
		
		if (uploadAttachFile != null) {
			
			String orgFileName =  uploadAttachFile.getUploadFilename();
			String savedFileName = uploadAttachFile.getStoreFileName();
			
			menuMainFileEntity = MenuMainFileEntity.builder()
					.orgFileName(orgFileName)
					.savedFileName(savedFileName)
					.savedFilePath(menuFileDir + savedFileName)
					.build();
					
			menuMainFileRepository.save(menuMainFileEntity);
			
		}
		
		MenuMainEntity menuMainEntity = MenuMainEntity.builder()
				.menuName(reqData.getMenuName())
				.menuInfo(reqData.getMenuInfo())
				.file(menuMainFileEntity)
				.build();
		
		menuMainRepository.save(menuMainEntity);
		
	}

	/**
	* 메인메뉴 목록 조회
	*/
	public List<MenuMainEntity> getMenuMainList() {							
		List<MenuMainEntity> result = menuMainRepository.findByOrderBySortAscIdAsc();		
		return result;		
	}
	
	/**
	* 서브메뉴등록 저장
	*/
	@Transactional
	public void saveMenuSub(MenuSubSaveReqDto reqData) {
				
		MenuSubEntity menuMainEntity = MenuSubEntity.builder()
				.menuMainId(reqData.getMenuOneId())
				.menuName(reqData.getMenuName())
				.build();
		
		menuSubRepository.save(menuMainEntity);
		
	}
	
	/**
	* 메인 메뉴 삭제
	*/
	@Transactional
	public void deleteMenuMain(Long menuId) {		
		menuSubRepository.deleteByMenuMainId(menuId);	
		
		MenuMainEntity menuMainEntity = menuMainRepository.findById(menuId).get();
		
		MenuMainFileEntity productMenuOneFileEntity = menuMainEntity.getFile();
		if (productMenuOneFileEntity != null) {
			Long fileId = menuMainEntity.getFile().getId();
			menuMainFileRepository.deleteById(fileId);
		}
		
		menuMainRepository.deleteById(menuId);		
	}
	
	/**
	* 서브메뉴 삭제
	*/
	@Transactional
	public void deleteMenuSub(Long menuId) {			
		menuSubRepository.deleteById(menuId);		
	}
	
	/**
	* 메뉴 이미지 가져오기
	*/
	public Resource downloadMenuImage(@PathVariable String filename) throws MalformedURLException {		
		return new UrlResource("file:" + fileStore.getFullPath(filename, menuFileDir));	
	}
}
