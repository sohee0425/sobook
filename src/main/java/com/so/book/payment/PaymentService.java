package com.so.book.payment;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentMapper paymentMapper;
	
	public void payment_status(@Param("payment_id") Integer payment_id, @Param("payment_status") String payment_status) {
		paymentMapper.payment_status(payment_id, payment_status);
	}
}
