package com.so.book.admin.product;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.so.book.category.AdCategoryService;
import com.so.book.category.CategoryVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 관리자 : 상품 관리 기능
@Slf4j
@Controller
@RequestMapping("/admin/product/*")
@RequiredArgsConstructor
public class AdProductController {
	
	private final AdCategoryService adCategoryService;
	
	// 상품등록 폼 1차 카테고리 정보 출력
	@GetMapping("/pro_insert")
	public void pro_insert(Model model) {
		//List<CategoryVo> cate_list = adCategoryService.getFirstCategoryList();
		model.addAttribute("cate_list", adCategoryService.getFirstCategoryList());
	}
	
	// 상품등록(저장)
	
	// 상품수정폼
	
	// 상품수정(변경 저장)
	
	// 상품목록
	
	// 상품 삭제
	
	// 상품 검색

}
