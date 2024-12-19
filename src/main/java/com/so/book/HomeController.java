package com.so.book;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.so.book.admin.category.AdCategoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {
	
	private final AdCategoryService adCategoryService;
	
	@GetMapping("/")
	public String home(Model model) {
		
		// 1차 카테고리목록
		model.addAttribute("cate_list", adCategoryService.getFirstCategoryList());
		
		return "index";
	}
}
