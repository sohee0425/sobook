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
	
	public List<QnaVo> qna_list(SearchCriteria cri, String qna_title, String qna_content) {
		return adQnaMapper.qna_list(cri, qna_title, qna_content);
	}
	
	public int qna_count(SearchCriteria cri, String qna_title, String qna_content) {
		return adQnaMapper.qna_count(cri, qna_title, qna_content);
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
