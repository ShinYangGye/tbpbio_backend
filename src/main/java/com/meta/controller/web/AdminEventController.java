package com.meta.controller.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.meta.dto.EventSaveReqDto;
import com.meta.entity.EventEntity;
import com.meta.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminEventController {
	
	private final EventService eventService;
	
	// 이벤트 관리 화면
	@GetMapping({"/event/list"})
	public String eventList(Model model) {		
		
		EventSaveReqDto item = new EventSaveReqDto();
		model.addAttribute("item", item);
		
		List<EventEntity> items = eventService.getEventList();		
		model.addAttribute("items", items);
		
		return "admin/event/event_list";
	}
	
	// 이벤트 등록
	@PostMapping("/event/list")
	public String saveEvent(@ModelAttribute EventSaveReqDto reqData, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		eventService.saveEvent(reqData);				
		return "redirect:/admin/event/list";
	}
	
	// 이벤트 삭제
	@GetMapping("/event/{eventId}/delete")
	public String deleteEvent(@PathVariable("eventId") Long eventId) {
		eventService.deleteEvent(eventId);				
		return "redirect:/admin/event/list";
	}
	
	// 이벤트 파일 다운로드
	@GetMapping("/attach/{eventId}")
	public ResponseEntity<Resource> downloadAttach(@PathVariable("eventId") Long eventId) throws MalformedURLException {
		return eventService.getDownloadAttach(eventId);
	}	
	
}
