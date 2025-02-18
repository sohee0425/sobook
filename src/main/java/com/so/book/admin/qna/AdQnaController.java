package com.so.book.admin.qna;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedAttribute;
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
import com.so.book.qna.AnswerVo;
import com.so.book.qna.QnaService;
import com.so.book.qna.QnaVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin/qna/*")
@RequiredArgsConstructor
public class AdQnaController {
	
	private final AdQnaService adQnaService;
	private final QnaService qnaService;
	
	// 이미지 관련 작업 기능
	private final FileUtils fileUtils;
	
	@Value("${com.ezen.upload.path}")
	private String uploadPath;
	
	// qna 목록
	@GetMapping("/qna_list")
	public void qna_list(@ModelAttribute("cri") SearchCriteria cri,	@ModelAttribute("period") String period, @ModelAttribute("start_date") String start_date, 
			@ModelAttribute("end_date") String end_date, Model model) throws Exception {
		log.info("검색정보:" + cri);
		log.info("날짜검색: " + period);
		log.info("날짜검색: " + start_date);
		log.info("날짜검색: " + end_date);
		log.info("검색 유형: " + cri.getSearchType());
		log.info("검색 키워드: " + cri.getKeyword());

		cri.setPerPageNum(5);
		
		List<QnaVo> qna_list = adQnaService.qna_list(cri, end_date, period, start_date);
		
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(adQnaService.qna_count(cri, period, start_date, end_date));
		
		model.addAttribute("qna_list", qna_list);
		model.addAttribute("pageMaker", pageMaker);
		
	}
	
	// qna 정보
	@GetMapping("/qna_info/{qna_code}")
	public ResponseEntity<QnaVo> qna_info(@PathVariable("qna_code") Integer qna_code) throws Exception {
		ResponseEntity<QnaVo> entity = null;
		
		entity = new ResponseEntity<QnaVo>(qnaService.qna_info(qna_code), HttpStatus.OK);
		
		return entity;
	}
	
	// qna 답변 등록
	@PostMapping(value = "/answer_insert", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> answer_insert(@RequestBody AnswerVo vo, HttpSession session) throws Exception {
		ResponseEntity<String> entity = null;
		
		// 관리자 아이디
		AdminDTO adminDTO = ((AdminDTO) session.getAttribute("admin_auth"));
		vo.setManager_id(adminDTO.getAdm_id());
		
		qnaService.answer_insert(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 답변 수정 정보
	@GetMapping("/answer_info/{ans_code}")
	public ResponseEntity<AnswerVo> answer_info(@PathVariable("ans_code") Integer ans_code) throws Exception {
		ResponseEntity<AnswerVo> entity = null;
		
		entity = new ResponseEntity<AnswerVo>(adQnaService.answer_info(ans_code), HttpStatus.OK);
		
		return entity;
	}
	
	// 수정 저장
	@PostMapping(value = "/answer_modify_save", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> answer_modify_save(@RequestBody AnswerVo vo) throws Exception {
		ResponseEntity<String> entity = null;
		
		adQnaService.answer_modify_save(vo.getAns_code(), vo.getAns_title(), vo.getAns_content());
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 답변 삭제
	@DeleteMapping("/answer_delete/{ans_code}")
	public ResponseEntity<String> answer_delete(@PathVariable("ans_code") Integer ans_code) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		adQnaService.answer_delete(ans_code);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
		
		
	}
	
	
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return fileUtils.getFile(uploadPath + "\\" + dateFolderName, fileName);
	}

}
