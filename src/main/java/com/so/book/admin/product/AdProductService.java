package com.so.book.admin.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.so.book.admin.category.CategoryVo;
import com.so.book.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdProductService {
	
	private final AdProductMapper adProductMapper;
	
	public void pro_insert(ProductVo vo) {
		adProductMapper.pro_insert(vo);
	}
	
	public List<ProductVo> pro_list(SearchCriteria cri) {
		return adProductMapper.pro_list(cri);
	}

	public int getTotalCount(SearchCriteria cri) {
		return adProductMapper.getTotalCount(cri);
	}
	
	public void pro_sel_delete(int[] pro_code_arr) {
		adProductMapper.pro_sel_delete(pro_code_arr);
	}
	
	public ProductVo pro_edit_form(Integer pro_code) {
		return adProductMapper.pro_edit_form(pro_code);
	}
	
	public void pro_edit_save(ProductVo vo) {
		adProductMapper.pro_edit_save(vo);
	}
	
	public void pro_delete(Integer pro_code) {
		adProductMapper.pro_delete(pro_code);
	}
}
