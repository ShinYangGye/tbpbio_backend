package com.meta.controller.api;

import java.net.MalformedURLException;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.meta.entity.BannerEntity;
import com.meta.service.BannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BannerController {
	
	private final BannerService bannerService;
	
	// 배너 목록 조회
	@GetMapping({"/banner/list"})
	public List<BannerEntity> getBannerList() {
		List<BannerEntity> result = bannerService.getBannerList();
		return result;
	}
	
	// 배너 이미지 가져오기
	@ResponseBody
	@GetMapping("/banner/image/{filename}")
	public Resource downloadImage(@PathVariable("filename") String filename) throws MalformedURLException {		
		return bannerService.downloadImage(filename);
	}
	
}
