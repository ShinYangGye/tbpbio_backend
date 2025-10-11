package com.meta.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import com.meta.dto.EventSaveReqDto;
import com.meta.dto.UploadFile;
import com.meta.entity.BannerEntity;
import com.meta.entity.EventEntity;
import com.meta.entity.EventFileEntity;
import com.meta.repository.BannerRepository;
import com.meta.repository.EventFileRepository;
import com.meta.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {
	
	@Value("${eventFile.dir}")
	private String eventFileDir;
	
	private final FileStore fileStore;	
	private final EventRepository eventRepository;
	private final EventFileRepository eventFileRepository;
	
	private final BannerRepository bannerRepository;
	private final ResourceLoader resourceLoader;
	
	/**
	* 이벤트 등록 저장
	*/
	@Transactional
	public void saveEvent(EventSaveReqDto reqData)  throws IllegalStateException, IOException {
		
		// 파일저장
		MultipartFile attachFile = reqData.getAttachFile();
		UploadFile uploadAttachFile = fileStore.storeFile(attachFile, eventFileDir);
		
		EventFileEntity eventFileEntity = null;
		EventFileEntity eventImgFileEntity = null;
		
		if (uploadAttachFile != null) {
			
			String orgFileName =  uploadAttachFile.getUploadFilename();
			String savedFileName = uploadAttachFile.getStoreFileName();
			
			eventFileEntity = EventFileEntity.builder()
					.orgFileName(orgFileName)
					.savedFileName(savedFileName)
					.savedFilePath(eventFileDir + savedFileName)
					.build();
					
			eventFileRepository.save(eventFileEntity);
			
		}
		
		// 이미지저장
		MultipartFile imgFile = reqData.getImgFile();
		UploadFile uploadImgFile = fileStore.storeFile(imgFile, eventFileDir);
		if (uploadImgFile != null) {
			String orgFileName =  uploadImgFile.getUploadFilename();
			String savedFileName = uploadImgFile.getStoreFileName();
			
			eventImgFileEntity = EventFileEntity.builder()
					.orgFileName(orgFileName)
					.savedFileName(savedFileName)
					.savedFilePath(eventFileDir + savedFileName)
					.build();
			eventFileRepository.save(eventImgFileEntity);
		}
		
		// 이벤트 저장
		EventEntity eventEntity = EventEntity.builder()
				.title(reqData.getTitle())
				.contents(reqData.getContents())
				.file(eventFileEntity)
				.imgFile(eventImgFileEntity)
				.build();
		
		eventRepository.save(eventEntity);
		
	}

	
	/**
	* 이벤트 목록 조회
	*/
	public List<EventEntity> getEventList() {				
		
		// List<EventEntity> result = eventRepository.findAll();		
		List<EventEntity> result = eventRepository.findByOrderByIdDesc();
		
		return result;		
	}
	
	/**
	* 이벤트 목록 조회
	*/
	public List<EventEntity> getEventTop() {				
		
		// List<EventEntity> result = eventRepository.findAll();		
		List<EventEntity> result = eventRepository.findTop3ByOrderByIdDesc();
		
		return result;		
	}
	
	/**
	* 이벤트 단건 조회
	*/
	public EventEntity getEvent(Long eventId) {						
		EventEntity result = eventRepository.findById(eventId).get();		
		return result;		
	}
	
	/**
	* 이벤트 삭제
	*/
	@Transactional
	public void deleteEvent(Long eventId) {
		
		int bannerSize = bannerRepository.findByProductId(eventId).size();
		if (bannerSize > 0) {
			for (int i=0;i<bannerSize;i++) {
				BannerEntity bannerEntity = bannerRepository.findByProductId(eventId).get(i);
				bannerRepository.delete(bannerEntity);
			}
		}
		
		
		EventEntity eventEntity = eventRepository.findById(eventId).get();
		
		EventFileEntity eventFileEntity = eventEntity.getFile();
		
		if (eventFileEntity != null) {
			Long fileId = eventEntity.getFile().getId();
			eventFileRepository.deleteById(fileId);
		}
		
		EventFileEntity eventImgFileEntity = eventEntity.getImgFile();
		if (eventImgFileEntity != null) {
			Long fileId = eventEntity.getImgFile().getId();
			eventFileRepository.deleteById(fileId);
		}
		
		eventRepository.deleteById(eventId);
		
	}
	
	// 이벤트 파일 다운로드
	public ResponseEntity<Resource> getDownloadAttach(Long eventId) throws MalformedURLException {
		
		try {
			EventEntity eventEntity = eventRepository.findById(eventId).get();
			
			Long fileId = eventEntity.getFile().getId();
			
			EventFileEntity eventFileEntity = eventFileRepository.findById(fileId).get();
	
			String orgFileName = eventFileEntity.getOrgFileName();
			String filePath = eventFileEntity.getSavedFilePath();
			
			UrlResource resource = new UrlResource("file:" + filePath);
			
			String encodeUploadFileName = UriUtils.encode(orgFileName, StandardCharsets.UTF_8);
			String contentDisposition = "inline; attachment; filename=\""+ encodeUploadFileName + "\"";
			// String contentDisposition = "inline; attachment; filename=\""+ encodeUploadFileName + "\"";
			File file = resource.getFile();
			
			
			
	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
	                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))
	                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF.toString())
	                // .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString())
	                .body(resource);
			
			/*
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
					.body(resource);
			*/
		
		} catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
		
	}	
}
