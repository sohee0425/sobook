package com.so.book.payment;

import org.apache.ibatis.annotations.Param;

public interface PaymentMapper {

	void payment_insert(PaymentVo vo);
	
	void payment_status(@Param("payment_id") Integer payment_id, @Param("payment_status") String payment_status);
}
