package com.so.book.admin.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.so.book.admin.AdminDTO;
import com.so.book.common.utils.FileUtils;
import com.so.book.common.utils.PageMaker;
import com.so.book.common.utils.SearchCriteria;
import com.so.book.review.ReviewReply;
import com.so.book.review.ReviewService;
import com.so.book.review.ReviewVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/review/*")
public class AdReviewController {

	private final AdReviewService adReviewService;
	private final ReviewService reviewService;
	
	// 이미지 관련 작업 기능
	private final FileUtils fileUtils;
	
	@Value("${com.ezen.upload.path}")
	private String uploadPath;
	
	@GetMapping("/review_list")
	public void review_list(@ModelAttribute("cri") SearchCriteria cri, @ModelAttribute("rev_content") String rev_content,  @ModelAttribute("rev_score") String rev_score, Model model) throws Exception {
		
		log.info("검색" + cri.toString());
		
		List<ReviewVo> review_list = adReviewService.review_list(cri, rev_content, rev_score);
		
		review_list.forEach(review_Info -> {
			review_Info.getProduct().setPro_up_folder((review_Info.getProduct().getPro_up_folder().replace("\\", "/")));
		});
		
		PageMaker pageMaker = new PageMaker(); 
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(adReviewService.review_count(cri, rev_content, rev_score));
		
		model.addAttribute("review_list", review_list);
		model.addAttribute("pageMaker", pageMaker);		
	}
	
	// 상품후기 정보
	@GetMapping("/review_info/{rev_code}")
	public ResponseEntity<ReviewVo> review_info(@PathVariable("rev_code") Integer rev_code) throws Exception {
		ResponseEntity<ReviewVo> entity = null;
		
		entity = new ResponseEntity<ReviewVo>(reviewService.review_info(rev_code), HttpStatus.OK);
		
		return entity;
	}
	
	// 상품후기 답변 저장
	@PostMapping(value = "/reply_insert", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> reply_insert(@RequestBody ReviewReply vo, HttpSession session) throws Exception {
		ResponseEntity<String> entity = null;
		
		// 관리자 아이디
		AdminDTO adminDTO = ((AdminDTO) session.getAttribute("admin_auth"));
		vo.setManager_id(adminDTO.getAdm_id());
		
		reviewService.reply_insert(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 답변하기 수정 정보
	// 리턴 되는 ReviewReply 객체가 json으로 변환하여 클라이언트 (fetch()함수)로 응답된다
	@GetMapping("/reply_info/{reply_id}")
	public ResponseEntity<ReviewReply> reply_info(@PathVariable("reply_id") Integer reply_id) throws Exception {
		ResponseEntity<ReviewReply> entity = null;
		
		entity = new ResponseEntity<ReviewReply>(adReviewService.reply_info(reply_id), HttpStatus.OK);
		
		return entity;
	}
	
	// 답변 수정 저장
	@PostMapping(value = "/reply_modify_save", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> reply_modify_save(@RequestBody ReviewReply vo) throws Exception {
		ResponseEntity<String> entity = null;
		
		adReviewService.reply_modify_save(vo.getReply_id(), vo.getReply_content());
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 답변삭제
	@DeleteMapping("/reply_delete/{reply_id}")
	public ResponseEntity<String> reply_delete(@PathVariable("reply_id") Integer reply_id) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		adReviewService.reply_delete(reply_id);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return fileUtils.getFile(uploadPath + "\\" + dateFolderName, fileName);
	}
}
