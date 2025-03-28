package com.so.book.admin.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.so.book.common.utils.SearchCriteria;
import com.so.book.order.OrderVo;
import com.so.book.payment.PaymentVo;

public interface AdOrderMapper {

	List<Map<String, Object>> order_list(@Param("cri") SearchCriteria cri, @Param("period") String period, @Param("start_date") String start_date, 
						@Param("end_date") String end_date, @Param("payment_method") String payment_method, @Param("payment_status") String payment_status,
						@Param("delivery_status") String delivery_status);
	
	int getTotalCount(@Param("cri") SearchCriteria cri, @Param("period") String period,  @Param("start_date") String start_date, 
						@Param("end_date") String end_date, @Param("payment_method") String payment_method, @Param("payment_status") String payment_status,
						@Param("delivery_status") String delivery_status);
	
	List<Map<String, Object>> orderdetail_info(Integer ord_code);
	
	PaymentVo payment_info(Integer ord_code);
	
	OrderVo order_info(Integer ord_code);
	
	void order_product_del(@Param("ord_code") Integer ord_code, @Param("pro_code") Integer pro_code);
	
	void order_info_edit(OrderVo vo);
	
	void admin_ord_message(@Param("ord_code") Integer ord_code, @Param("ord_message") String ord_message);
	
	int order_total_price(Integer ord_code);
	
	void order_info_change_price(Integer ord_code, int ord_total_price);
	
	void payment_change_price(@Param("ord_code") Integer ord_code, @Param("ord_total_price") int ord_total_price);
	
	void order_status(@Param("ord_code") Integer ord_code, @Param("ord_status") String ord_status);
	
	void delivery_status(@Param("ord_code") Integer ord_code, @Param("delivery_status") String delivery_status);
	
}
