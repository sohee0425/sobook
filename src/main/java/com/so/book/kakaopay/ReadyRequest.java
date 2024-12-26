package com.so.book.kakaopay;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

// 결제 준비(ready)요청 파라미터
@Getter
@AllArgsConstructor // 모든 필트를 대상으로 생성자메서드 생성
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ReadyRequest {

	private String cid;
	private String partner_order_id;
	private String partner_user_id;
	private String item_name;
	private int quantity;
	private int total_amount;
	private int tax_free_amount;
	private String approval_url;
	private String cancel_url;
	private String fail_url;
}