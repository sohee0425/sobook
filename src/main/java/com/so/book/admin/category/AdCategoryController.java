package com.so.book.admin.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/category/*")
public class AdCategoryController {

	private final AdCategoryService adCategoryService;
	
	// 1차를 참조하는 2차 카테고리 목록
	@GetMapping("/secondcategory/{cate_prtcode}")
	public ResponseEntity<List<CategoryVo>> getSecondCategoryList(@PathVariable("cate_prtcode")Integer cate_prtcode) {
		log.info("1차 카테고리:"+cate_prtcode);
		
		ResponseEntity<List<CategoryVo>> entity = null;
		
		// List<CategoryVo> secondCategoryList = adCategoryService.getSecondCategoryList(cate_prtcode);
		
		entity = new ResponseEntity<List<CategoryVo>>(adCategoryService.getSecondCategoryList(cate_prtcode), HttpStatus.OK);
		
		return entity;
	}
}
