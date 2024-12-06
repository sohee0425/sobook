package com.so.book.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// 스프링시큐리티 보안기능 설정을 하기 위해서 만든 클래스.
// 목적 : 비밀번호 암호화 클래스를 bean으로 생성하는 기능만 사용
// bean은 bean객체가 필요한 곳에 주입하여 쓰기 위해 만드는 것
@Configuration
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
