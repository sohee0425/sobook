package com.so.book.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class OrderDetailVo {

	private Integer ord_code;
	private Integer pro_code;
	private int dt_amount;
	private int dt_price;
}
