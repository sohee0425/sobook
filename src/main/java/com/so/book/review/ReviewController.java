package com.so.book.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.so.book.common.utils.PageMaker;
import com.so.book.common.utils.SearchCriteria;
import com.so.book.member.MemberVo;

import jakarta.servlet.http.HttpSession;
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
	
	// 리뷰 등록
	@PostMapping(value = "/review_save", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> review_save(@RequestBody ReviewVo vo, HttpSession session) throws Exception {
		
		String mem_id = ((MemberVo)session.getAttribute("login_auth")).getMem_id();
		vo.setMem_id(mem_id);
		
		log.info("상품후기"+vo);
		
		ResponseEntity<String> entity = null;
		
		reviewService.review_save(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 수정 목적으로 사용할 후기정보를 JSON포맷으로 클라이언트에게 전송
	@GetMapping(value = "/review_info/{rev_code}")
	public ResponseEntity<ReviewVo> review_info(@PathVariable("rev_code") Long rev_code) throws Exception {
		
		log.info("후기코드"+ rev_code);
		
		ResponseEntity<ReviewVo> entity = null;
		
		entity = new ResponseEntity<ReviewVo>(reviewService.review_info(rev_code), HttpStatus.OK);
		
		return entity;
	}
	
	// 수정
	@PutMapping("/review_modify")
	public ResponseEntity<String> review_modify(@RequestBody ReviewVo vo) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		reviewService.review_modify(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 삭제
	@DeleteMapping("/review_delete/{rev_code}")
	public ResponseEntity<String> review_delete(@PathVariable("rev_code") Long rev_code) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		reviewService.review_delete(rev_code);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
}
