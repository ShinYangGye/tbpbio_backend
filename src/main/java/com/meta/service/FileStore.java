package com.meta.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.meta.dto.UploadFile;

@Component
public class FileStore {

	// @Value("${file.dir}")
	// private String fileDir;
	
	public String getFullPath(String fileName, String fileDir) {
		return fileDir + fileName;
	}
	

	public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles, String fileDir) throws IllegalStateException, IOException {
		
		List<UploadFile> storeFileResult = new ArrayList<>();
		for (MultipartFile multipartFile : multipartFiles) {
			if (!multipartFile.isEmpty()) {
				UploadFile uploadFile =  storeFile(multipartFile, fileDir);
				storeFileResult.add(uploadFile);
			}
		}
		
		return storeFileResult;
	}

	
	public UploadFile storeFile(MultipartFile multipartFile, String fileDir) throws IllegalStateException, IOException {
		if (multipartFile.isEmpty()) {
			return null;
		}
		
		String originFilename = multipartFile.getOriginalFilename();
		String storeFilename = createStoreFilename(originFilename);
		
		multipartFile.transferTo(new File(getFullPath(storeFilename, fileDir)));
		
		return new UploadFile(originFilename, storeFilename);
	}
	
	private String createStoreFilename(String originalFileame) {
		String ext = extractExt(originalFileame);		
		String uuid = UUID.randomUUID().toString();
		return uuid + "." + ext;
	}
	
	private String extractExt(String originalFileame) {
		int pos = originalFileame.lastIndexOf(".");
		return originalFileame.substring( + 1);
	}
	
}
