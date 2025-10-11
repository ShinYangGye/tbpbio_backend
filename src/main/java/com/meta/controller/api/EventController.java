package com.meta.controller.api;

import java.net.MalformedURLException;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.meta.entity.EventEntity;
import com.meta.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EventController {
	
	private final EventService eventService;
	
	// 이벤트 목록
	@GetMapping({"/event/list"})
	public List<EventEntity> eventList() {				
		List<EventEntity> result = eventService.getEventList();				
		return result;
	}
	
	// 이벤트 목록
	@GetMapping({"/event/list-top"})
	public List<EventEntity> eventTop() {				
		List<EventEntity> result = eventService.getEventTop();				
		return result;
	}
	
	// 이벤트 목록
	@GetMapping({"/event/{eventId}/detail"})
	public EventEntity getEvent(@PathVariable("eventId") Long eventId) {				
		EventEntity result = eventService.getEvent(eventId);				
		return result;
	}
	
	// 이벤트 파일 다운로드
	@GetMapping("/event/attach/{eventId}")
	public ResponseEntity<Resource> downloadAttach(@PathVariable("eventId") Long eventId) throws MalformedURLException {
		return eventService.getDownloadAttach(eventId);
	}
	
}
