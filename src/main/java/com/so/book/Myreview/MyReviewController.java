package com.so.book.Myreview;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.so.book.common.constants.Constants;
import com.so.book.common.utils.FileUtils;
import com.so.book.common.utils.PageMaker;
import com.so.book.common.utils.SearchCriteria;
import com.so.book.member.MemberVo;
import com.so.book.order.OrderService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/review/*")
@RequiredArgsConstructor
public class MyReviewController {

	private final MyReviewService myReviewService;
	private final FileUtils fileUtils;
	
	@Value("${com.ezen.upload.path}")
	private String uploadPath;
	
	
	@GetMapping("/my_review")
	public void my_reviews(Model model, HttpSession session, SearchCriteria cri) {
		
		String mem_id = ((MemberVo)session.getAttribute("login_auth")).getMem_id();
		cri.setPerPageNum(Constants.REVIEW_LIST_PAGE_SIZE);
		List<Map<String, Object>> my_review = myReviewService.my_review(mem_id, cri);
		
		model.addAttribute("my_review", my_review);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(myReviewService.getTotalReviewCountByUserId(mem_id));
		
		model.addAttribute("pageMaker", pageMaker);
		
	}
	

	
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return fileUtils.getFile(uploadPath + "\\" + dateFolderName, fileName);
	}

}
