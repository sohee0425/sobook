package com.so.book.review;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.so.book.common.utils.SearchCriteria;

public interface ReviewMapper {

	List<ReviewVo> rev_list(@Param("pro_code") Integer pro_code, @Param("cri") SearchCriteria cri);
	
	// 페이징 정보 구성 상품후기 개수
	int getCountReviewByPro_code(Integer pro_code);
	
	void review_save(ReviewVo vo);
	
	ReviewVo review_info(Integer rev_code);
	
	void review_modify(ReviewVo vo);
	
	void review_delete(Integer rev_code);
	
	void reply_insert(ReviewReply vo);
}
