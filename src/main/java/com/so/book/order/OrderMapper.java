package com.so.book.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.so.book.common.utils.SearchCriteria;

public interface OrderMapper {

	void order_insert(OrderVo vo);
	
	void order_detail_insert(@Param("ord_code") Integer ord_code, @Param("mem_id") String mem_id);
	
	List<Map<String, Object>> getOrderInfoByOrd_code(Integer ord_code);
	
	List<Map<String, Object>> getOrderInfoByUser_id(@Param("mem_id") String mem_id, @Param("cri") SearchCriteria cri);
	
	int getOrderCountByUser_id(String mem_id);
	
	String getCategoryNameByPro_code(Integer pro_code);
	
}
