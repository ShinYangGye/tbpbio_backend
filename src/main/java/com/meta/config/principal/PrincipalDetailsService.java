package com.meta.config.principal;

import java.util.Optional;import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.meta.advice.exception.CUserNotExistException;
import com.meta.entity.UserEntity;
import com.meta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info("email ============================================ {}", email);
		// UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
		// UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new CUserNotExistException());
		
		UserEntity userEntity = userRepository.findByEmail(email);
			
		
		if (userEntity == null) {
			log.info("사용자가 존재하지 않습니다. {}", email);
			throw new UsernameNotFoundException("사용자가 존재하지 않습니다.");
		}
			
		return new PrincipalDetails(userEntity);
		
		
	}

}