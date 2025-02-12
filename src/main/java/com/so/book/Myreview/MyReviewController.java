package com.so.book.Myreview;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.so.book.common.utils.FileUtils;
import com.so.book.member.MemberVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/myReview/*")
@RequiredArgsConstructor
public class MyReviewController {

	private final MyReviewService myReviewService;
	
private final FileUtils fileUtils;
	
	@Value("${com.ezen.upload.path}")
	private String uploadPath;
	
	@GetMapping("/my_reviews")
	public void my_reviews(Model model, HttpSession session) {
		
		String mem_id = ((MemberVo)session.getAttribute("login_auth")).getMem_id();
		
		List<Map<String, Object>> my_reviews = myReviewService.my_reviews(mem_id);
		
		model.addAttribute("my_reviews", my_reviews);
		
	}

	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return fileUtils.getFile(uploadPath + "\\" + dateFolderName, fileName);
	}

}
