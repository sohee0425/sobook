package com.so.book.cart;

import java.util.List;
import java.util.Map;

public interface CartMapper {

	void cart_add(CartVo vo);
	
	List<Map<String, Object>> getCartDetailsByUserId(String mem_id);
	
	int getCartTotalPriceByUserId(String mem_id);
}
