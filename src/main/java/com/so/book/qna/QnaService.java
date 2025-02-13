package com.so.book.qna;

import java.util.List;

import org.springframework.stereotype.Service;

import com.so.book.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaService {
	
	private final QnaMapper qnaMapper;
	
	// qna 등록
	public void qna_write(QnaVo vo) {
		qnaMapper.qna_write(vo);
	}
	
	public List<QnaVo> qna_list(Integer pro_code, SearchCriteria cri) {
		return qnaMapper.qna_list(pro_code, cri);
	}
	
	public int getCountQna(Integer pro_code) {
		return qnaMapper.getCountQna(pro_code);
	}
	
	public QnaVo qna_info(Integer qna_code) {
		return qnaMapper.qna_info(qna_code);
	}
	
	public void qna_modify(QnaVo vo) {
		qnaMapper.qna_modify(vo);
	}
	
	public void qna_delete(Integer qna_code) {
		qnaMapper.qna_delete(qna_code);
	}
	
	public void answer_insert(AnswerVo vo) {
		qnaMapper.answer_insert(vo);
	}
}
