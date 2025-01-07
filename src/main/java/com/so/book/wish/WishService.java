package com.so.book.wish;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishService {

	private final WishMapper wishMapper;
	
	public void wishAdd(String mem_id, Integer pro_code) {
		wishMapper.wishAdd(mem_id, pro_code);
	}
}
