package com.meta.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.meta.dto.ResponseMessageDto;
import com.meta.dto.account.req.AccountJoinReqDto;
import com.meta.dto.account.req.AccountLoginReqDto;
import com.meta.service.AccountService;
import com.meta.service.ResponseMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
	
	private final ResponseMessageService responseMessageService;
	private final AccountService accountService;

	@PostMapping("/join")
	public ResponseEntity<ResponseMessageDto> userJoin(@RequestBody AccountJoinReqDto reqData) {		
		accountService.saveUserJoin(reqData);		
		return ResponseEntity.ok(responseMessageService.getSuccessResult());
	}
	
	@PostMapping("/login")
	public ResponseEntity<ResponseMessageDto> getAccountToken(@RequestBody AccountLoginReqDto reqData) {
		accountService.getAccountToken(reqData);
		return ResponseEntity.ok(responseMessageService.getSuccessResult());
		//return ResponseEntity.ok().body(resData);
	}
}
