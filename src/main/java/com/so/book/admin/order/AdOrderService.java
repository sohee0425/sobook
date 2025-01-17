package com.so.book.admin.order;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.so.book.common.utils.SearchCriteria;
import com.so.book.order.OrderVo;
import com.so.book.payment.PaymentVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdOrderService {

	private final AdOrderMapper adOrderMapper;
	
	public List<Map<String, Object>> order_list(SearchCriteria cri, String period, String start_date, String end_date, String payment_method, String payment_status, String ord_status) {
		return adOrderMapper.order_list(cri, period, start_date, end_date, payment_method, payment_status, ord_status);
	}
	
	public int getTotalCount(SearchCriteria cri, String period, String start_date, String end_date, String payment_method, String payment_status, String ord_status) {
		return adOrderMapper.getTotalCount(cri, period, start_date, end_date, payment_method, payment_status, ord_status);
	}
	
	public List<Map<String, Object>> orderdetail_info(Integer ord_code) {
		return adOrderMapper.orderdetail_info(ord_code);
	}
	
	public PaymentVo payment_info(Integer ord_code) {
		return adOrderMapper.payment_info(ord_code);
	}
	
	public OrderVo order_info(Integer ord_code) {
		return adOrderMapper.order_info(ord_code);
	}
	
	@Transactional
	public void order_product_del(Integer ord_code, Integer pro_code) {
		
		// 주문 상세테이블에서 상품 삭제
		adOrderMapper.order_product_del(ord_code, pro_code);
	}
	
	public void order_info_edit(OrderVo vo) {
		adOrderMapper.order_info_edit(vo);
	}
	
	public void admin_ord_message(Integer ord_code, String ord_message) {
		adOrderMapper.admin_ord_message(ord_code, ord_message);
	}
}
