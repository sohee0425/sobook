package com.so.book.search;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.so.book.admin.product.ProductVo;
import com.so.book.common.utils.PageMaker;
import com.so.book.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/search/*")
@Controller
@RequiredArgsConstructor
public class SearchController {
	
	private final SearchService searchService;

	// 검색목록
//	@GetMapping("/search_list")
//	public void search_list(Model model, SearchCriteria cri) {
//		
//		List<ProductVo> search_list = searchService.search_list(cri);
//			
//		PageMaker pageMaker = new PageMaker();
//		pageMaker.setCri(cri);
//		pageMaker.setTotalCount(searchService.search_count(cri));
//			
//		
//		
//		model.addAttribute("search_list", search_list);
//		model.addAttribute("pageMaker", pageMaker);
//	}
	
	 @GetMapping("/search_list")
	    public String search_list(@RequestParam(value = "searchType", required = false) String searchType,
	                                 @RequestParam(value = "keyword", required = false) String keyword,
	                                 Model model) {
	        SearchCriteria criteria = new SearchCriteria();
	        criteria.setSearchType(searchType);
	        criteria.setKeyword(keyword);

	        List<ProductVo> productList = searchService.search_list(criteria);
	        model.addAttribute("productList", productList);

	        return "search/serch_list";
	 }

}
