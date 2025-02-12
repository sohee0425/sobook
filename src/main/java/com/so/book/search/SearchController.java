package com.so.book.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.so.book.admin.product.ProductVo;
import com.so.book.common.utils.FileUtils;
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
	
	
	private final FileUtils fileUtils;
	
	@Value("${com.ezen.upload.path}")
	private String uploadPath;

	// 검색목록
	@GetMapping("/search_list")
	public String search_list(Model model, SearchCriteria cri) {
		
		log.info("검색어: " + cri.getKeyword());
		log.info("검색 유형: " + cri.getSearchType());
		
		List<ProductVo> search_list = searchService.search_list(cri);
			
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(searchService.search_count(cri));
		
		
		model.addAttribute("searchType", cri.getSearchType());
		model.addAttribute("keyword", cri.getKeyword());

		model.addAttribute("search_list", search_list);
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("cri", cri);
		
		
		 return "search/search_list";
	}
	

	
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return fileUtils.getFile(uploadPath + "\\" + dateFolderName, fileName);
	}

}
