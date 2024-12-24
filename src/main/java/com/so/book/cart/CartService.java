package com.so.book.cart;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

	private final CartMapper cartMapper;
	
	public void cart_add(CartVo vo) {
		cartMapper.cart_add(vo);
	}
	
	public List<Map<String, Object>> getCartDetailsByUserId(String mem_id) {
		return cartMapper.getCartDetailsByUserId(mem_id);
	}
	
	public int getCartTotalPriceByUserId(String mem_id) {
		return cartMapper.getCartTotalPriceByUserId(mem_id);
	}
}
