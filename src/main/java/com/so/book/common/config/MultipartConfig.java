package com.so.book.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

// 파일업로드를 위한 bean 생성 작업
@Configuration
public class MultipartConfig {

	// 스프링부트 3.0 파일업로드 시 영향을 받지않음.
//	@Bean
//	public MultipartResolver multipartResolver() {
//		return new StandardServletMultipartResolver();
//	}
}
