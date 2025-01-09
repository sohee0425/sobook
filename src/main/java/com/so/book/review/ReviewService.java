package com.so.book.review;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.so.book.common.utils.SearchCriteria;
import com.so.book.product.ProductMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {

	private final ReviewMapper reviewMapper;
	private final ProductMapper productMapper;
	
	public List<ReviewVo> rev_list(Integer pro_code, SearchCriteria cri) {
		return reviewMapper.rev_list(pro_code, cri);
	}
	
	public int getCountReviewByPro_code(Integer pro_code) {
		return reviewMapper.getCountReviewByPro_code(pro_code);
	}
	
	@Transactional
	public void review_save(ReviewVo vo) {
		//1 상품후기등록 작업
		reviewMapper.review_save(vo);
		//2 상품후기 count 작업
		productMapper.review_count(vo.getPro_code());
		
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
