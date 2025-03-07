package com.so.book.order;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderVo {

	private Integer ord_code;
	private String mem_id;
	private String ord_name;
	private String ord_zipcode;
	private String ord_addr;
	private String ord_addr_detail;
	private String ord_tel;
	private String ord_email;
	private int ord_price;
	private LocalDateTime ord_regdate;
	private String ord_status;
	private String ord_message;
}
