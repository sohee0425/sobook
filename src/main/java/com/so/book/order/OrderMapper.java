package com.so.book.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.so.book.admin.product.ProductVo;
import com.so.book.common.utils.SearchCriteria;
import com.so.book.payment.PaymentVo;

public interface OrderMapper {

	void order_insert(OrderVo vo);
	
	void order_detail_insert(@Param("ord_code") Integer ord_code, @Param("mem_id") String mem_id);
	
	List<Map<String, Object>> getOrderInfoByOrd_code(Integer ord_code);
	
	List<Map<String, Object>> getOrderInfoByUser_id(@Param("mem_id") String mem_id, @Param("cri") SearchCriteria cri);
	
	int getOrderCountByUser_id(String mem_id);
	
	String getCategoryNameByPro_code(Integer pro_code);
	
	int getReviewCountByUser_id(String mem_id);
	
	// 주문 상세 페이지
	List<Map<String, Object>> order_detail_info(Integer ord_code);
	
	// 주문 상세내역 결제내역 조회
	PaymentVo payment_info(Integer ord_code);
	
	// 주문 상세내역 배송지 조회
	OrderVo order_delivery_info(Integer ord_code);
	
	// 주문 상세내역 배송지 수정
	void order_info_edit(OrderVo vo);
	
//	// 주문상태 조회 (배송 준비 까지만 배송지 변경 가능하게 하기 위해서)
//	String getOrderStatus(Integer ord_code);
	
	List<Map<String, Object>> review_manage(@Param("mem_id") String mem_id, @Param("cri") SearchCriteria cri);
}
