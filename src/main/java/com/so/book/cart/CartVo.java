package com.so.book.cart;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartVo {
	
	private Integer pro_code;
	private Integer cart_amount;
	private String mem_id;
	private Date cart_date;
}
