package com.so.book.order;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.so.book.cart.CartMapper;
import com.so.book.payment.PaymentMapper;
import com.so.book.payment.PaymentVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderService {

	private final OrderMapper orderMapper;
	private final PaymentMapper paymentMapper;
	private final CartMapper cartMapper;
	
	// 주문하기 기능 (주문테이블, 주문상세테이블, 결제테이블, 장바구니테이블)
	@Transactional
	public void order_process(OrderVo vo, String mem_id, String p_method) {
		
		log.info("주문번호:" + vo.getOrd_code()); // null
		
		// 주문테이블
		orderMapper.order_insert(vo);
		
		log.info("주문번호:" + vo.getOrd_code()); // 주문번호 출력
		
		// 주문상세테이블 : 장바구니 테이블의 정보 이용하여 작업
		orderMapper.order_detail_insert(vo.getOrd_code(), mem_id);
		
		// 결제테이블
		PaymentVo p_vo = new PaymentVo();
		p_vo.setOrd_code(vo.getOrd_code());
		p_vo.setMem_id(mem_id);
		
		p_vo.setPayment_method(p_method); // "카카오페이"
		p_vo.setPayment_price(vo.getOrd_price()); // 총구매금액
		
		if(p_method.equals("카카오페이")) {
			p_vo.setPayment_status("입금완료");
		}else if(p_method.contains("무통장입금")) {
			p_vo.setPayment_status("입금대기");
		}
		
		paymentMapper.payment_insert(p_vo);
		
		// 장바구니 테이블
		cartMapper.cart_empty(mem_id);
	}
	
	// 실시간결제에 따른 주문내역
	public List<Map<String, Object>> getOrderInfoByOrd_code(Integer ord_code) {
		return orderMapper.getOrderInfoByOrd_code(ord_code);
	}
}
