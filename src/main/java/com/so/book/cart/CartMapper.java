package com.so.book.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CartMapper {

	void cart_add(CartVo vo);
	
	List<Map<String, Object>> getCartDetailsByUserId(String mem_id);
	
	Integer getCartTotalPriceByUserId(String mem_id);
	
	void cart_empty(String mem_id);
	
	List<Map<String, Object>> cart_list(String mem_id);
	
	void cart_change(CartVo vo);
	
	void cart_sel_delete(HashMap<String, Object> map);
}
