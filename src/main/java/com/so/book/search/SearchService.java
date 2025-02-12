package com.so.book.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.so.book.admin.product.ProductVo;
import com.so.book.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {
	
	private final SearchMapper searchMapper;
	
//	// 자료검색 페이지
	public List<ProductVo> search_list(SearchCriteria cri) {
		return searchMapper.search_list(cri);
	}

	
//	public List<ProductVo> search_list(SearchCriteria cri) {
//	    Map<String, Object> paramMap = new HashMap<>();
//	    paramMap.put("searchType", cri.getSearchType());
//	    paramMap.put("keyword", cri.getKeyword());
//	    paramMap.put("pageStart", cri.getPageStart());
//	    paramMap.put("perPageNum", cri.getPerPageNum());
//
//	    return searchMapper.search_list(paramMap);
//	}
	
	public int search_count(SearchCriteria cri) {
		return searchMapper.search_count(cri);
	}
	
//	public List<ProductVo> search_list(SearchCriteria cri) {
//        return searchMapper.search_list(cri);
//    }
	
}
