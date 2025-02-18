package com.so.book.review;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewReply {

	private Integer reply_id;
	private Integer rev_code;
	private String manager_id;
//	private String reply_title;
	private String reply_content;
	private LocalDateTime reply_date;
	private LocalDateTime reply_update;
	
}
