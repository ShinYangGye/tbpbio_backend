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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import com.meta.dto.EventSaveReqDto;
import com.meta.dto.UploadFile;
import com.meta.entity.EventEntity;
import com.meta.entity.EventFileEntity;
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
	
	/**
	* 이벤트 등록 저장
	*/
	@Transactional
	public void saveEvent(EventSaveReqDto reqData)  throws IllegalStateException, IOException {
		
		// 파일저장
		MultipartFile attachFile = reqData.getAttachFile();
		UploadFile uploadAttachFile = fileStore.storeFile(attachFile, eventFileDir);
		
		EventFileEntity eventFileEntity = null;
		
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
		
		EventEntity eventEntity = EventEntity.builder()
				.title(reqData.getTitle())
				.contents(reqData.getContents())
				.file(eventFileEntity)
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
	* 이벤트 삭제
	*/
	@Transactional
	public void deleteEvent(Long eventId) {
		
		EventEntity eventEntity = eventRepository.findById(eventId).get();
		
		EventFileEntity eventFileEntity = eventEntity.getFile();
		
		if (eventFileEntity != null) {
			Long fileId = eventEntity.getFile().getId();
			eventFileRepository.deleteById(fileId);
		}
		
		eventRepository.deleteById(eventId);
		
	}
	
	// 이벤트 파일 다운로드
	public ResponseEntity<Resource> getDownloadAttach(Long eventId) throws MalformedURLException {
		
		EventEntity eventEntity = eventRepository.findById(eventId).get();
		
		Long fileId = eventEntity.getFile().getId();
		
		EventFileEntity eventFileEntity = eventFileRepository.findById(fileId).get();

		String orgFileName = eventFileEntity.getOrgFileName();
		String filePath = eventFileEntity.getSavedFilePath();
		
		UrlResource resource = new UrlResource("file:" + filePath);
		
		String encodeUploadFileName = UriUtils.encode(orgFileName, StandardCharsets.UTF_8);
		String contentDisposition = "attachment; filename=\""+ encodeUploadFileName + "\"";
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
				.body(resource);

	}	
}
