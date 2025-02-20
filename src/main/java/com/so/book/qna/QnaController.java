package com.so.book.qna;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.so.book.common.utils.PageMaker;
import com.so.book.common.utils.SearchCriteria;
import com.so.book.member.MemberVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/qna/*")
@RequiredArgsConstructor
public class QnaController {
	
	private final QnaService qnaService;
	
	// qna 등록
	@PostMapping(value = "/qna_write", consumes = "application/json", produces =  {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> qna_write(@RequestBody QnaVo vo, HttpSession session) throws Exception {
		
		String mem_id = ((MemberVo)session.getAttribute("login_auth")).getMem_id();
		vo.setMem_id(mem_id);
		
		ResponseEntity<String> entity = null;
		
		qnaService.qna_write(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// qna 목록
	@GetMapping("/qna_list/{pro_code}/{page}")
	public ResponseEntity<Map<String, Object>> qna_list(@PathVariable("pro_code") Integer pro_code, @PathVariable("page") int page) throws Exception {
		
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> map = new HashMap<>();
		
		// ans 목록
		SearchCriteria cri = new SearchCriteria();
		cri.setPerPageNum(5);
		cri.setPage(page);
		
		List<QnaVo> qna_list = qnaService.qna_list(pro_code, cri);
		
		
		
		// 페이징
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(qnaService.getCountQna(pro_code));
		
		map.put("qna_list", qna_list);
		map.put("pageMaker", pageMaker);
		
		entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		
		return entity;
	}
	
	//  상세
	@GetMapping("/qna_info/{qna_code}")
	public ResponseEntity<QnaVo> qna_info(@PathVariable("qna_code") Integer qna_code) throws Exception {
		
		ResponseEntity<QnaVo> entity = null;
		
		entity = new ResponseEntity<QnaVo>(qnaService.qna_info(qna_code), HttpStatus.OK);
		
		return entity;
	}
	
	// 수정
	@PutMapping("/qna_modify")
	public ResponseEntity<String> qna_modify(@RequestBody QnaVo vo) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		qnaService.qna_modify(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 삭제
	@DeleteMapping("/qna_delete/{qna_code}")
	public ResponseEntity<String> qna_delete(@PathVariable("qna_code") Integer qna_code) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		qnaService.qna_delete(qna_code);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}

}
