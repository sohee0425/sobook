package com.so.book.cart;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

	private final CartMapper cartMapper;
	
	public void cart_add(CartVo vo) {
		cartMapper.cart_add(vo);
	}
}
