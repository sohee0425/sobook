package com.so.book.kakaopay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Configuration
@PropertySource("classpath:kakaopay/kakaopay.properties")
public class KakaoPayProperties {
	
	@Value("${kakaopay.secretKey}")
	private String secretKey;
	@Value("${kakaopay.cid}")
	private String cid;

}
