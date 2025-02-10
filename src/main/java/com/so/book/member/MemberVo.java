package com.so.book.member;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 클래스명은 테이블명과 상관 없지만, 필드는 컬럼명과 동일하게 작성한다.
@Getter
@Setter
@ToString
public class MemberVo {

	private Integer mem_code;
	private String mem_id;
	private String mem_name;
	private String mem_pw;
	private String mem_email;
	private String mem_zipcode;
	private String mem_addr;
	private String mem_deaddr;
	private String mem_tel;
	private String mem_receivEmail;
	private String mem_receivSMS;
	private int mem_point;
	private Date mem_joindate;
	private Date mem_updatedate;
	private Date mem_lastlogin;
}
