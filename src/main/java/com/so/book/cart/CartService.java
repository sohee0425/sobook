package com.so.book.cart;

import java.util.HashMap;
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
	
	public Integer getCartTotalPriceByUserId(String mem_id) {
		return cartMapper.getCartTotalPriceByUserId(mem_id);
	}
	
	public List<Map<String, Object>> cart_list(String mem_id) {
		return cartMapper.cart_list(mem_id);
	}
	
	public void cart_empty(String mem_id) {
		cartMapper.cart_empty(mem_id);
	}
	
	public void cart_change(CartVo vo) {
		cartMapper.cart_change(vo);
	}
	
	public void cart_sel_delete(int[] pro_code, String mem_id) {
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("pro_code_arr", pro_code); // 선택된 상품코드
		map.put("mem_id", mem_id); // 사용자 아이디
		
		cartMapper.cart_sel_delete(map);
	}
}
