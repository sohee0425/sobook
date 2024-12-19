package com.so.book.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.so.book.admin.category.AdCategoryService;
import com.so.book.common.utils.PageMaker;
import com.so.book.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@RequestMapping("/product/*")
@RequiredArgsConstructor
@Controller
public class ProductController {

	private final ProductService productService;
	private final AdCategoryService adCategoryService;
	
	@GetMapping("/pro_list")
	public void pro_list(SearchCriteria cri, String cate_name, Integer cate_code, Model model) throws Exception {
		
		// 1차 카테고리목록
		model.addAttribute("cate_list", adCategoryService.getFirstCategoryList());
		
		// 2차 카테고리 상품목록 연결
		model.addAttribute("pro_list", productService.getProductListBysecondCategory(cri, cate_code));
		
		// 페이징 작업
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(productService.getCountProductListBysecondCategory(cate_code));
		
		model.addAttribute("pageMaker", pageMaker);
	}
	
	
}
