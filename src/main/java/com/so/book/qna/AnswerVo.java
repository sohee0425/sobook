package com.so.book.qna;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnswerVo {

	private Integer ans_code;
	private Integer qna_code;
	private String manager_id;
	private String ans_title;
	private String ans_content;
	private Date ans_date;
	private Date ans_update;
}
