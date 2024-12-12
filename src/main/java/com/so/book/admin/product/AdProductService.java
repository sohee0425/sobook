package com.so.book.admin.product;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdProductService {
	
	private final AdProductMapper adProductMapper;
	
	public void pro_insert(ProductVo vo) {
		adProductMapper.pro_insert(vo);
	}

}
