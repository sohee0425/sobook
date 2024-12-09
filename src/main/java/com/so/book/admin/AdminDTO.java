package com.so.book.admin;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminDTO {

	private String adm_id;
	private String adm_pw;
	private Date adm_date;
}
