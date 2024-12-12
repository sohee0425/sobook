package com.so.book.admin.category;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryVo {

	private Integer cate_code; // 2차 카테고리
	private Integer cate_prtcode;
	private String cate_name;
}
