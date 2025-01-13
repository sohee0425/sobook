package com.so.book.admin.order;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdOrderService {

	private final AdOrderMapper adOrderMapper;
}
