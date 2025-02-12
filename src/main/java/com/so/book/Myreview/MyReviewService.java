package com.so.book.Myreview;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyReviewService {

	private final MyReviewMapper myReviewMapper;
	
	public List<Map<String, Object>> my_reviews(String mem_id) {
		return myReviewMapper.my_reviews(mem_id);
	}

}
