package com.meta.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.httpBasic((auth) -> auth.disable());
		http.formLogin((auth) -> auth.disable());		
		http.csrf((auth) -> auth.disable());
		http.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()));
		http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));		
				
		http.authorizeHttpRequests( (auth) -> 
	
			auth					
			.requestMatchers("/api/test").hasRole("ADMIN")
			// .requestMatchers("/test2").hasAnyRole("ADMIN2", "ADMIN3")
			
			// 스웨거
			.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll()
			
			// 사용자 계정
			.requestMatchers("/api/account/**").permitAll()
			
			.requestMatchers("/admin/**").permitAll()
			.requestMatchers("/static/**").permitAll()
			
			// 포스트
			// .requestMatchers("/api/post/list").permitAll()
			// .requestMatchers("/api/post").hasRole("USER_BASIC")				
				
			/*
			.requestMatchers(HttpMethod.POST ,"/api/post/**").authenticated()
			.requestMatchers(HttpMethod.PUT ,"/api/post/**").authenticated()
			.requestMatchers(HttpMethod.DELETE ,"/api/post/**").authenticated()
			.requestMatchers(HttpMethod.GET ,"/api/post/**").permitAll()
			
			.anyRequest().authenticated()
			*/
			
			.anyRequest().permitAll()
			
		);
		
		return http.build();
	}

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		// configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000",	"http://localhost:8080"));
		configuration.setAllowedOriginPatterns(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		// configuration.addExposedHeader(JwtProperties.RESPONSE_TOKEN_HEADER);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/api/**", configuration);
		return source;
	}   

}
