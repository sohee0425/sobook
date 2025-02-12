package com.so.book.search;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.so.book.admin.product.ProductVo;
import com.so.book.common.utils.SearchCriteria;

public interface SearchMapper {

	// 자료검색 페이지
	List<ProductVo> search_list(@Param("cri") SearchCriteria cri);
	
//	List<ProductVo> search_list(Map<String, Object> paramMap);
	
	int search_count(@Param("cri") SearchCriteria cri);
	
//	  List<ProductVo> search_list(@Param("cri") SearchCriteria cri);
}
