package com.so.book.common.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.so.book.common.interceptor.LoginInterceptor;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
// 인터셉터 클래스가 관리되는 URI주소 설정 작업
public class WebMvcConfig implements WebMvcConfigurer {

	private final LoginInterceptor loginInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(loginInterceptor)
			.addPathPatterns("/cart/**", "/order/**", "/member/*")
			.excludePathPatterns("/", "/member/join", "/member/login");
	}
	
	
}
