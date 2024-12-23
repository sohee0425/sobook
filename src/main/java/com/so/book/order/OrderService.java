package com.so.book.order;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {

	private final OrderMapper orderMapper;
}
