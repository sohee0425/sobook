package com.so.book.admin.product;

import java.util.List;

import org.springframework.stereotype.Service;

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
}
