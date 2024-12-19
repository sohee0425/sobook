package com.so.book.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.so.book.admin.product.ProductVo;
import com.so.book.common.utils.SearchCriteria;

public interface ProductMapper {

	List<ProductVo> getProductListBysecondCategory(@Param("cri") SearchCriteria cri,@Param("cate_code") Integer second_cate_code);
	
	int getCountProductListBysecondCategory(@Param("cate_code") Integer second_cate_code);
}
