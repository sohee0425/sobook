package com.so.book.review;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewReply {

	private Long reply_id;
	private Long rev_code;
	private String manager_id;
	private String reply_title;
	private String reply_content;
	private LocalDateTime reply_date;
	private LocalDateTime reply_update;
	
}
