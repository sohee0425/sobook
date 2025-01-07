package com.so.book.wish;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WishVo {

	private Integer wish_id;
	private Integer pro_code;
	private String mem_id;
	private Date wish_date;
}
