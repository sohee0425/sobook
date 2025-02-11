package com.so.book.search;

import java.util.List;

import org.springframework.stereotype.Service;

import com.so.book.admin.product.ProductVo;
import com.so.book.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {
	
	private final SearchMapper searchMapper;
	
//	// 자료검색 페이지
//	public List<ProductVo> search_list(SearchCriteria cri) {
//		return searchMapper.search_list(cri);
//	}
//	
//	public int search_count(SearchCriteria cri) {
//		return searchMapper.search_count(cri);
//	}
	
	public List<ProductVo> search_list(SearchCriteria cri) {
        return searchMapper.search_list(cri);
    }
	
}
