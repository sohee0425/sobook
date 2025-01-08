package com.so.book.review;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.so.book.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {

	private final ReviewMapper reviewMapper;
	
	public List<ReviewVo> rev_list(Integer pro_code, SearchCriteria cri) {
		return reviewMapper.rev_list(pro_code, cri);
	}
	
	public int getCountReviewByPro_code(Integer pro_code) {
		return reviewMapper.getCountReviewByPro_code(pro_code);
	}
	
	public void review_save(ReviewVo vo) {
		reviewMapper.review_save(vo);
	}
	
	public ReviewVo review_info(Long rev_code) {
		return reviewMapper.review_info(rev_code);
	}
	
	public void review_modify(ReviewVo vo) {
		reviewMapper.review_modify(vo);
	}
	
	public void review_delete(Long rev_code) {
		reviewMapper.review_delete(rev_code);
	}
}
