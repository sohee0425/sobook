package com.so.book.search;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.so.book.admin.product.ProductVo;
import com.so.book.common.utils.SearchCriteria;

public interface SearchMapper {

	// 자료검색 페이지
//	List<ProductVo> search_list(SearchCriteria cri);
//	
//	int search_count(SearchCriteria cri);
	
	  List<ProductVo> search_list(@Param("cri") SearchCriteria cri);
}
