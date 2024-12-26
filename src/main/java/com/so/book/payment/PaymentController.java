package com.so.book.payment;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;
	
	
}
