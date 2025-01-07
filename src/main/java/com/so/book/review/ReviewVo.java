package com.so.book.review;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewVo {

	private Long rev_code;
	private String mem_id;
	private Integer pro_code;
	private String rev_content;
	private int rev_score;
	private LocalDateTime rev_date;
}
