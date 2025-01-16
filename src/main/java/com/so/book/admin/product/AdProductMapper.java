package com.so.book.admin.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.so.book.common.utils.SearchCriteria;

public interface AdProductMapper {

	// 상품 등록
	void pro_insert(ProductVo vo);
	
	List<ProductVo> pro_list(@Param("cri") SearchCriteria cri, @Param("period") String period, @Param("start_date") String start_date, @Param("end_date") String end_date, 
				 @Param("cate_code") String cate_code, @Param("pro_buy") String pro_buy);
	
	int getTotalCount(@Param("cri") SearchCriteria cri, @Param("period") String period, @Param("start_date") String start_date, @Param("end_date") String end_date,
				 @Param("cate_code") String cate_code, @Param("pro_buy") String pro_buy);
	
	void pro_sel_delete(int[] pro_code_arr);
	
	ProductVo pro_edit_form(Integer pro_code);
	
	void pro_edit_save(ProductVo vo);
	
	void pro_delete(Integer pro_code);
}
