package com.so.book.admin.review;

import java.util.List;

import org.springframework.stereotype.Service;

import com.so.book.common.utils.SearchCriteria;
import com.so.book.review.ReviewReply;
import com.so.book.review.ReviewVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdReviewService {

	private final AdReviewMapper adReviewMapper;
	
	public List<ReviewVo> review_list(SearchCriteria cri, String rev_content, String rev_score) {
		return adReviewMapper.review_list(cri, rev_content, rev_score);
	}
	
	public int review_count(SearchCriteria cri, String rev_content, String rev_score) {
		return adReviewMapper.review_count(cri, rev_content, rev_score);
	}
	
	public ReviewReply reply_info(Integer reply_id) {
		return adReviewMapper.reply_info(reply_id);
	}
	
	public void reply_modify_save(Integer reply_id, String reply_content) {
		adReviewMapper.reply_modify_save(reply_id, reply_content);
	}
	
	public void reply_delete(Integer reply_id) {
		adReviewMapper.reply_delete(reply_id);
	}
}
