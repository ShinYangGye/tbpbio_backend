package com.meta.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.meta.advice.exception.CAuthenticationEntryPointException;
import com.meta.advice.exception.CUserNotExistException;
// import com.meta.comm.ResponseMessageDto;
// import com.meta.comm.ResponseMessageService;
import com.meta.controller.api.AccountController;
import com.meta.dto.ResponseMessageDto;
import com.meta.service.ResponseMessageService;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;


//@RestControllerAdvice
//@RestControllerAdvice(annotations = {RestController.class}, basePackageClasses = {AccountController.class})

//swagger 출동 오류 때문에 아래 방식으로 사용함
@RestControllerAdvice(annotations = {RestController.class}, basePackages = {"com.meta.controller.api"})
@RequiredArgsConstructor
public class ExceptionAdvice {

	private final ResponseMessageService responseMessageService;
	private final ExceptionMessage exceptionMessage;
	
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 Internal Server Error(서버 내부 에러)
    protected ResponseMessageDto unKnownException(HttpServletRequest request, Exception e) {
        // return responseService.getFailResult(getMessage("UnknownException.code"), getMessage("UnknownException.msg") + "(" + e.getMessage() + ")");
    	return responseMessageService.getFailResult(exceptionMessage.getMessage("UnknownException.code"), exceptionMessage.getMessage("UnknownException.msg") + "(" + e.getClass().getSimpleName() + ")");
    	// return responseService.getFailResult(exceptionMessage.getMessage("UnknownException.code"), exceptionMessage.getMessage("UnknownException.msg") + "(" + e.getMessage() + ")");
    }
	
    
    @ExceptionHandler(CUserNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request(잘못된 요구)
    protected ResponseMessageDto userNotExist(HttpServletRequest request, CUserNotExistException e) {
        return responseMessageService.getFailResult(exceptionMessage.getMessage("UserNotExistException.code"), exceptionMessage.getMessage("UserNotExistException.msg"));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request(잘못된 요구)
    protected ResponseMessageDto userNameNotFound(HttpServletRequest request, UsernameNotFoundException e) {
        return responseMessageService.getFailResult(exceptionMessage.getMessage("UserNotExistException.code"), exceptionMessage.getMessage("UserNotExistException.msg"));
    }
    
    @ExceptionHandler(CAuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401 Unauthorized (인증되지 않았음)
    public ResponseMessageDto authenticationEntryPointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
        return responseMessageService.getFailResult(exceptionMessage.getMessage("EntryPointException.code"), exceptionMessage.getMessage("EntryPointException.msg"));
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request(잘못된 요구)
    protected ResponseMessageDto badCredentialsException(HttpServletRequest request, BadCredentialsException e) {
        return responseMessageService.getFailResult(exceptionMessage.getMessage("BadCredentialsException.code"), exceptionMessage.getMessage("BadCredentialsException.msg"));
    }
}
