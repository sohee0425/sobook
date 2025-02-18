package com.so.book.admin.review;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.so.book.common.utils.SearchCriteria;
import com.so.book.review.ReviewReply;
import com.so.book.review.ReviewVo;

public interface AdReviewMapper {
	
	List<ReviewVo> review_list(@Param("cri") SearchCriteria cri, @Param("rev_content") String rev_content, @Param("rev_score") String rev_score, @Param("period") String period, @Param("start_date") String start_date, 
			@Param("end_date") String end_date) ;

	int review_count(@Param("cri") SearchCriteria cri, @Param("rev_content") String rev_content, @Param("rev_score") String rev_score, @Param("period") String period, @Param("start_date") String start_date, 
			@Param("end_date") String end_date);
	
	ReviewReply reply_info(Integer reply_id);
	
	void reply_modify_save(@Param("reply_id") Integer reply_id, @Param("reply_content") String reply_content);
	
	void reply_delete(Integer reply_id);
}
