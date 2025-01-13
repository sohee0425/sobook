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
	private String mem_id;
	private int cart_amount;
	private Date cart_date;
}
