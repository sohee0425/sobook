package com.so.book.admin.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

// 관리자 : 상품 관리 기능
@Slf4j
@Controller
@RequestMapping("/admin/product/*")
public class AdProductController {
	
	// 상품등록 폼
	@GetMapping("/pro_insert")
	public void pro_insert() {
		
	}
	
	// 상품등록(저장)
	
	// 상품수정폼
	
	// 상품수정(변경 저장)
	
	// 상품목록
	
	// 상품 삭제
	
	// 상품 검색

}
