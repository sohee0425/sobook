package com.so.book.admin.product;

import java.util.List;

import com.so.book.common.utils.SearchCriteria;

public interface AdProductMapper {

	// 상품 등록
	void pro_insert(ProductVo vo);
	
	List<ProductVo> pro_list(SearchCriteria cri);
	
	int getTotalCount(SearchCriteria cri);
	
	void pro_sel_delete(int[] pro_code_arr);
}
