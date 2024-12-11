package com.so.book.admin.notice;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoticeVo {

	private int ntc_bno;
	private String ntc_title;
	private String ntc_content;
	private String adm_id;
	private Date ntc_regdate;
	private Date ntc_updatedate;
	private int ntc_viewcount;
}
