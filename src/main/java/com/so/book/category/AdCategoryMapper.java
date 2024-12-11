package com.so.book.category;

import java.util.List;

public interface AdCategoryMapper {

	// 1차 카테고리 목록
	List<CategoryVo> getFirstCategoryList();
	
	// 2차 카테고리 목록
	List<CategoryVo> getSecondCategoryList(Integer cate_prtcode);
}
