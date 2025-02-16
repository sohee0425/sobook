package com.so.book.review;

import java.time.LocalDateTime;
import java.util.List;

import com.so.book.admin.product.ProductVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewVo {

	private Integer rev_code;
	private String mem_id;
	private Integer pro_code;
	private String rev_title;
	private String rev_content;
	private int rev_score;
	private LocalDateTime rev_date;
	private LocalDateTime rev_update;
	
	// 관리자 상품후기 목록에서 사용하는 상품
	private ProductVo product;
	
	// 관리자 상품후기 답변
	private List<ReviewReply> replies;
	
	
}
