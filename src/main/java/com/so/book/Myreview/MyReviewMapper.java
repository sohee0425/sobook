package com.so.book.Myreview;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.so.book.common.utils.SearchCriteria;

public interface MyReviewMapper {
	
	List<Map<String, Object>> my_review(@Param("mem_id") String mem_id, @Param("cri") SearchCriteria cri);
		
	int getTotalReviewCountByUserId(String mem_id);
}
