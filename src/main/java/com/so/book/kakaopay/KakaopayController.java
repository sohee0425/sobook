package com.so.book.kakaopay;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.so.book.member.MemberVo;
import com.so.book.order.OrderService;
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
	private final OrderService orderService;
	
	private OrderVo order_info;
	private String mem_id;
	private int order_total_price;
	
	@PostMapping("/kakaopay")
	public ResponseEntity<ReadyResponse> kakaopay(OrderVo vo, String item_name, int quantity, HttpSession session) {
		
		mem_id = ((MemberVo) session.getAttribute("login_auth")).getMem_id();
		vo.setMem_id(mem_id);
		
		log.info("주문정보: " + vo);
		
		this.order_info = vo;
		this.order_total_price = vo.getOrd_price();
		
		ResponseEntity<ReadyResponse> entity = null;
		
		String partner_order_id = "sobook[" + mem_id + "] - " + new Date().toString();
		
		ReadyResponse readyResponse = kakaopayService.ready(partner_order_id, mem_id, item_name, quantity, order_total_price, 0);
		
		log.info("결제준비요청 응답결과" + readyResponse.toString());
		
		entity = new ResponseEntity<ReadyResponse>(readyResponse, HttpStatus.OK);
		
		return entity;
	}
	
	// 아래 주소를 카카오페이 개발자 애플리케이션 플랫폼에 설정
	// 결제준비요청 성공 시 QR코드에서 페이지 스캔작업 진행 후 아래주소로 pg_token 값 전달 후 호출
	@GetMapping("/approval")
	public String approval(String pg_token, RedirectAttributes rttr) {
		
		log.info("pg_token:"+ pg_token);
		
		// 결제 승인 요청
		String response = kakaopayService.approve(pg_token);
		
		// 결제 승인 요청의 성공 응답 파라미터로 aid 확인
		if(response.contains("aid")) {
			orderService.order_process(this.order_info, mem_id, "카카오페이");
		}
		
		rttr.addAttribute("ord_code", order_info.getOrd_code());
		
		
		return "redirect:/order/order_result";
	}
	
	// 결제취소
	@GetMapping("/cancel")
	public String cancel() {
		return "/order/order_cancel";
	}
	
	// 결제실패
	@GetMapping("/fail")
	public String fail() {
		return "/order/order_fail";
	}
}
