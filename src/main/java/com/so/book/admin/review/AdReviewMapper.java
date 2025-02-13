package com.so.book.admin.review;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.so.book.common.utils.SearchCriteria;
import com.so.book.review.ReviewReply;
import com.so.book.review.ReviewVo;

public interface AdReviewMapper {
	
	List<ReviewVo> review_list(@Param("cri") SearchCriteria cri, @Param("rev_content") String rev_content, @Param("rev_score") String rev_score) ;

	int review_count(@Param("cri") SearchCriteria cri, @Param("rev_content") String rev_content, @Param("rev_score") String rev_score);
	
	ReviewReply reply_info(Long reply_id);
	
	void reply_modify_save(@Param("reply_id") Long reply_id, @Param("reply_content") String reply_content);
	
	void reply_delete(Long reply_id);
}
