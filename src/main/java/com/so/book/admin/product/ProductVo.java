package com.so.book.admin.product;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductVo {

	private Integer pro_code;
	private Integer cate_code;
//	private int cate_prtcode;
	private String pro_name;
	private int pro_price;
	private int pro_discount;
	private String pro_publisher;
	private String pro_content;
	private String pro_up_folder; // 이미지 파일 저장되는 날짜폴더명
	private String pro_img; // 상품 이미지 이름/ 업로드된 파일에서 파일이름을 이용하여 저장
	private int pro_amount;
	private String pro_buy;
	private int pro_review;
	private Date pro_date;
	private Date pro_update;
	private String pro_writer;
}
