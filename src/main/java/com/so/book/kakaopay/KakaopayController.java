package com.so.book.kakaopay;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.so.book.order.OrderVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/kakao/*")
@Controller
@RequiredArgsConstructor
public class KakaopayController {

	private final KakaopayService kakaopayService;
	
	@PostMapping("/kakaopay")
	public ResponseEntity<ReadyResponse> kakaoPay(OrderVo vo, HttpSession session) {
		
		
		
		log.info("주문정보: " + vo);
		
		ResponseEntity<ReadyResponse> entity = null;
		
		return entity;
	}
}
