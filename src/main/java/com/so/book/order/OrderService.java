package com.so.book.order;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.so.book.cart.CartMapper;
import com.so.book.common.utils.SearchCriteria;
import com.so.book.delivery.DeliveryMapper;
import com.so.book.delivery.DeliveryVo;
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
	private final DeliveryMapper deliveryMapper;
	
	// 주문하기 기능 (주문테이블, 주문상세테이블, 결제테이블, 장바구니테이블)
	@Transactional
	public void order_process(OrderVo vo, String mem_id, String p_method) {
		
		log.info("주문번호:" + vo.getOrd_code()); // null
		
		// 1주문테이블
		orderMapper.order_insert(vo);
		
		log.info("주문번호:" + vo.getOrd_code()); // 주문번호 출력
		
		// 2주문상세테이블 : 장바구니 테이블의 정보 이용하여 작업
		orderMapper.order_detail_insert(vo.getOrd_code(), mem_id);
		
		// 3결제테이블
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
		
		// 4장바구니 테이블
		cartMapper.cart_empty(mem_id);
		
		//5 배송테이블
		DeliveryVo deliveryVo = new DeliveryVo();
		deliveryVo.setOrd_code(vo.getOrd_code());
		deliveryVo.setShipping_zipcode(vo.getOrd_zipcode());
		deliveryVo.setShipping_addr(vo.getOrd_addr());
		deliveryVo.setShipping_deaddr(vo.getOrd_addr_detail());
		
		deliveryMapper.delivery_insert(deliveryVo);
	}
	
	// 실시간결제에 따른 주문내역
	public List<Map<String, Object>> getOrderInfoByOrd_code(Integer ord_code) {
		return orderMapper.getOrderInfoByOrd_code(ord_code);
	}
	
	// 주문내역
	public List<Map<String, Object>> getOrderInfoByUser_id(String mem_id, SearchCriteria cri) {
		return orderMapper.getOrderInfoByUser_id(mem_id, cri);
	}
	//카운트
	public int getOrderCountByUser_id(String mem_id) {
		return orderMapper.getOrderCountByUser_id(mem_id);
	}
	
	public String getCategoryNameByPro_code(Integer pro_code) {
		return orderMapper.getCategoryNameByPro_code(pro_code);
	}

	public List<Map<String, Object>> review_manage(String mem_id, SearchCriteria cri) {
		return orderMapper.review_manage(mem_id, cri);
	}
	
	public int getReviewCountByUser_id(String mem_id) {
		return orderMapper.getReviewCountByUser_id(mem_id);
	}
	
	// 주문 상세 내역
	public List<Map<String, Object>> order_detail_info(Integer ord_code) {
		return orderMapper.order_detail_info(ord_code);
	}
	
	// 주문 상세 결제 정보
	public PaymentVo payment_info(Integer ord_code) {
		return orderMapper.payment_info(ord_code);
	}
	
	// 주문 상세 배송지 정보
	public OrderVo order_delivery_info(Integer ord_code) {
		return orderMapper.order_delivery_info(ord_code);
	}
	
	// 주문 상세 배송지 변경
	public void order_info_edit(OrderVo vo) {
		orderMapper.order_info_edit(vo);
	}
	
//	public String getOrderStatus(Integer ord_code) {
//		return orderMapper.getOrderStatus(ord_code);
//	}
	
}
