package com.so.book.admin.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin/order/*")
@RequiredArgsConstructor
public class AdOrderController {

	private final AdOrderService adOrderService;
	
	
}
