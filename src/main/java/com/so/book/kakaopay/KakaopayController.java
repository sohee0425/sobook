package com.so.book.kakaopay;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.so.book.member.MemberVo;
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
		
		String mem_id = ((MemberVo) session.getAttribute("login_auth")).getMem_id();
		vo.setMem_id(mem_id);
		
		log.info("주문정보: " + vo);
		
		ResponseEntity<ReadyResponse> entity = null;
		
		ReadyResponse readyResponse = kakaopayService.ready("1000", mem_id, "상품A", 10, 50000, 0);
		
		log.info("결제준비요청 응답결과" + readyResponse.toString());
		
		entity = new ResponseEntity<ReadyResponse>(readyResponse, HttpStatus.OK);
		
		return entity;
	}
	
	// 결제준비요청 성공 시 QR코드에서 페이지 스캔작업 진행 후 아래주소로 pg_token 값 전달 후 호출
	@GetMapping("/approval")
	public void approval(String pg_token) {
		
		log.info("pg_token:"+ pg_token);
		
		kakaopayService.approve(pg_token);
	}
	
	// 결제취소
	@GetMapping("/cancel")
	public void cancel() {
		
	}
}
