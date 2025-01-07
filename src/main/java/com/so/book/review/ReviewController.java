package com.so.book.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.so.book.common.utils.PageMaker;
import com.so.book.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController // Rest API 개발
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/review/*")
public class ReviewController {

	private final ReviewService reviewService;
	
	@GetMapping("/rev_list/{pro_code}/{page}")
	public ResponseEntity<Map<String, Object>> rev_list(@PathVariable("pro_code") Integer pro_code, @PathVariable("page") int page) throws Exception {
		
		log.info("상품코드"+pro_code);
		log.info("페이지"+page);
		
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> map = new HashMap<>();
		
		// 상품후기 목록
		SearchCriteria cri = new SearchCriteria();
		cri.setPerPageNum(5);
		cri.setPage(page);
		
		List<ReviewVo> rev_list = reviewService.rev_list(pro_code, cri);
		
		// 페이징 정보
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(reviewService.getCountReviewByPro_code(pro_code));
		
		// key가 자바 ajax 변수 참조함
		map.put("rev_list", rev_list);
		map.put("pageMaker", pageMaker);
		
		entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		
		return entity;
	}
}
