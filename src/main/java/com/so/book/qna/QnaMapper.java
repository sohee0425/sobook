package com.so.book.qna;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.so.book.common.utils.SearchCriteria;

public interface QnaMapper {
	
	void qna_write(QnaVo vo);
	
	List<QnaVo> qna_list(@Param("pro_code") Integer pro_code, @Param("cri") SearchCriteria cri);
	
	int getCountQna(Integer pro_code);
	
	QnaVo qna_info(Integer qna_code);
	
	void qna_modify(QnaVo vo);
	
	void qna_delete(Integer qna_code);
	
	void answer_insert(AnswerVo vo);
}
