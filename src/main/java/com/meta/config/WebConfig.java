package com.meta.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
	
	private final Environment environment;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/{path:[^\\.]*}").setViewName("forward:/index.html");      
        registry.addViewController("/*/{path:[^\\.]*}").setViewName("forward:/index.html");       
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	
        // /files/** 요청을 받으면, 운영체제 특정 폴더에 있는 파일을 찾습니다.
        // Windows 예시: file:///C:/opt/files/
        // Linux/macOS 예시: file:/opt/files/
    	
    	String profile = environment.getActiveProfiles()[0];
    	/*
        for (String profile : en) {
            System.out.println("====================================== " + profile);
        }
    	*/
    	
    	String dirPath = "";
    	if ("local".equals(profile)) {
    		dirPath = "file:///E:/PROJECT/tnpbio/files/event/";
    	} else {
    		dirPath = "file:/web/files/event/";
    	}
    	
        registry.addResourceHandler("/api/event/files/**")
                .addResourceLocations(dirPath);
    }
}
