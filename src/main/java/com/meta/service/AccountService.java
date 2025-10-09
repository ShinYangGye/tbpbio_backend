package com.meta.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.meta.advice.exception.CUserNotExistException;
import com.meta.config.principal.PrincipalDetails;
import com.meta.config.principal.PrincipalDetailsService;
import com.meta.dto.account.req.AccountJoinReqDto;
import com.meta.dto.account.req.AccountLoginReqDto;
import com.meta.entity.UserEntity;
import com.meta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

	private final UserRepository userRepository;
	private final PrincipalDetailsService principalDetailsService;
	// private final JwtProvider jwtProvider;	
	
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// 회원가입
	@Transactional
	public void saveUserJoin(AccountJoinReqDto reqData) {
		
		log.info("pass=================== {}", reqData.getPassword());
		
			String encPassword = passwordEncoder.encode(reqData.getPassword());
			
			UserEntity userEntity = UserEntity.builder()				
					.email(reqData.getEmail())
					.password(encPassword)
					.name(reqData.getName())
					.role(reqData.getRole())				
					.build();
					
			userRepository.save(userEntity);


	}
	
	// 로그인
	@Transactional
	public void getAccountToken(AccountLoginReqDto reqData) {
		
		/*
		log.info("================= {}", reqData.getEmail());
		
		//UserEntity userEntity = userRepository.findByEmail(reqData.getEmail()).orElseThrow(() -> new CUserNotExistException());
		UserEntity userEntity = userRepository.findByEmail(reqData.getEmail()).get();
		
		log.info("userEntity================= {}", userEntity.getEmail());
		*/
		
		PrincipalDetails principalDetails = (PrincipalDetails)principalDetailsService.loadUserByUsername(reqData.getEmail());
		
		if ( !passwordEncoder.matches(reqData.getPassword(), principalDetails.getPassword())) {
			throw new BadCredentialsException(reqData.getEmail());
		}
		
		// String accessToken = jwtProvider.makeUserAccessToken(principalDetails.getUser());
		// String refreshToken =  jwtProvider.makeUserRefreshToken(principalDetails.getUser()); 
		
		// AccountTokenResDto userTokenResDto = null;
		/*
		AccountTokenResDto userTokenResDto = AccountTokenResDto.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();				
				*/
		// return userTokenResDto;
	}
}


