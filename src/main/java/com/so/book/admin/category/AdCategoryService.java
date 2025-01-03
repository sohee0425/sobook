package com.so.book.admin.category;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdCategoryService {

	private final AdCategoryMapper adCategoryMapper;
	
	
	public List<CategoryVo> getFirstCategoryList() {
		return adCategoryMapper.getFirstCategoryList();
	}
	
	public List<CategoryVo> getSecondCategoryList(Integer cate_prtcode) {
		return adCategoryMapper.getSecondCategoryList(cate_prtcode);
	}
	
	// 상품 수정 폼에서 사용할 1차카테고리 정보
		public CategoryVo getFirstCategoryBySecondCategory(int secondCategory) {
			return adCategoryMapper.getFirstCategoryBySecondCategory(secondCategory);
		}
}
