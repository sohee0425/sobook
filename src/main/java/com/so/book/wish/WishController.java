package com.so.book.wish;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wish/*")
public class WishController {

	private final WishService wishService;
	
	// wish 추가
	@PostMapping("/add")
	public ResponseEntity<String> wishAdd(String mem_id, @RequestParam Integer pro_code) {
		
		ResponseEntity<String> entity = null;
		wishService.wishAdd(mem_id, pro_code);
		return entity;
	}
}
