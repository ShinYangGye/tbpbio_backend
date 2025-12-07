package com.meta.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
		
		http.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()));
		
	    http
        .csrf((csrfConfig) ->
            csrfConfig.disable()
        )
        .authorizeHttpRequests((authorizeRequests ->
            authorizeRequests
            .requestMatchers("/admin/login", "/api/*").permitAll()
			
            .requestMatchers(
					"/admin/menu/**"
					, "/admin/menu/**"
					, "/admin/product/**"
					, "/admin/product-main/**"
					, "/admin/brand/**"
					, "/admin/event/**"
					, "/admin/price/**"
					, "/admin/banner/**"
					, "/admin/index"
					).hasRole("ADMIN")
					
			.anyRequest().permitAll()
        ));
 
    http
        .formLogin(login -> login
        .loginPage("/admin/login")
          .loginProcessingUrl("/admin/login-process")
          .usernameParameter("email")
          .passwordParameter("password")
          .defaultSuccessUrl("/admin/index")
          .failureForwardUrl("/admin/login") // 로그인 실패시
          .permitAll()
        );
 
    return http.build();
		
		/*
		http.csrf(AbstractHttpConfigurer::disable)
			// .httpBasic(AbstractHttpConfigurer::disable)
			// .formLogin(AbstractHttpConfigurer::disable)
			
			.authorizeHttpRequests((authorize) -> authorize
				
				// .requestMatchers("/admin/**").hasRole("ADMIN")
				//.requestMatchers("/admin/login", "/").permitAll()
				// .anyRequest().authenticated()
				.requestMatchers("/admin/login").permitAll()
				.requestMatchers("/admin/**").hasRole("ADMIN")	
				.anyRequest().permitAll()
			)
			      
			.formLogin(formLogin -> formLogin
				.loginPage("/admin/login")
				.loginProcessingUrl("/admin/login-process")
				.defaultSuccessUrl("/admin/munu/list")
			)
			.logout((logout) -> logout
				.logoutSuccessUrl("/admin/login")
				.invalidateHttpSession(true)
			);
			
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			);
			
		return http.build();
		
		
		
		http.httpBasic((auth) -> auth.disable());
		http.formLogin((auth) -> auth.disable());		
		http.csrf((auth) -> auth.disable());
		http.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()));
		http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));		
			
		// http.authorizeHttpRequests( (auth) -> 
	
			// auth					
			// .requestMatchers("/api/admin").hasRole("ADMIN")
			// .anyRequest().permitAll()
			// .requestMatchers("/api/test").hasRole("ADMIN")
			// .requestMatchers("/test2").hasAnyRole("ADMIN2", "ADMIN3")
			
			// 스웨거
			// .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll()
			
			// 사용자 계정
			// .requestMatchers("/api/account/**").permitAll()
			
			// .requestMatchers("/admin/**").permitAll()
			// .requestMatchers("/static/**").permitAll()
			
			// 포스트
			// .requestMatchers("/api/post/list").permitAll()
			// .requestMatchers("/api/post").hasRole("USER_BASIC")				
				
			
			.requestMatchers(HttpMethod.POST ,"/api/post/**").authenticated()
			.requestMatchers(HttpMethod.PUT ,"/api/post/**").authenticated()
			.requestMatchers(HttpMethod.DELETE ,"/api/post/**").authenticated()
			.requestMatchers(HttpMethod.GET ,"/api/post/**").permitAll()
			
			.anyRequest().authenticated()			
			
		// );
		
		// return http.build();
		*/
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
