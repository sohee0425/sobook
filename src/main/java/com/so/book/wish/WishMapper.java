package com.so.book.wish;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.so.book.common.utils.SearchCriteria;

public interface WishMapper {

	void wish_add(WishVo vo);
	
	List<Map<String, Object>> getLikeLIstByUserid(@Param("mem_id") String mem_id, @Param("cri") SearchCriteria cri);
	
	List<Map<String, Object>> wish_list(@Param("mem_id") String mem_id, @Param("cri") SearchCriteria cri);

	void wish_sel_delete(HashMap<String, Object> map);
	
	void wish_delete(Integer pro_code);
}
