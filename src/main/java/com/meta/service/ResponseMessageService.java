package com.meta.service;

import org.springframework.stereotype.Service;

import com.meta.dto.ResponseMessageDto;

import lombok.Data;

@Service
public class ResponseMessageService {

    public enum CommonResponse {
        SUCCESS("success", "성공했습니다."),
    	FAIL("fail", "실패했습니다.");

        String code;
        String message;

        CommonResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
    
    // 성공 결과만 처리하는 메소드
    public ResponseMessageDto getSuccessResult() {
    	ResponseMessageDto result = new ResponseMessageDto();
        setSuccessResult(result);
        return result;
    }
    
    // 실패 결과만 처리하는 메소드
    public ResponseMessageDto getFailResult(String code, String message) {
    	ResponseMessageDto result = new ResponseMessageDto();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    
    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private void setSuccessResult(ResponseMessageDto result) {    
    	result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMessage(CommonResponse.SUCCESS.getMessage());
    }
	
}


