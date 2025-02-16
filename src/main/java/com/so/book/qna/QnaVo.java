package com.so.book.qna;

import java.util.Date;
import java.util.List;

import com.so.book.admin.product.ProductVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QnaVo {

	private Integer qna_code;
	private String mem_id;
	private Integer pro_code;
	private String qna_title;
	private String qna_content;
	private Date qna_date;
	private Date qna_update;
	
	// qna답변
	private List<AnswerVo> answer;
	
	private ProductVo product;
}
