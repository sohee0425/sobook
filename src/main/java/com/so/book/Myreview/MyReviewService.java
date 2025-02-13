package com.so.book.Myreview;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.so.book.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyReviewService {

	private final MyReviewMapper myReviewMapper;
	
	public List<Map<String, Object>> my_review(String mem_id, SearchCriteria cri) {
		return myReviewMapper.my_review(mem_id, cri);
	}
	
	public int getTotalReviewCountByUserId(String mem_id) {
		return myReviewMapper.getTotalReviewCountByUserId(mem_id);
	}

}
