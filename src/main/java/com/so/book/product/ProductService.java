package com.so.book.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.so.book.admin.product.ProductVo;
import com.so.book.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class ProductService {
	
	private final ProductMapper productMapper;
	
	public List<ProductVo> getProductListBysecondCategory(SearchCriteria cri, Integer second_cate_code) {
		return productMapper.getProductListBysecondCategory(cri, second_cate_code);
	}
	
	public int getCountProductListBysecondCategory(Integer second_cate_code) {
		return productMapper.getCountProductListBysecondCategory(second_cate_code);
	} 
	
	public ProductVo pro_info(Integer pro_code) {
		return productMapper.pro_info(pro_code);
	}
}
