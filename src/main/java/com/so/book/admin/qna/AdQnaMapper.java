package com.so.book.admin.qna;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.so.book.common.utils.SearchCriteria;
import com.so.book.qna.AnswerVo;
import com.so.book.qna.QnaVo;

public interface AdQnaMapper {
	
	List<QnaVo> qna_list(@Param("cri") SearchCriteria cri,  @Param("period") String period, @Param("start_date") String start_date, @Param("end_date") String end_date);

	int qna_count(@Param("cri") SearchCriteria cri,  @Param("period") String period, @Param("start_date") String start_date, @Param("end_date") String end_date );

	AnswerVo answer_info(Integer ans_code);
	
	void answer_modify_save(@Param("ans_code") Integer ans_code, @Param("ans_title") String ans_title, @Param("ans_content") String ans_content);

	void answer_delete(Integer ans_code);
}
