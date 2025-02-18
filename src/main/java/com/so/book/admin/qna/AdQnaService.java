package com.so.book.admin.qna;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.so.book.common.utils.SearchCriteria;
import com.so.book.qna.AnswerVo;
import com.so.book.qna.QnaVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdQnaService {
	
	private final AdQnaMapper adQnaMapper;
	
	public List<QnaVo> qna_list(SearchCriteria cri, String period, String start_date, String end_date) {
		return adQnaMapper.qna_list(cri, period, start_date, end_date);
	}
	
	public int qna_count(SearchCriteria cri, String period, String start_date, String end_date) {
		return adQnaMapper.qna_count(cri, period, start_date, end_date);
	}
	
	public AnswerVo answer_info(Integer ans_code) {
		return adQnaMapper.answer_info(ans_code);
	}
	
	public void answer_modify_save(Integer ans_code, String ans_title, String ans_content) {
		adQnaMapper.answer_modify_save(ans_code, ans_title, ans_content);
	}
	
	public void answer_delete(Integer ans_code) {
		adQnaMapper.answer_delete(ans_code);
	}

}
